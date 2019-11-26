package com.zwp.travelmemories.web;

import com.zwp.travelmemories.web.controller.VersionCheckInterception;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: travelmemories
 * @description: Mvc配置文件
 * @author: zwp-flyz
 * @create: 2019-11-26 13:31
 * @version: v1.0
 **/
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Bean
    public VersionCheckInterception versionCheckInterception(){
        return new VersionCheckInterception();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(versionCheckInterception())
                .addPathPatterns("/**");
    }
}
