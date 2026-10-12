package com.gulimall.search.controller;

import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.exception.BizCodeEnume;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.search.service.ProductEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/search/save")
@RestController
public class ElasticSaveController {

    @Autowired
    private ProductEsService productEsService;

    //商品上架
    @PostMapping("/productUp")
    public AjaxResult productUp(@RequestBody List<SkuEsModel> esModels) {
        boolean b = false;
        try {
            b = productEsService.productUp(esModels);
        }catch (Exception e){
            log.error("ElasticSaveController商品上架错误",e);
            return new AjaxResult(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg(),b);
        }
        if(!b){
            return AjaxResult.success();
        }else{
            return new AjaxResult(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg(),b);
        }
    }
}
