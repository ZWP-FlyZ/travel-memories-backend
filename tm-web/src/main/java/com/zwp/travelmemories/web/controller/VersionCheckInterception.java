package com.zwp.travelmemories.web.controller;

import com.zwp.travelmemories.comm.utils.GsonUtils;
import com.zwp.travelmemories.web.vo.Gkeys;
import com.zwp.travelmemories.web.vo.ResponseCodes;
import com.zwp.travelmemories.web.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @program: travelmemories
 * @description: 为所有控制器的request检查版本
 * @author: zwp-flyz
 * @create: 2019-11-26 13:18
 * @version: v1.0
 **/

public class VersionCheckInterception implements HandlerInterceptor {

    private final static Logger LOGGER = LoggerFactory.getLogger(VersionCheckInterception.class);

    @Value("${c-version}")
    String version;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        LOGGER.debug("c-version check for "+request.getRequestURI());
        String remoteVersion = request.getHeader(Gkeys.C_VERSION_NAME);
        // 版本不匹配
        if(remoteVersion==null||
                !version.equals(remoteVersion)){
            // 设置通信版本号
            response.addHeader(Gkeys.C_VERSION_NAME,version);
            try(OutputStream os = response.getOutputStream()) {
                ResponseResult res =
                        new ResponseResult(ResponseCodes.VERSION_NOT_MATCHED,null);
                os.write(GsonUtils.toJson(res).getBytes());
                os.flush();
            }
            return false;
        }
        return true;
    }
}
