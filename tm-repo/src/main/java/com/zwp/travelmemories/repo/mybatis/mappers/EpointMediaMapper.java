package com.zwp.travelmemories.repo.mybatis.mappers;

import com.zwp.travelmemories.comm.vo.EpMediaInfoVo;

import java.util.List;

/**
 * @program: travelmemories
 * @description: 媒体信息接口
 * @author: zwp-flyz
 * @create: 2019-12-14 11:26
 * @version: v1.0
 **/
public interface EpointMediaMapper {

    List<EpMediaInfoVo> selectMediaInfoByEpId(Long uId, Long epId);

    Integer insertMediaInfo(EpMediaInfoVo info);

    Integer deleteMediaInfo(Long uId,Long epId,Long epMiId);

    Integer updateMediaInfoDesc(Long uId,Long epId,Long epMiId,String epMiDesc);

}
