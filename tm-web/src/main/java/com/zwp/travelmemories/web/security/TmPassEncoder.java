package com.zwp.travelmemories.web.security;

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
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence);
    }
}
