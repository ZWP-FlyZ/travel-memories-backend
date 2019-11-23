package com.zwp.travelmemories.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: travelmemories
 * @description: 用户访问权限级别以外资源时的异常处理器
 * @author: zwp-flyz
 * @create: 2019-11-22 11:43
 * @version: v1.0
 **/
public class RequestAccessDeniedHandler implements AccessDeniedHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        LOGGER.debug("user denied "+e.getMessage());
    }
}
