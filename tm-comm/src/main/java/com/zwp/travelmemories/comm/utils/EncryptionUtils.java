package com.zwp.travelmemories.comm.utils;

import org.springframework.util.DigestUtils;

/**
 * @program: travelmemories
 * @description: 加密工具
 * @author: zwp-flyz
 * @create: 2019-11-26 19:52
 * @version: v1.0
 **/
public class EncryptionUtils {

    public static String md5Encoder(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    // salt
    public final static String SALT_SPILT="-";

    public static String salt(){
        String s = "zwp-"+System.currentTimeMillis()+"-N_yZ-"+Math.random();
        return md5Encoder(s).substring(0,9);
    }

    public static String encrypt(String password,String salt){
        StringBuilder sb = new StringBuilder();
        sb.append(salt.charAt(0))
                .append(password.charAt(password.length()-1))
                .append(password)
                .append(password.charAt(0))
                .append(salt.charAt(salt.length()-1))
                .append(salt.substring(salt.length()/2));
        String t = sb.toString();
        t = md5Encoder(t);
        t = md5Encoder(t+sb.toString());
        return t;
    }



}
