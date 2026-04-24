package com.example.demo.common;

import lombok.Data;

@Data
public class Result<T> {
    private int code; // 业务状态码 0 表示成功，500 表示失败
    private String message; // 描述信息
    private T data; // 数据

    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<T>();
        r.setCode(0);
        r.setData(object);;
        r.setMessage("成功");
        return r;
    }


    public static <T> Result<T> success() {
        Result<T> r = new Result<>();
        r.setCode(0);
        r.setMessage("成功");
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMessage(msg);
        return r;
    }
}
