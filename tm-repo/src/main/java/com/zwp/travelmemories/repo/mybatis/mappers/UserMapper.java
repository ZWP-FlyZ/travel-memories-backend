package com.zwp.travelmemories.repo.mybatis.mappers;

import com.zwp.travelmemories.comm.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @program: travelmemories
 * @description: 操作user表的接口
 * @author: zwp-flyz
 * @create: 2019-11-26 15:58
 * @version: v1.0
 **/
@Mapper
public interface UserMapper {

    /**
     * 通过唯一的用户名查找用户信息
     * @param username
     * @return UserVo 用户信息
     */
    UserVo selectUserByUsername(@Param("username")String username);

    /**
     * 添加用户
     * @param vo
     * @return
     */
    Integer insertUser(UserVo vo);



}
