package com.zwp.travelmemories.web.controller;

import com.zwp.travelmemories.service.TestService;
import com.zwp.travelmemories.web.vo.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: travelmemories
 * @description: 测试控制器
 * @author: zwp-flyz
 * @create: 2019-11-20 15:15
 * @version: v1.0
 **/
@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/dosome")
    @ResponseBody
    public ResponseResult doTest(HttpServletRequest request){

        TestService.dosome();
        return ResponseResult.success(request.toString());
    }

}
