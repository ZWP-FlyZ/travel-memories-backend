package com.zwp.travelmemories.web.controller;

import com.zwp.travelmemories.web.vo.ResponseResult;
import com.zwp.travelmemories.web.vo.UserDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: travelmemories
 * @description: 处理用户登录和登出的控制器
 * @author: zwp-flyz
 * @create: 2019-11-22 11:48
 * @version: v1.0
 **/
@RestController
public class AuthorizedController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorizedController.class);

    @PostMapping("/login")
    public ResponseResult login(HttpServletRequest request){
        Authentication aut =
                SecurityContextHolder.getContext().getAuthentication();
        UserDetailVo user = (UserDetailVo)aut.getPrincipal();
        try{
            // 登录成功后的操作
            LOGGER.info("login success->username:[{}],ip:[{}],port:[{}]",
                    user.getUsername(),request.getRemoteHost(),request.getRemotePort());
        }catch (Exception e){
            // 更新登录信息失败
            LOGGER.error("unknown error -> username:{} ",user.getUsername());
            LOGGER.error("update login status error. ",e);

            // 重要，将保留在holder中的认证信息清空
            SecurityContextHolder.getContext().setAuthentication(null);
            return ResponseResult.failure("登录失败！");
        }
        return ResponseResult.success();
    }

    @PostMapping("/login_failure")
    public ResponseResult login_failure(HttpServletRequest request){
        String username = request.getParameter("username");
        LOGGER.info("login failure->username:[{}],ip:[{}],port:[{}]",
                username,request.getRemoteHost(),request.getRemotePort());
        return ResponseResult.failure("用户名或者密码错误");
    }

}
