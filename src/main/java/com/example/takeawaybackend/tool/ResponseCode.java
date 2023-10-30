package com.example.takeawaybackend.tool;

public enum ResponseCode {
    SUCCESS(0,"响应成功"),
    //后端服务异常以500开头
    SYSTEM_ERROR(500000,"服务异常，请稍后再试"),
    OPERATION_ERROR(500001,"操作失败，请稍后再试"),
    //后端服务异常以400开头
    DATA_PARAM_ERROR(400000,"传入参数错误"),
    ACCOUNT_ALREADY_EXISTS(400001,"账号已存在，请登录"),
    ACCOUNT_NOT_FOUND(400002,"账号不存在"),

    ACCOUNT_LOCK(400003,"账号已锁定，请联系管理员解锁"),
    ACCOUNT_ERROR(400004,"账户密码不匹配"),
    EMAIL_ERROR(400005,"该邮箱已注册"),
    EMAIL_NOT_FOUND(400006,"邮箱不存在"),
    CODE_ERROR(400006,"验证码不正确"),
    NICKNAME_REPEAT(400007,"该昵称已被注册"),
    TOKEN_ERROR(401000,"token 已失效,请重新登录")
    ;
    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
