package com.zwp.travelmemories.web.vo;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @program: travelmemories
 * @description: Response返回结果
 * @author: zwp-flyz
 * @create: 2019-11-26 09:57
 * @version: v1.0
 **/
@Data
public class ResponseResult<T> extends AbstractResponseResult {

    private T data=null;

    public ResponseResult(ResponseCodes status, String message,T data) {
        super(status, message);
        this.data=data;
    }

    public ResponseResult(ResponseCodes status,T data) {
        super(status);
        this.data=data;
    }


    /**
     * 返回一个自定义信息的成功的回应结果
     * @param message 自定义消息
     * @return
     */
    public static ResponseResult success(String message){
        return new ResponseResult(ResponseCodes.OK,message,null);
    }

    /**
     * 返回一个成功的回应结果
     * @return
     */
    public static ResponseResult success(){
        return new ResponseResult(ResponseCodes.OK,null);
    }

    public static ResponseResult failure(String message){
        return new ResponseResult(ResponseCodes.FAILURE,message,null);
    }

    public static ResponseResult failure(){
        return new ResponseResult(ResponseCodes.FAILURE,null);
    }

}
