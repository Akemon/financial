package com.hk.manager.error;

/**
 * @author 何康
 * @date 2018/9/14 18:47
 */

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/***
 * 自定义错误处理controller
 */
public class MyErrorController extends BasicErrorController{


    public MyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        Map<String,Object> attrs =  super.getErrorAttributes(request, includeStackTrace);
        attrs.remove("timestamp");
        attrs.remove("status");
        attrs.remove("error");
        attrs.remove("path");
        attrs.remove("exception");

        //获取错误码
        String code = (String) attrs.get("message");
        ErrorEnum errorEnum = ErrorEnum.getByCode(code);
        attrs.put("message",errorEnum.getMessage());
        attrs.put("code",errorEnum.getCode());
        attrs.put("canRetry",errorEnum.isCanRetry());
        return attrs;
    }
/**
     *
     * {
     "timestamp": "2018-09-14 18:48:49",
     "status": 500,
     "error": "Internal Server Error",
     "message": "编号不能为空",
     "path": "/manager/product/addProduct"
     }
     */
}
