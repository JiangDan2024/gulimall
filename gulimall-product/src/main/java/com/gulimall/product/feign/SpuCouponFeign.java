package com.gulimall.product.feign;

import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.common.to.SpuBoundsTO;
import com.gulimall.common.core.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-coupon")
public interface SpuCouponFeign {

    @PostMapping("/coupon/spuBounds/save")
    public AjaxResult addSpuBound(@RequestBody SpuBoundsTO spuBounds);

    @PostMapping("/coupon/skuFullReduction/skuReduction/save")
    public AjaxResult addSkuCoupon(@RequestBody SkuReductionTo skuReductionTo);

}
