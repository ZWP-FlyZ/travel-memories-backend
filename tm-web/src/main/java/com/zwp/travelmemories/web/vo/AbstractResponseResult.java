package com.zwp.travelmemories.web.vo;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @program: travelmemories
 * @description: 返回结果的抽象类
 * @author: zwp-flyz
 * @create: 2019-11-26 09:50
 * @version: v1.0
 **/
@Getter
public abstract class AbstractResponseResult {

    private int code;
    private String message;

    private AbstractResponseResult(int code,String message){
        this.code=code;
        this.message=message;
    }

    public AbstractResponseResult(ResponseCodes status,String message){
        this(status.getCode(),message);
    }

    public AbstractResponseResult(ResponseCodes status){
        this(status.getCode(),status.getMessage());
    }

}
