package com.zwp.travelmemories.web;

import com.zwp.travelmemories.comm.utils.GsonUtils;
import com.zwp.travelmemories.web.vo.Gkeys;
import com.zwp.travelmemories.web.vo.ResponseCodes;
import com.zwp.travelmemories.web.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: travelmemories
 * @description: 通信协议版本检查和设置的过滤器
 * @author: zwp-flyz
 * @create: 2019-11-26 17:55
 * @version: v1.0
 **/

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class VersionCheckFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(VersionCheckFilter.class);

    @Value("${c-version}")
    String version;

    private byte[] errMsg;

    public VersionCheckFilter(){
        ResponseResult ff =
                new ResponseResult(ResponseCodes.VERSION_NOT_MATCHED,null);
        errMsg = GsonUtils.toJson(ff).getBytes();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        String remotev = req.getHeader(Gkeys.C_VERSION_NAME);
        resp.setHeader(Gkeys.C_VERSION_NAME,version);
        if(req.getRequestURI().
                contains("epoint/files"))
            chain.doFilter(request,response);
        else if(remotev==null||!remotev.equals(version)){
            LOGGER.debug("c-version check for "+req.getRequestURI()
                    +" not match! cur:{} remote:{}",version,remotev);
            resp.setHeader("Content-Type","application/json");

            // 版本不匹配
            try(OutputStream os = resp.getOutputStream()){
                os.write(errMsg);
                os.flush();
            }
        }else
            // 版本匹配继续执行
            chain.doFilter(request,response);

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
        errMsg=null;
    }
}
