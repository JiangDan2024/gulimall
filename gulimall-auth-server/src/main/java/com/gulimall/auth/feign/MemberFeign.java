package com.gulimall.auth.feign;

import com.gulimall.auth.vo.SocialUser;
import com.gulimall.auth.vo.UserLoginVo;
import com.gulimall.auth.vo.UserRegisteVo;
import com.gulimall.common.core.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-member")
public interface MemberFeign {
    @PostMapping("/member/member/register")
    AjaxResult register(@RequestBody UserRegisteVo vo);

    @PostMapping("/member/member/login")
    AjaxResult login(@RequestBody UserLoginVo vo);

    @PostMapping("/member/member/oauth2/weibo")
    AjaxResult oauth2loggin(@RequestBody SocialUser socialUser);
}
