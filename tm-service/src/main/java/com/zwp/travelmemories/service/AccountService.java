package com.zwp.travelmemories.service;

import com.zwp.travelmemories.comm.vo.UserVo;
import org.springframework.stereotype.Service;

/**
 * @program: travelmemories
 * @description: 账户相关服务
 * @author: zwp-flyz
 * @create: 2019-11-23 15:02
 * @version: v1.0
 **/
@Service
public class AccountService {

    /**
     * 通过username获取到用户信息，专用于登录
     * @param username
     * @return 如果用户不存在或者username等于null则返回null，
     * 否则正确返回用户信息
     */
    public UserVo getUserByUsernameForLogin(String username){

        if(username==null) return null;

        UserVo vo = new UserVo();
        vo.setUId(123123123L);
        vo.setUsername(username);
        vo.setPassword("123456");
        vo.setRoles("USER");
        vo.setState(0);
        return vo;
    }


}
