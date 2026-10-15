package com.gulimall.member.vo;

import lombok.Data;

@Data
public class SocialUser {
    private String accessToken;
    private String uid;
    private Integer expiresIn;
}
