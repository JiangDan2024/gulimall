package com.gulimall.thirdparty.controller;

import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thirdparty/sms")
public class SmsController {

    @Autowired
    SmsComponent smsComponent;

    @GetMapping("/sendCode")
    public AjaxResult sendCode(@RequestParam String phone, @RequestParam String code){
        smsComponent.sendSmsCode(phone,code);
        return AjaxResult.success();
    }
}
