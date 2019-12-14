package com.zwp.travelmemories.service;

import com.zwp.travelmemories.comm.vo.EpMediaInfoVo;
import com.zwp.travelmemories.comm.vo.EpTextInfoVo;
import com.zwp.travelmemories.comm.vo.EpointVo;
import com.zwp.travelmemories.repo.mybatis.mappers.EpTextMapper;
import com.zwp.travelmemories.repo.mybatis.mappers.EpointMapper;
import com.zwp.travelmemories.repo.mybatis.mappers.EpointMediaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @program: travelmemories
 * @description: 事件点相关服务
 * @author: zwp-flyz
 * @create: 2019-11-27 15:30
 * @version: v1.0
 **/
@Service
public class EpointService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EpointService.class);

    @Autowired
    EpointMapper epointMapper;

    @Autowired
    EpTextMapper epTextMapper;

    @Autowired
    EpointMediaMapper epointMediaMapper;

    /**
     * 通过用户id查找所有事件点，
     * @param uId 合法用户id
     * @return 返回获取的事件点，当没有事件点时返回空的List对象
     *
     */
    public List<EpointVo> getAllEpointsByUid(Long uId){
        return epointMapper.selectAllEpointByUid(uId);
    }

    /**
     * 添加事件点
     * @param point
     * @return 当添加成功时返回包含epId的point对象，若添加失败返回null
     * @throws NullPointerException 参数为空的异常
     */
    public EpointVo addEpoint(EpointVo point){
        Assert.notNull(point,"null");
        int s = epointMapper.insertEpoint(point);
        if(s>0) return point;// 添加成功
        else return null;// 添加失败
    }

    /**
     * 获取一个事件点的文本信息
     * @param epId
     * @return 当事件点的文本信息不存在返回null
     */
    public EpTextInfoVo getEpointTextInfo(Long epId,Long uId){
        return epTextMapper.selectTextInfoByEpId(epId,uId);
    }


    /**
     * 更新事件点属性
     * @param point
     * @return 当更新成功时返回true,否则返回false
     */
    public boolean updateEpoint(EpointVo point){
        if(point.getEpType()>2)
            point.setEpType(2);
        else if(point.getEpType()<0)
            point.setEpType(0);
        return epointMapper.updateEpoint(point)>0;
    }


    /**
     * 删除某一个事件点内容
     * @param epId 事件点id
     * @param uId 用户id
     * @return 删除成功放回true，否则返回false
     */
    public boolean deleteEpoint(Long epId,Long uId){
        return epointMapper.updateEpointForDelete(epId,uId)>0;
    }



    /**
     * 更新事件点文本信息，如果原事件点文本信息不存在则创建
     * @param epTextInfoVo
     * @return 更新或者创建成功返回true，否则返回false
     * @throws NullPointerException 参数为空的异常
     * @throws DataIntegrityViolationException 当用户id或事件点id不存在时会出现该异常
     */
    public boolean updateEpointTextInfo(EpTextInfoVo epTextInfoVo){
        Assert.notNull(epTextInfoVo,"null");
        boolean res = true;
        int s = epTextMapper.replaceTextInfo(epTextInfoVo);
        if(s==1){
            LOGGER.debug("Add EpointTextInfo success.EpId:[{}],uId:[{}]",
                    epTextInfoVo.getEpId(),epTextInfoVo.getUId());
        }else if(s==2){
            LOGGER.debug("Update EpointTextInfo success.EpId:[{}],uId:[{}]",
                    epTextInfoVo.getEpId(),epTextInfoVo.getUId());
        }else{
            res=false;
            LOGGER.debug("Update or Add EpointTextInfo failure.EpId:[{}],uId:[{}]" +
                            " the ep may not be created by the user",
                    epTextInfoVo.getEpId(),epTextInfoVo.getUId());
        }
        return res;
    }


    /**
     * 通过用户id和事件点id获取所有媒体信息
     * @param uId
     * @param epId
     * @return 当无媒体信息时，返回空list
     */
    public List<EpMediaInfoVo> getAllMediaInfoByEpId(Long uId,Long epId){
        return epointMediaMapper.selectMediaInfoByEpId(uId,epId);
    }


    /**
     * 添加事件点id的媒体信息
     * @param info
     * @return
     */
    public boolean addMediaInfo(EpMediaInfoVo info){
        return epointMediaMapper.insertMediaInfo(info)>0;
    }



}
