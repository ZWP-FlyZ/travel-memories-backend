package com.zwp.travelmemories.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: travelmemories
 * @description: 用户未登录访问资源时的异常处理器
 * @author: zwp-flyz
 * @create: 2019-11-22 11:41
 * @version: v1.0
 **/
public class UnAuthorizedHandler implements AuthenticationEntryPoint {

    private final static Logger LOGGER = LoggerFactory.getLogger(UnAuthorizedHandler.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        LOGGER.debug("user unauthorized"+e.getMessage());
    }
}
