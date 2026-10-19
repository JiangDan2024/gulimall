package com.gulimall.ssoserver.web;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class LoginController {
    @GetMapping("/loginPage")
    public String loginPage(@RequestParam("redirect_url") String redirect_url,
                            Model model,
                            @CookieValue(value = "sso_token",required = false) String sso_token){
        if(!StringUtils.isEmpty(sso_token)){
            return "redirect:"+redirect_url+"?token="+sso_token;
        }else{
            model.addAttribute("redirect_url", redirect_url);
            return "login";
        }
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam(value = "redirect_url",required = false) String redirect_url,
                          HttpSession session,
                          HttpServletResponse response){
        //如果账号密码不为空就登录成功
        if(!StringUtils.isEmpty(username)&&!StringUtils.isEmpty(password)){
            String token = UUID.randomUUID().toString();
            Cookie ssoToken = new Cookie("sso_token", token);
            response.addCookie(ssoToken);
            //返回原来的页面
            return "redirect:"+redirect_url+"?token="+token;
        }
        //失败就重新登录
        return "loginPage";
    }
}
