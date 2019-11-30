package com.zwp.travelmemories.web.vo;

/**
 * @program: travelmemories
 * @description: 状态码枚举
 * @author: zwp-flyz
 * @create: 2019-11-26 09:04
 * @version: v1.0
 **/
public enum ResponseCodes {


    // 通用状态码-10xx
    OK(1000,"成功"),
    FAILURE(1001,"失败"),
    VERSION_NOT_MATCHED(1002,"通信协议版本不匹配"),
    PERMISSION_DENIED(1003,"访问未授权资源"),
    UNAUTHORIZED(1004,"未认证登录"),
    OP_PERMISSION_DENIED(1312,"用户无权操作目标事件点"),
    PARAM_FORMATTING_ERROR(1006,"参数格式不符合要求"),

    // 用户注册相关-11xx
    LOGON_USERNAME_REPEAT(1102,"用户名重复"),

    // 用户登录相关-12xx
    LOGIN_USERNAME_NOT_EXIST(1202,"用户名不存在"),
    LOGIN_PASSWORD_ERROR(1203,"用户名或密码错误"),

    // 用户登出相关
    // 略

    // 事件点创建相关-130x

    // 事件点操作通用部分-131x
    EP_OP_FAILURE_LOCKED(1311,"事件点已锁定，操作失败"),



    // 事件点锁定与解锁-132x
    EP_LOCKED(1320,"事件点加锁成功"),
    EP_UNLOCKED(1321,"事件点解锁成功");

    // 事件点设置相关-133x


    // 事件点文本信息更改-134x


    //事件点媒体信息相关-135x



    private int code;
    private String message;

    private ResponseCodes(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode(){return this.code;}
    public String getMessage(){return this.message;}
    public String getName(){return this.name();}



}
