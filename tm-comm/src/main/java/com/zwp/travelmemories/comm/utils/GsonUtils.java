package com.zwp.travelmemories.comm.utils;

import com.google.gson.Gson;

/**
 * @program: travelmemories
 * @description: 多线程安全的gson工具
 * @author: zwp-flyz
 * @create: 2019-11-23 15:28
 * @version: v1.0
 **/
public class GsonUtils {

    private final static Gson gson = new Gson();

    /**
     * 将对象转换为json字符串，多线程安全
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    /**
     * 将json字符串 转换为T类型对象，多线程安全
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json,Class<T> tClass){
        return gson.fromJson(json,tClass);
    }

}
