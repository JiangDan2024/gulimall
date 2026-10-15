package com.gulimall.auth.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oauth2.weibo")
public class OAuthWeiboComponent {
    //App Key：
    private String clientId;
    //App Secret：
    private String clientSecret;
    //授权回调页地址
    private String redirectUri;
}
