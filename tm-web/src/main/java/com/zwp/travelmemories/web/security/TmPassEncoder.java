package com.zwp.travelmemories.web.security;

import com.zwp.travelmemories.comm.utils.EncryptionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: travelmemories
 * @description: 用户密码加密器
 * @author: zwp-flyz
 * @create: 2019-11-22 10:20
 * @version: v1.0
 **/
public class TmPassEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        String salt = EncryptionUtils.salt();
        String t = EncryptionUtils.encrypt(rawPassword.toString(),salt);
        return t+EncryptionUtils.SALT_SPILT+salt;
    }

    @Override
    public boolean matches(CharSequence rawPass, String encodedPass) {
        if(rawPass==null||rawPass.toString().equals(""))
            return false;
        String[] passSalt =  encodedPass.split(EncryptionUtils.SALT_SPILT);
        if(passSalt.length==1){
            return rawPass.toString().equals(encodedPass);
        }else if(passSalt.length==2){
            String ec = EncryptionUtils.encrypt(rawPass.toString(),passSalt[1]);
            return ec.equals(passSalt[0]);
        }else return false;
    }
}
