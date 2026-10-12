package com.gulimall.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.search.constant.ProductConstant;
import com.gulimall.search.service.ProductEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ProductEsServiceImpl implements ProductEsService {

    @Autowired
    ElasticsearchClient esClient;

    @Override
    public boolean productUp(List<SkuEsModel> esModels) throws IOException {
        //创建一个索引
//        esClient.indices().create(c->c
//                .index(ProductConstant.PRODUCT_ES_INDEX)
//        );
        //新建映射mapping，手动在es客户端建了product
        //构建批量新增请求
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (SkuEsModel esModel : esModels) {
            br.operations(op->op
                    .index(idx->idx
                            .index(ProductConstant.PRODUCT_ES_INDEX)
                            .id(String.valueOf(esModel.getSkuId()))
                            .document(esModel)));
        }
        BulkResponse result = esClient.bulk(br.build());
        boolean b = result.errors();
        if(b){
            //TODO 对失败数据进行处理
            log.error("商品上架es批量保存失败{}", result);
        }else{
            List<String> list = result.items().stream().map(BulkResponseItem::id).toList();
            log.info("es保存成功{}", list);
        }
        return result.errors();
    }
}
