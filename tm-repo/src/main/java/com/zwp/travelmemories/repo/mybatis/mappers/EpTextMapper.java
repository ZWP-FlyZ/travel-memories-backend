package com.zwp.travelmemories.repo.mybatis.mappers;

import com.zwp.travelmemories.comm.vo.EpTextInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @program: travelmemories
 * @description: 事件点文本信息
 * @author: zwp-flyz
 * @create: 2019-11-28 10:14
 * @version: v1.0
 **/
@Mapper
public interface EpTextMapper {


    /**
     * 通过事件点id查找事件文本信息
     * @param epId
     * @return
     */
    EpTextInfoVo selectTextInfoByEpId(@Param("epId") Long epId);


    /**
     * 更新文本信息，当对应事件点的文本信息不存在则插入
     * @param textInfo
     * @return 1=创建成功 2=更新成功 0=失败未知原因
     */
    Integer replaceTextInfo(EpTextInfoVo textInfo);


}
