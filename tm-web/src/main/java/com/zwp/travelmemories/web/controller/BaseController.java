package com.zwp.travelmemories.web.controller;

import com.zwp.travelmemories.web.vo.ResponseResult;
import com.zwp.travelmemories.web.vo.UserDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: travelmemories
 * @description: 父类控制器
 * @author: zwp-flyz
 * @create: 2019-11-27 16:03
 * @version: v1.0
 **/
public abstract class BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger("ExceptionHandler");
    /**
     * 获取当前登录的用户信息
     * @return
     */
    protected UserDetailVo getCurrentLoginUserInfo(){
        Authentication aut =
                SecurityContextHolder.getContext().getAuthentication();
        return  (UserDetailVo)aut.getPrincipal();
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseResult unknownException(HttpServletRequest request, Exception e){
        LOGGER.error("error in url ["+request.getRequestURI()+"] ",e);
        return ResponseResult.failure(e.getMessage());
    }

}
