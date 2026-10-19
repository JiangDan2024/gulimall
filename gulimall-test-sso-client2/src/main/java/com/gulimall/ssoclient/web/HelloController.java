package com.gulimall.ssoclient.web;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

//    @Autowired
//    RestTemplate restTemplate;

    //无需密码即可访问
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    //需要登录
    @GetMapping("/boss")
    public String employees(Model model, HttpSession session,
                            @RequestParam(value = "token",required = false) String token){
        if(!StringUtils.isEmpty(token)){
            //是否携带tooken，是则已登录
            //TODO 去ssoserver获取当前token真正对应的用户信息
            session.setAttribute("loginUser",token);
//            restTemplate.getForEntity("",String.class);
        }
        //判断是否登录
        Object loginUser = session.getAttribute("loginUser");
        if(loginUser==null){
            //没登录，重定向到登录页面
            return "redirect:http://ssoserver.com:8080/loginPage?redirect_url=http://client2.com:8082/boss";
        }else{
            //登录了才能访问页面
            List<String> names = new ArrayList<>();
            names.add("张三");
            names.add("李四");
            model.addAttribute("employees",names);
            return "employees";
        }
    }
}
