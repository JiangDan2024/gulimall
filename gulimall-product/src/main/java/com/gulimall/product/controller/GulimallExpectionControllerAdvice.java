package com.gulimall.product.controller;

import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.exception.BizCodeEnume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.gulimall.product.controller")
public class GulimallExpectionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public AjaxResult handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{},异常类型：{}",e.getMessage(),e.getCause());
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return AjaxResult.error().put("code", BizCodeEnume.VAILD_EXCEPTION.getCode()).put("msg",BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data",errors);
    }

    @ExceptionHandler(value = Throwable.class)
    public AjaxResult handleException(Throwable e) {
        log.error(BizCodeEnume.UNKNOW_EXCEPTION.getMsg(),e);
        return AjaxResult.error().put("code",BizCodeEnume.UNKNOW_EXCEPTION.getCode()).put("msg",BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }
}
