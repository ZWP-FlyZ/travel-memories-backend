package com.zwp.travelmemories.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: travelmemories
 * @description: 用户登录失败处理器
 * @author: zwp-flyz
 * @create: 2019-11-22 11:45
 * @version: v1.0
 **/
public class LoginFailHandler implements AuthenticationFailureHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginFailHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {

        String username = httpServletRequest.getParameter("username");
        LOGGER.debug("user:{} login failure! message="+e.getMessage(),username);
    }
}
