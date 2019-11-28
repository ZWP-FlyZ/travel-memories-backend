package com.zwp.travelmemories.web;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: travelmemories
 * @description: 检查操作的用户id是否与自身用户id相同
 * @author: zwp-flyz
 * @create: 2019-11-28 14:45
 * @version: v1.0
 **/
public class UserIdCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        return false;
    }
}
