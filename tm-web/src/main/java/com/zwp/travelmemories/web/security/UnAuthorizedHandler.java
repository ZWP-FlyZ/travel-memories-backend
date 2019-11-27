package com.zwp.travelmemories.web.security;

import com.zwp.travelmemories.comm.utils.GsonUtils;
import com.zwp.travelmemories.web.vo.ResponseCodes;
import com.zwp.travelmemories.web.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: travelmemories
 * @description: 用户未登录访问资源时的异常处理器
 * @author: zwp-flyz
 * @create: 2019-11-22 11:41
 * @version: v1.0
 **/
public class UnAuthorizedHandler implements AuthenticationEntryPoint {

    private final static Logger LOGGER = LoggerFactory.getLogger(UnAuthorizedHandler.class);

    @Value("${c-version}")
    String version;

    final byte[] errMsg;

    public UnAuthorizedHandler(){
        ResponseResult res = new ResponseResult(ResponseCodes.UNAUTHORIZED,
                "用户未登录",null);
        errMsg = GsonUtils.toJson(res).getBytes();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        LOGGER.info("unauthorized->username:[{}],ip:[{}],port:[{}]",
                request.getParameter("username"),request.getRemoteHost(),request.getRemotePort());
        response.setHeader("Content-Type","application/json");
        try(OutputStream os = response.getOutputStream()) {
            os.write(errMsg);
            os.flush();
        }

    }
}
