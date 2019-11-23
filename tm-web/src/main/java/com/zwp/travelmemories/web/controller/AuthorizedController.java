package com.zwp.travelmemories.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: travelmemories
 * @description: 处理用户登录和登出的控制器
 * @author: zwp-flyz
 * @create: 2019-11-22 11:48
 * @version: v1.0
 **/
@RestController
public class AuthorizedController {

    @PostMapping("/login")
    public String login(){
        return "hi";
    }

}
