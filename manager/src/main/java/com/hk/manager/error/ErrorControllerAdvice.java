package com.hk.manager.error;

/**
 * @author 何康
 * @date 2018/10/3 17:19
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.Expression;
import java.util.HashMap;
import java.util.Map;

/***
 * 统一错误处理异常，此种方式是将controller外层包裹一层,如果controller出现异常将直接进入此类进行对应的处理
 */
@ControllerAdvice(basePackages = "com.hk.manager.controller")
@Slf4j
public class ErrorControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception e){
        e.printStackTrace();
        Map<String,Object> attrs =  new HashMap<>();
        attrs.remove("timestamp");
        attrs.remove("status");
        attrs.remove("error");
        attrs.remove("path");
        attrs.remove("exception");
        //获取错误码
        String code = e.getMessage();
//        System.out.println("code:"+code);
//        System.out.println("attrs:"+attrs);
        ErrorEnum errorEnum = ErrorEnum.getByCode(code);
        attrs.put("message",errorEnum.getMessage());
        attrs.put("code",errorEnum.getCode());
        attrs.put("canRetry",errorEnum.isCanRetry());
        //用于区别继承BasicErrorController的错误处理
        attrs.put("type","controllerAdvice");
        return new ResponseEntity(attrs, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
