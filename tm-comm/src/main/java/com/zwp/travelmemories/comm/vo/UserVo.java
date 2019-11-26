package com.zwp.travelmemories.comm.vo;

import lombok.Data;

/**
 * @program: travelmemories
 * @description: 用户信息vo
 * @author: zwp-flyz
 * @create: 2019-11-22 10:32
 * @version: v1.0
 **/
@Data
public class UserVo {

    private Long uId;
    private String username;
    private String password;
    private String roles;
    private Integer status;
    private Long regTime;
    private Long lastTime;//最后一次登录时间

}
