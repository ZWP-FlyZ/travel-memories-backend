package com.zwp.travelmemories.web.controller;

import com.zwp.travelmemories.web.vo.Gkeys;
import com.zwp.travelmemories.web.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @program: travelmemories
 * @description: 为所有控制器的response添加通信版本
 * @author: zwp-flyz
 * @create: 2019-11-26 12:16
 * @version: v1.0
 **/
@ControllerAdvice("com.zwp.travelmemories.web.controller")
public class VersionSetAdvice implements ResponseBodyAdvice<ResponseResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(VersionSetAdvice.class);
    @Value("${c-version}")
    public String version;

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public ResponseResult beforeBodyWrite(ResponseResult body, MethodParameter returnType,
                                          MediaType selectedContentType,
                                          Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                          ServerHttpRequest request, ServerHttpResponse response) {
        LOGGER.info(body.toString());
        response.getHeaders().add(Gkeys.C_VERSION_NAME,version);
        return body;
    }

}
