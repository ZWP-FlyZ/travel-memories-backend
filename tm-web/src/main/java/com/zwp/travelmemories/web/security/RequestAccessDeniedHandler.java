package com.zwp.travelmemories.web.security;

import com.zwp.travelmemories.comm.utils.GsonUtils;
import com.zwp.travelmemories.web.vo.ResponseCodes;
import com.zwp.travelmemories.web.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: travelmemories
 * @description: 用户访问权限级别以外资源时的异常处理器
 * @author: zwp-flyz
 * @create: 2019-11-22 11:43
 * @version: v1.0
 **/
public class RequestAccessDeniedHandler implements AccessDeniedHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestAccessDeniedHandler.class);

    @Value("${c-version}")
    public String version;

    private final byte[] errMsg ;

    public RequestAccessDeniedHandler(){
        ResponseResult res = new ResponseResult(ResponseCodes.PERMISSION_DENIED,
                "用户访问受限资源",null);
        errMsg = GsonUtils.toJson(res).getBytes();
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        LOGGER.info("permission_denied->username:[{}],ip:[{}],port:[{}]",
                request.getParameter("username"),request.getRemoteHost(),request.getRemotePort());
        response.setHeader("Content-Type","application/json");
        // 写入返回内容
        try(OutputStream os = response.getOutputStream()) {
            os.write(errMsg);
            os.flush();
        }

    }
}
