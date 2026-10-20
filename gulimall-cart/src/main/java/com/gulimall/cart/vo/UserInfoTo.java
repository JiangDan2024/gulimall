package com.gulimall.cart.vo;

import lombok.Data;

@Data
public class UserInfoTo {
    private String userId;
    private String userKey;

    private Boolean tempUser=false;
}
