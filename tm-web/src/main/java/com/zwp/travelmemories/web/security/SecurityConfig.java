package com.zwp.travelmemories.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: travelmemories
 * @description: Spring Security 配置文件
 * @author: zwp-flyz
 * @create: 2019-11-22 09:57
 * @version: v1.0
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注册用户细节服务
     * @return
     */
    @Bean
    public UserDetailsService accountProvider(){
        return new AccountProvider();
    }

    /**
     * 注册加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new TmPassEncoder();
    }

    @Bean
    public UnAuthorizedHandler unAuthorizedHandler(){
        return new UnAuthorizedHandler();
    }

    @Bean
    public RequestAccessDeniedHandler requestAccessDeniedHandler(){
        return new RequestAccessDeniedHandler();
    }


    /**
     * 配置http相关内容
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable();

        http
                //设置注册相关请求的设置
                .authorizeRequests()
                    //选择注册的域名，并且允许所有人（包括未登录人）都可以访问
                    .antMatchers("/reg/**").permitAll()
                    //所有已经登录的人（任意权限的人）都可以访问
                    .anyRequest().authenticated()
                    .and()

                //登录相关配置
                .formLogin()
                    // 登录域名配置
                    .loginProcessingUrl("/login")
                    //认证成功后向控制层转发的域名
                    .successForwardUrl("/login")
                    // 认证失败时处理器
                    .failureForwardUrl("/login_failure")
//                    .failureHandler(new LoginFailHandler())
                    //允许所有人（包括未登录人）都可以访问
                    .permitAll()
                    .and()
                //其他基础配置
                .httpBasic();

        // 登录与访问时异常处理
        http.exceptionHandling()
                .authenticationEntryPoint(unAuthorizedHandler())
                .accessDeniedHandler(requestAccessDeniedHandler());
    }
}
