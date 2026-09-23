package com.gulimall.ware.feign;

import com.gulimall.common.core.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("gulimall-product")
public interface ProductFeign {

    @GetMapping("/product/skuinfo/info/{skuId}")
    AjaxResult getSkuInfo(@PathVariable("skuId") Long skuId);
}
