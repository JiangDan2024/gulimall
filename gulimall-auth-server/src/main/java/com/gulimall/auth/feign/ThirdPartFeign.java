package com.gulimall.auth.feign;

import com.gulimall.common.core.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gulimall-third-party")
public interface ThirdPartFeign {

    @GetMapping("/thirdparty/sms/sendCode")
    AjaxResult sendCode(@RequestParam String phone, @RequestParam String code);
}
