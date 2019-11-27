package com.zwp.travelmemories.web.controller;

import com.zwp.travelmemories.web.vo.UserDetailVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @program: travelmemories
 * @description: 父类控制器
 * @author: zwp-flyz
 * @create: 2019-11-27 16:03
 * @version: v1.0
 **/
public class BaseController {

    /**
     * 获取当前登录的用户信息
     * @return
     */
    protected UserDetailVo getCurrentLoginUserInfo(){
        Authentication aut =
                SecurityContextHolder.getContext().getAuthentication();
        return  (UserDetailVo)aut.getPrincipal();
    }

}
