package com.example.takeawaybackend.tool;

import lombok.Data;

@Data
public class DataResult <T>{
    private int code;
    private T data;
    private String message;
    public static <T> DataResult<T> success(T data){
        DataResult<T> result=new DataResult<>();
        result.data=data;
        result.code= ResponseCode.SUCCESS.getCode();
        result.message=ResponseCode.SUCCESS.getMessage();
        return result;
    }
    public static <T> DataResult<T> fail(ResponseCode r){
        DataResult<T> result=new DataResult<>();
        result.code= r.getCode();
        result.message=r.getMessage();
        return result;
    }
    public static <T> DataResult<T> failMessage(){
        DataResult<T> result=new DataResult<>();
        result.code= ResponseCode.ACCOUNT_ERROR.getCode();
        result.message=ResponseCode.ACCOUNT_ERROR.getMessage();
        return result;
    }
}