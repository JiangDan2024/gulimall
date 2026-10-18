package com.gulimall.auth.web;

import com.alibaba.fastjson.TypeReference;
import com.gulimall.auth.feign.MemberFeign;
import com.gulimall.auth.feign.ThirdPartFeign;
import com.gulimall.auth.vo.UserLoginVo;
import com.gulimall.auth.vo.UserRegisteVo;
import com.gulimall.common.constant.AuthConstants;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.exception.BizCodeEnume;
import com.gulimall.common.utils.StringUtils;
import com.gulimall.common.utils.uuid.UUID;
import com.gulimall.common.vo.MemberVo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    @Autowired
    ThirdPartFeign thirdPartFeign;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    MemberFeign memberFeign;

    @ResponseBody
    @GetMapping("/sendCode")
    public AjaxResult sendCode(@RequestParam String phone){
        //TODO 接口防刷和验证码再次校验
        //验证该手机号是否在60s内发送过验证码
        String codeCache = stringRedisTemplate.opsForValue().get(AuthConstants.SMS_CODE_CACHE_PREFIX + phone);
        if(StringUtils.isNotEmpty(codeCache)){
            String[] split = codeCache.split("_");
            long l = Long.parseLong(split[1]);
            if(System.currentTimeMillis()-l<60*1000){
                return new AjaxResult(BizCodeEnume.SMS_EXCEPTION.getCode(),BizCodeEnume.SMS_EXCEPTION.getMsg());
            }
        }
        //生成一个验证码
        String code = UUID.randomUUID().toString().substring(0, 5);
        //避免重复发送验证码，存入的code加上时间戳
        //验证码再次校验，存入redis加上过期时间10min
        stringRedisTemplate.opsForValue().set(AuthConstants.SMS_CODE_CACHE_PREFIX+phone, code+"_"+System.currentTimeMillis(),10, TimeUnit.MINUTES);
        //发送验证码
        thirdPartFeign.sendCode(phone,code);
        return AjaxResult.success();
    }

    //注册方法
    @PostMapping("/register")
    public String register(@Valid UserRegisteVo registe, BindingResult result, RedirectAttributes redirectAttributes){
        //TODO 重定向携带数据，利用session原理，将数据放在session中，要解决分布式下session问题
        //如果校验有错，就返回给前端回显
        if(result.hasErrors()){
            //把errors封装成一个Map返回
            Map<String, @Nullable String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (msg1, msg2) -> msg1 + "，" + msg2 ));
            redirectAttributes.addFlashAttribute("errors",errors);
            //校验出错，前端返回到注册页
            //forward转发是用原来的post请求，但reg是get方法，因此换成redirect
            return "redirect:http://auth.gulimall.com/reg.html";
        }
        //校验验证码是否一致
        String codeCache = stringRedisTemplate.opsForValue().get(AuthConstants.SMS_CODE_CACHE_PREFIX + registe.getPhone());
        if(StringUtils.isNotEmpty(codeCache)){
            String[] split = codeCache.split("_");
            if(split[0].equals(registe.getCode())){
                //删除redis的code，避免被重复使用
                stringRedisTemplate.delete(AuthConstants.SMS_CODE_CACHE_PREFIX+registe.getPhone());
                //TODO 远程调用注册方法
                AjaxResult r = memberFeign.register(registe);
                if(r.isSuccess()){
                    //注册成功回到登录页面
                    return "redirect:http://auth.gulimall.com/login.html";
                }else{
                    Map<String, String> errors = new HashMap<>();
                    errors.put("msg",r.getData("msg",new TypeReference<String>() {}));
                    redirectAttributes.addFlashAttribute("errors",errors);
                    return "redirect:http://auth.gulimall.com/reg.html";
                }
            }else{
                Map<String, String> errors = new HashMap<>();
                errors.put("code","验证码错误");
                redirectAttributes.addFlashAttribute("errors",errors);
                return "redirect:http://auth.gulimall.com/reg.html";
            }
        }else{
            Map<String, String> errors = new HashMap<>();
            errors.put("code","验证码已失效");
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.gulimall.com/reg.html";
        }
    }

    @PostMapping("/login")
    public String login(UserLoginVo loginVo, RedirectAttributes redirectAttributes, HttpSession session){
        //远程调用登录方法
        AjaxResult login = memberFeign.login(loginVo);
        if(login.isSuccess()){
            //登录成功返回首页
            MemberVo data = login.getData(new TypeReference<MemberVo>() {
            });
            session.setAttribute(AuthConstants.LOGIN_USER,data);
            return "redirect:http://gulimall.com";
        }else{
            Map<String, String> errors = new HashMap<>();
            errors.put("msg",login.getData("msg",new TypeReference<String>() {}));
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }

    //登录请求，需要判断是否已登录过，如果登陆过，就直接跳转到首页，否则再登录
    @GetMapping("/login.html")
    public String login(HttpSession session){
        if(session.getAttribute(AuthConstants.LOGIN_USER)==null){
            return "login";
        }else{
            return "redirect:http://gulimall.com";
        }
    }

}
