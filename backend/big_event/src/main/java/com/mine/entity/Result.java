package com.mine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//数据返回响应类型
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor
public class Result<T> {

    private int code;//状态码
    private String message;//提示词
    private T data;//响应数据

    public static <T> Result<T> success() {
        return new Result<>(200,"操作成功",null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200,"操作成功",data);
    }
    public static <T> Result<T> error(String message) {
        return new Result<>(500,message,null);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code,message,null);
    }


}