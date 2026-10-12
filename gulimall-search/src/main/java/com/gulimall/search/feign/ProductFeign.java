package com.gulimall.search.feign;

import com.gulimall.common.core.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gulimall-product")
public interface ProductFeign {
    @GetMapping("/product/attr/info/{attrId}")
    AjaxResult attrInfo(@PathVariable("attrId") Long attrId);

    @GetMapping("/product/brand/infos")
    AjaxResult brandInfos(@RequestParam("brandIds") List<Long> brandIds);

    @GetMapping("/product/pmsCategory/getById/{catId}")
    AjaxResult catalogInfo(@PathVariable Long catId);
}
