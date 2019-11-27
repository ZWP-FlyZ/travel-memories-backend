package com.zwp.travelmemories.web.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @program: travelmemories
 * @description: 注册时使用的对象
 * @author: zwp-flyz
 * @create: 2019-11-26 20:24
 * @version: v1.0
 **/
@Data
public class LogonVo {


    @NotNull(message="username is null")
    @Length(min=4,max=32,
            message="username'length  is not in range(6,32)")
    @Pattern(regexp="^(_|[0-9a-zA-Z])+$", // 大小写字母数字和下划线
            message="Illegal username ")
    private String username;

    @NotNull(message="password is null")
    @Length(min=6,max=32,
            message="password'length is not in range(6,32)")
    private String password;

}
