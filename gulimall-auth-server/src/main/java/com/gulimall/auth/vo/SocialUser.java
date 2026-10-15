package com.gulimall.auth.vo;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class SocialUser {
    private String accessToken;
    private String uid;
    private Integer expiresIn;
}
