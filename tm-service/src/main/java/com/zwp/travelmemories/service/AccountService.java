package com.zwp.travelmemories.service;

import com.zwp.travelmemories.comm.vo.UserVo;
import com.zwp.travelmemories.repo.mybatis.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final static Logger LOGGER  = LoggerFactory.getLogger(AccountService.class);


    @Autowired
    UserMapper userMapper;

    /**
     * 通过username获取到用户信息，专用于登录
     * @param username
     * @return 如果用户不存在或者username等于null则返回null，
     * 否则正确返回用户信息
     */
    public UserVo getUserByUsernameForLogin(String username){

        if(username==null) return null;

        return userMapper.selectUserByUsername(username);
    }

    /**
     * 注册用户
     * @param user
     * @return 当用户名已经存在返回false,注册成功返回true
     */
    public boolean addUser(UserVo user){
        user.setRoles("USER");
        user.setRegTime(System.currentTimeMillis());
        user.setStatus(0);
        user.setLastTime(0L);
        int s = userMapper.insertUser(user);
        if(s==0) return false;// 用户名重复
        LOGGER.debug("user:[{}] uid:[{}] logon success!"
                ,user.getUsername(),user.getUId());
        return true;
    }


}
