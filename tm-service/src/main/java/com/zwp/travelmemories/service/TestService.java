package com.zwp.travelmemories.service;

import com.zwp.travelmemories.comm.TestUtils;
import com.zwp.travelmemories.repo.TestRepo;

/**
 * @program: travelmemories
 * @description: 测试服务
 * @author: zwp-flyz
 * @create: 2019-11-20 12:55
 * @version: v1.0
 **/
public class TestService {

    public static void dosome(){
        System.err.println("Hi,this is TestService");
        TestRepo.dosome();
    }

}
