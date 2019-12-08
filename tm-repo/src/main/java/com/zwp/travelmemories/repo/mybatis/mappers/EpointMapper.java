package com.zwp.travelmemories.repo.mybatis.mappers;

import com.zwp.travelmemories.comm.vo.EpointVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: travelmemories
 * @description: 事件点数据库操作
 * @author: zwp-flyz
 * @create: 2019-11-27 15:16
 * @version: v1.0
 **/
@Mapper
public interface EpointMapper {

    List<EpointVo> selectAllEpointByUid(@Param("uId") Long uId);

    Integer insertEpoint(EpointVo point);

    Integer updateEpoint(EpointVo point);

}
