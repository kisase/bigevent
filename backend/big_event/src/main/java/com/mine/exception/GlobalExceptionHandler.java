package com.mine.exception;

import com.mine.entity.Result;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        e.printStackTrace();
        //StringUtils.hasLength()判断字符串非空。
        //e.getMessage() ; 只会获得异常的名称。如：需要匹配正则表达式\"^\\S{5,16}$\""
        //三元判断
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
