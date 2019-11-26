package com.zwp.travelmemories.web.vo;

import com.zwp.travelmemories.comm.vo.UserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @program: travelmemories
 * @description: 用户细节消息对象
 * @author: zwp-flyz
 * @create: 2019-11-22 11:18
 * @version: v1.0
 **/
public class UserDetailVo implements UserDetails, Serializable {

    private Long uId;
    private String username;
    private String password;
    private Integer status;
    private List<GrantedAuthority> roles;


    public UserDetailVo(Long uId,String username,String password,Integer status,String... roles){
        Assert.notNull(uId,"the uid is null");
        Assert.notNull(username,"the username is null");
        Assert.notNull(password,"the password is null");
        Assert.notNull(roles,"the roles is null");
        Assert.notNull(status,"the status is null");

        this.uId = uId;
        this.username=username;
        this.password=password;
        this.status =status;
        this.roles= new ArrayList<>();
        // 装载角色
        Arrays.stream(roles).forEach(r->
                this.roles.add(new SimpleGrantedAuthority("ROLE_"+r)));
    }


    public static UserDetailVo from(UserVo user){
        return new UserDetailVo(
                user.getUId(),
                user.getUsername(),
                user.getPassword(),
                user.getStatus(),
                user.getRoles().split(",")
                );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUid() {
        return uId;
    }
}
