package com.gulimall.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.*;
import com.alibaba.fastjson.TypeReference;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.common.utils.StringUtils;
import com.gulimall.search.constant.EsConstant;
import com.gulimall.search.constant.ProductConstant;
import com.gulimall.search.feign.ProductFeign;
import com.gulimall.search.service.SearchListService;
import com.gulimall.search.vo.*;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchListServiceImpl implements SearchListService {

    @Autowired
    ElasticsearchClient esClient;

    @Autowired
    ProductFeign productFeign;

    @Override
    public SearchResult search(SearchParam param) {
        //准备检索请求
        SearchRequest searchRequest = buildQuery(param);
        SearchResult searchResult;
        try {
            //执行请求
            SearchResponse<SkuEsModel> searchResponse = esClient.search(searchRequest,SkuEsModel.class);
            //分析结果
            searchResult = buildSearchResult(searchResponse,param);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return searchResult;
    }

    private SearchResult buildSearchResult(SearchResponse<SkuEsModel> searchResponse,SearchParam param) throws IOException {
        SearchResult searchResults = new SearchResult();
        searchResults.setAttrIds(new ArrayList<>());
        //封装所查询到的商品
        List<SkuEsModel> products = new ArrayList<>();
        for (Hit<SkuEsModel> hit : searchResponse.hits().hits()) {
            SkuEsModel source = hit.source();
            if (StringUtils.isNotEmpty(param.getKeyword())){
                String skuTitle = hit.highlight().get("skuTitle").get(0);
                source.setSkuTitle(skuTitle);
            }
            products.add(source);
        }
        //当前商品涉及到的所有属性信息
        List<SearchResult.AttrVo>  attrs = new ArrayList<>();
        Aggregate attrAgg = searchResponse.aggregations().get("attr_agg");
        Aggregate attrIdAgg = attrAgg.nested().aggregations().get("attr_id_agg");
        for (LongTermsBucket bucket : attrIdAgg.lterms().buckets().array()) {
            SearchResult.AttrVo vo = new SearchResult.AttrVo();
            vo.setAttrId(bucket.key());
            FieldValue attrsNameAggs = bucket.aggregations().get("attrs_name_aggs").sterms().buckets().array().get(0).key();
            List<String> attrsValueAggs = bucket.aggregations().get("attrs_value_aggs").sterms().buckets().array().stream().map(item -> item.key().stringValue()).toList();
            vo.setAttrName(attrsNameAggs.stringValue());
            vo.setAttrValue(attrsValueAggs);
            attrs.add(vo);
        }
        //当前商品涉及到的所有品牌信息
        Aggregate brandAgg = searchResponse.aggregations().get("brand_agg");
        List<SearchResult.BrandVo>  brands = new ArrayList<>();
        if (brandAgg!=null){
            LongTermsAggregate lterms = brandAgg.lterms();
            for (LongTermsBucket bucket : lterms.buckets().array()) {
                SearchResult.BrandVo vo = new SearchResult.BrandVo();
                vo.setBrandId(bucket.key());
                FieldValue brandImgAgg = bucket.aggregations().get("brand_img_agg").sterms().buckets().array().get(0).key();
                vo.setBrandImg(brandImgAgg.stringValue());
                FieldValue brandNameAgg = bucket.aggregations().get("brand_name_agg").sterms().buckets().array().get(0).key();
                vo.setBrandName(brandNameAgg.stringValue());
                brands.add(vo);
            }
        }
        //当前商品涉及到所有分类信息
        List<SearchResult.CatalogVo> catalogVos = new ArrayList<>();
        Aggregate catalogAgg = searchResponse.aggregations().get("catalog_agg");
        for (LongTermsBucket bucket : catalogAgg.lterms().buckets().array()) {
            SearchResult.CatalogVo vo = new SearchResult.CatalogVo();
            vo.setCatalogId(bucket.key());
            FieldValue catalogNameAgg = bucket.aggregations().get("catalog_name_agg").sterms().buckets().array().get(0).key();
            vo.setCatalogName(catalogNameAgg.stringValue());
            catalogVos.add(vo);
        }
        //分页信息，页码，总记录数，总页码
        long total = searchResponse.hits().total().value();
        searchResults.setTotal(total);
        if(param.getPageNum()!=null){
            searchResults.setPageNum(param.getPageNum());
        }
        int totalPages = total%EsConstant.PRODUCT_PAGESIZE==0?(int)total/EsConstant.PRODUCT_PAGESIZE:(int)total/EsConstant.PRODUCT_PAGESIZE+1;
        searchResults.setTotalPages(totalPages);
        searchResults.setProducts(products);
        searchResults.setBrandVos(brands);
        searchResults.setCatalogs(catalogVos);
        searchResults.setAttrs(attrs);
        System.out.println(searchResults);

        //构建能够遍历的页码
        List<Integer> pageNavs = new ArrayList<>();
        for (int i=1;i<=totalPages;i++){
            pageNavs.add(i);
        }
        //构建面包屑
        List<Long> attrIds = new ArrayList<>();
        if(param.getAttrs()!=null){
            List<SearchResult.NavVo> navVos = param.getAttrs().stream().map(attr -> {
                String[] attrSplit = attr.split("_");
                SearchResult.NavVo navVo = new SearchResult.NavVo();
                attrIds.add(Long.parseLong(attrSplit[0]));
                //远程查询attrId，得到attrName
                AjaxResult ajaxResult = productFeign.attrInfo(Long.parseLong(attrSplit[0]));
                //成功
                if(ajaxResult.isSuccess()){
                    AttrVo data = ajaxResult.getData(new TypeReference<AttrVo>() {});
                    navVo.setNavName(data.getAttrName());
                }else{
                    navVo.setNavName(attrSplit[0]);
                }
                navVo.setNavValue(attrSplit[1]);
                String link = getLink(param, attr,"attrs");
                navVo.setLink("http://search.gulimall.com/list.html?"+link);
                return navVo;
            }).collect(Collectors.toList());
            searchResults.setNavs(navVos);
            searchResults.setAttrIds(attrIds);
        }
        //加上分类和品牌的面包屑，避免空指针异常，先把面包屑赋一个ArrayList
        List<SearchResult.NavVo> navs = searchResults.getNavs();
        if(param.getBrandId()!=null&&param.getBrandId().size()>0){
            SearchResult.NavVo brandNav = new SearchResult.NavVo();
            //brand根据ids查询出brand信息list
            brandNav.setNavName("品牌");
            AjaxResult ajaxResult = productFeign.brandInfos(param.getBrandId());
            if(ajaxResult.isSuccess()){
                List<BrandVo> data = ajaxResult.getData(new TypeReference<List<BrandVo>>() {
                });
                StringBuffer brandVal = new StringBuffer();
                String link = "";
                for (BrandVo brand : data) {
                    brandVal.append(brand.getName());
                    //TODO 品牌id是多个，这个方法不成不成
                     link = getLink(param, brand.getBrandId().toString(), "brandId");
                }
                brandNav.setLink(link);
                brandNav.setNavValue(brandVal.toString());
            }else {
                brandNav.setNavValue("");
            }
            navs.add(brandNav);
        }
        //品牌
        if(param.getCatalog3Id()!=null){
            SearchResult.NavVo catalogNav = new SearchResult.NavVo();
            catalogNav.setNavName("分类");
            //远程根据id查询分类名称
            AjaxResult ajaxResult = productFeign.catalogInfo(param.getCatalog3Id());
            if(ajaxResult.isSuccess()){
                CatalogVo data = ajaxResult.getData(new TypeReference<CatalogVo>() {
                });
                catalogNav.setNavValue(data.getName());
            }
            String catalog3IdLink = getLink(param, param.getCatalog3Id().toString(), "catalog3Id");
            catalogNav.setLink(catalog3IdLink);
            navs.add(catalogNav);
        }
        searchResults.setNavs(navs);
        searchResults.setPageNavs(pageNavs);
        return searchResults;
    }

    private static @NonNull String getLink(SearchParam param, String val,String str) {
        //link存储的是取消这个面包屑的新地址
        String encode = URLEncoder.encode(val, StandardCharsets.UTF_8);
        //encode会把空格翻译为+，那样就无法替代了，提前replace所有的+
        String replace = encode.replace("+", "%20");
        String queryString = param.get_queryString();
        String link = queryString.replace("&"+str+"="+replace, "");
        return link;
    }

    private SearchRequest buildQuery(SearchParam param) {
        BoolQuery.Builder boolBuilder =new BoolQuery.Builder();
        /*
         * 准备检索请求
         * 模糊匹配，过滤（按照属性、分类、品牌，价格区间，库存）,排序，分页，高亮，聚合分析
         */


        //1.模糊匹配，过滤（按照属性、分类、品牌，价格区间，库存） 价格区间skuPrice=1_100/_100/100_
        //模糊匹配
        if(StringUtils.isNotEmpty(param.getKeyword())){
            MatchQuery matchQuery = MatchQuery.of(m->m
                    .field("skuTitle")
                    .query(param.getKeyword()));
            boolBuilder.must(matchQuery);
        }
        //filter
        List<Query> queries = new ArrayList<>();
        //品牌
        if(param.getBrandId() != null&& !param.getBrandId().isEmpty()){
            Query brandQuery = Query.of(q->q
                    .terms(t->t
                            .field("brandId")
                            .terms(ts->ts.value(
                                    param.getBrandId().stream().map(FieldValue::of)
                                            .toList()
                            ))));
            queries.add(brandQuery);
        }
        //分类
        if(param.getCatalog3Id()!=null){
            Query catalogQuery = Query.of(q->q
                    .term(t->t
                            .field("catalogId")
                            .value(param.getCatalog3Id())));
            queries.add(catalogQuery);
        }
        //是否有库存
        if(param.getHasStock()!=null){
            Query stockQuery = Query.of(q->q
                    .term(t->t
                            .field("hasStock")
                            .value(true)));
            queries.add(stockQuery);
        }
        //价格区间
        if(StringUtils.isNotEmpty(param.getSkuPrice())){
            Query priceQuery = null;
            String[] prices = param.getSkuPrice().split("_");
            if(param.getSkuPrice().startsWith("_")){
                priceQuery = Query.of(q->q
                        .range(r->r
                                .longNumber(l->l
                                        .field("skuPrice")
                                        .lte(Long.parseLong(prices[1])))));
            }else if(param.getSkuPrice().endsWith("_")){
                priceQuery = Query.of(q->q
                        .range(r->r
                                .longNumber(l->l
                                        .field("skuPrice")
                                        .gte(Long.parseLong(prices[0])))));
            }else if(param.getSkuPrice().contains("_")){
                priceQuery = Query.of(q->q
                        .range(r->r
                                .longNumber(l->l
                                        .field("skuPrice")
                                        .gte(Long.parseLong(prices[0]))
                                        .lte(Long.parseLong(prices[1])))));
            }
            queries.add(priceQuery);
        }
        //属性查询
        if (param.getAttrs()!=null && !param.getAttrs().isEmpty()){
            List<Query> attrQueries = new ArrayList<>();
//            BoolQuery.Builder attrQuery = new BoolQuery.Builder();
            Query attrQuery =null;
            for (String attr : param.getAttrs()) {
                List<Query> attrsQuery = new ArrayList<>();
                String[] attrs =  attr.split("_");
                Query queryId = Query.of(q->q
                        .term(t->t
                                .field("attrs.attrId")
                                .value(attrs[0])));
                Query queryValue = Query.of(q->q
                        .term(t->t
                                .field("attrs.attrValue")
                                .value(attrs[1])));
                attrsQuery.add(queryId);
                attrsQuery.add(queryValue);
                Query attrs1 = Query.of(q -> q
                        .nested(n -> n
                                .path("attrs")
                                .query(nq -> nq
                                        .bool(b -> b
                                                .must(attrsQuery)))));
                attrQueries.add(attrs1);
            }
            attrQuery = Query.of(q -> q
                    .bool(b -> b
                            .must(attrQueries)));
            queries.add(attrQuery);
        }
        SearchRequest.Builder searchBuilder = new SearchRequest.Builder();
        //2.排序，分页，高亮
        if (StringUtils.isNotEmpty(param.getSort())) {
            String[] sorts = param.getSort().split("_");
            SortOptions sortOptions = SortOptionsBuilders.field(s -> s
                    .field(sorts[0])
                    .order(sorts[1].equalsIgnoreCase("desc") ?SortOrder.Desc:SortOrder.Asc));
            searchBuilder.sort(sortOptions);
        }

        //分页
        Integer from = 0;
        if (param.getPageNum()!=null){
             from = (param.getPageNum()-1)*2;
        }
        //高亮
        Highlight.Builder highlight = new Highlight.Builder();
        highlight = highlight.fields("skuTitle", f->f)
                .preTags("<b style='color:red'>")
                        .postTags("</b>");
        //3.聚合分析
        Aggregation brandName = AggregationBuilders.terms(t -> t.field("brandName").size(1));
        Aggregation brandImg = AggregationBuilders.terms(t -> t.field("brandImg").size(1));
        Aggregation brandAgg = new Aggregation.Builder().terms(t -> t
                .field("brandId")
                .size(10))
                .aggregations("brand_name_agg",brandName)
                .aggregations("brand_img_agg",brandImg)
                .build();
        Aggregation catalogName = AggregationBuilders.terms(t -> t.field("catalogName").size(100));
        Aggregation catalogAgg = new Aggregation.Builder().terms(t -> t
                .field("catalogId")
                .size(100))
                .aggregations("catalog_name_agg",catalogName)
                .build();
        Aggregation attrNameAgg = AggregationBuilders.terms(t -> t.field("attrs.attrName").size(100));
        Aggregation attrValueAgg = AggregationBuilders.terms(t -> t.field("attrs.attrValue").size(50));
        Aggregation attrIdAgg = new Aggregation.Builder().terms(t -> t
                .field("attrs.attrId")
                .size(100))
                .aggregations("attrs_name_aggs",attrNameAgg)
                .aggregations("attrs_value_aggs",attrValueAgg)
                .build();
        Aggregation attrAgg = new Aggregation.Builder()
                .nested(n -> n.path("attrs"))
                .aggregations("attr_id_agg", attrIdAgg)
                .build();
        boolBuilder.filter(queries);
        SearchRequest build = searchBuilder
                .index(ProductConstant.PRODUCT_ES_INDEX)
                .query(q -> q
                        .bool(boolBuilder.build()))
                .from(from)
                .size(EsConstant.PRODUCT_PAGESIZE)
                .highlight(highlight.build())
                .aggregations("brand_agg",brandAgg)
                .aggregations("catalog_agg",catalogAgg)
                .aggregations("attr_agg",attrAgg)
                .build();
        System.out.println(build.toString());
        return build;
    }
}
