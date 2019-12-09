package com.zwp.travelmemories.web.security;

import com.zwp.travelmemories.comm.utils.GsonUtils;
import com.zwp.travelmemories.web.vo.ResponseCodes;
import com.zwp.travelmemories.web.vo.ResponseResult;
import com.zwp.travelmemories.web.vo.UserDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: travelmemories
 * @description: 登出成功处理器
 * @author: zwp-flyz
 * @create: 2019-12-09 14:38
 * @version: v1.0
 **/
public class LogoutSuccessHandlerImp implements LogoutSuccessHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(UnAuthorizedHandler.class);

    @Value("${c-version}")
    String version;

    final byte[] errMsg;

    LogoutSuccessHandlerImp(){
        ResponseResult res = ResponseResult.success("登出成功");
        errMsg = GsonUtils.toJson(res).getBytes();
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        UserDetailVo user = (UserDetailVo)authentication.getPrincipal();
        LOGGER.info("username:[{}],ip:[{}],port:[{}] logout success",
                request.getParameter("username"),request.getRemoteHost(),
                request.getRemotePort());
        response.setHeader("Content-Type","application/json");
        try(OutputStream os = response.getOutputStream()) {
            os.write(errMsg);
            os.flush();
        }
    }
}
