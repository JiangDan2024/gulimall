package com.gulimall.search.vo;

import com.gulimall.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResult {
    //查询到的所有商品信息
    private List<SkuEsModel> products;
    //分页
    private Integer pageNum=1;//当前页码
    private Long total=1L;//总记录数
    private Integer totalPages=1;//总页码数

    private List<BrandVo> brandVos;//当前查询到的结果，所有涉及到的品牌
    private List<CatalogVo> catalogs;//当前查询到的结果，所有涉及到的分类
    private List<AttrVo> attrs;//当前查询到的结果，所有涉及到的属性
    private List<Integer> pageNavs;
    private List<NavVo> navs = new ArrayList<>();

    //attrIds,便于前端用于判断
    private List<Long> attrIds;

    @Data
    public static class BrandVo {
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    @Data
    public static class CatalogVo {
        private Long catalogId;
        private String catalogName;
    }

    @Data
    public static class AttrVo{
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }

    @Data
    public static class NavVo{
        private String navName;
        private String navValue;
        private String link;
    }
}
