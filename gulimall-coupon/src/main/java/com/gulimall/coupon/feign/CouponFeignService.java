package com.gulimall.coupon.feign;

import com.gulimall.common.core.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    @RequestMapping("/coupon/smsCoupon/member/list")
    public AjaxResult memberCoupons();
}
