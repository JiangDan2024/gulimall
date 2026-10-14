package com.gulimall.member.vo;

import lombok.Data;

@Data
public class UserRegisterVo {
    private String userName;
    //密码
    private String password;
    //电话
    private String phone;
    //验证码
    private String code;
}
