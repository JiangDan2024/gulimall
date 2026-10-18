package com.gulimall.auth.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gulimall.auth.component.OAuthWeiboComponent;
import com.gulimall.auth.feign.MemberFeign;
import com.gulimall.common.constant.AuthConstants;
import com.gulimall.common.vo.MemberVo;
import com.gulimall.auth.vo.SocialUser;
import com.gulimall.common.core.domain.AjaxResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class OAuth2Controller {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OAuthWeiboComponent weiboComponent;
    @Autowired
    private MemberFeign memberFeign;

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam String code, HttpSession session){
        //用code获取授权，获得accessToken
        //使用redisTemplate构建一个获取微博授权的请求
        String url = "https://api.weibo.com/oauth2/access_token";
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //设置参数
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id",weiboComponent.getClientId());
        map.add("client_secret",weiboComponent.getClientSecret());
        map.add("grant_type","authorization_code");
        map.add("code",code);
        map.add("redirect_uri",weiboComponent.getRedirectUri());
        //组装请求
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        //发起请求
        ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);
        if(result.getStatusCode().is2xxSuccessful()){
            //判断是否获取accessToken
            String body = result.getBody();
            SocialUser socialUser = JSON.parseObject(body, SocialUser.class);
            //远程调用Member服务，实现社交登录的自动注册或登录
            AjaxResult ajaxResult = memberFeign.oauth2loggin(socialUser);
            if(ajaxResult.isSuccess()){
                MemberVo data = ajaxResult.getData(new TypeReference<MemberVo>() {
                });
                session.setAttribute(AuthConstants.LOGIN_USER,data);
                //社交登录成功后返回首页地址
                return "redirect:http://gulimall.com";
            }else{
                return "redirect:http://auth.gulimall.com/login.html";
            }
        }else{
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }

    @GetMapping("/testSession")
    @ResponseBody
    public Object testSession(HttpSession session) {
        Object member = session.getAttribute("member");
        System.out.println("取出的数据是：" + member); // 如果输出 null，才是真的没存进去
        return member;
    }
}
