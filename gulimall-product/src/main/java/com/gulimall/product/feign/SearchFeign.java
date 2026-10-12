package com.gulimall.product.feign;

import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.to.es.SkuEsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-search")
public interface SearchFeign {

    @PostMapping("/search/save/productUp")
    AjaxResult productUp(@RequestBody List<SkuEsModel> esModels);
}
