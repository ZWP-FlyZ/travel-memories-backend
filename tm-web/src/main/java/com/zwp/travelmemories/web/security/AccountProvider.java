package com.zwp.travelmemories.web.security;

import com.zwp.travelmemories.comm.vo.UserVo;
import com.zwp.travelmemories.service.AccountService;
import com.zwp.travelmemories.web.vo.UserDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @program: travelmemories
 * @description: 用户账户详情服务
 * @author: zwp-flyz
 * @create: 2019-11-22 10:22
 * @version: v1.0
 **/
public class AccountProvider implements UserDetailsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountProvider.class);

    @Autowired
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 从数据源中获取道用户登录信息，并返回给spring security 进行检查
        UserVo user ;
        LOGGER.debug("user:{} login request",s);
        if(s==null||s.equals("")||
                (user = accountService.getUserByUsernameForLogin(s))==null)
            throw new UsernameNotFoundException("user ["+s+"] not founded");

        LOGGER.debug("Try to log in. user[{}] ,uid[{}] ",s,user.getUId());
        return UserDetailVo.from(user);
    }
}
