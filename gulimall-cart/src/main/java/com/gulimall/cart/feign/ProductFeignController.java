package com.gulimall.cart.feign;

import com.gulimall.common.core.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("gulimall-product")
public interface ProductFeignController {

    @GetMapping("/product/skuinfo/info/{skuId}")
    AjaxResult getInfo(@PathVariable("skuId") Long skuId);

    @GetMapping("/product/skuSaleAttrValue/stringList/{skuId}")
    List<String> getSkuAttrValues(@PathVariable("skuId") Long skuId);
}
