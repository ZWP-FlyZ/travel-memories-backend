package com.zwp.travelmemories;

import com.zwp.travelmemories.repo.TestRepo;
import com.zwp.travelmemories.service.TestService;

/**
 * @program: travelmemories
 * @description: 测试web应用
 * @author: zwp-flyz
 * @create: 2019-11-20 13:14
 * @version: v1.0
 **/
public class TestWebApp {

    public static void dosome(){
        System.err.println("HI,this is TestWebApp");
        TestService.dosome();
    }
}
