package com.gulimall.auth.feign;

import com.gulimall.auth.vo.UserRegisteVo;
import com.gulimall.common.core.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-member")
public interface MemberFeign {
    @PostMapping("/member/member/register")
    AjaxResult register(@RequestBody UserRegisteVo vo);
}
