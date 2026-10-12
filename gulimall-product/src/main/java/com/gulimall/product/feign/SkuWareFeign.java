package com.gulimall.product.feign;

import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.to.SkuHasstockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-ware")
public interface SkuWareFeign {

    @PostMapping("/ware/waresku/hasStock")
    AjaxResult hasStock(@RequestBody List<Long> ids);
}
