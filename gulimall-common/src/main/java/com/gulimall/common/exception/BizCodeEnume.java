package com.gulimall.common.exception;

import lombok.Getter;

/**
 * 错误码列表：
 * 10通用
 *      001：参数格式校验
 *      002：短信验证码频率太高
 * 11：商品
 * 12：订单
 * 13：购物车
 * 14：物流
 * 15：用户
 */

@Getter
public enum BizCodeEnume {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VAILD_EXCEPTION(10001,"参数格式校验失败"),
    SMS_EXCEPTION(10002,"验证码发送频率太高，请稍后再试"),
    USER_EXIST_EXCEPTION(15001,"用户已存在"),
    PHONE_EXIST_EXCEPTION(15002,"手机号已存在"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架失败");

    private int code;
    private String msg;
    BizCodeEnume(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
