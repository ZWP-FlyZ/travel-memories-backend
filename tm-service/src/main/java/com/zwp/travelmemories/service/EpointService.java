package com.zwp.travelmemories.service;

import com.zwp.travelmemories.comm.vo.EpointVo;
import com.zwp.travelmemories.repo.mybatis.mappers.EpointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    EpointMapper epointMapper;

    /**
     * 通过用户id查找所有事件点，
     * @param uId 合法用户id
     * @return 返回获取的事件点，当没有事件点时返回空的List对象
     */
    public List<EpointVo> getAllEpointsByUid(Long uId){
        return epointMapper.selectAllEpointByUid(uId);
    }

    /**
     * 添加事件点
     * @param point
     * @return 当添加成功时返回包含epId的point对象，若添加失败返回null
     */
    public EpointVo addEpoint(EpointVo point){
        if(point==null) return null;
        int s = epointMapper.insertEpoint(point);
        if(s>0) return point;// 添加成功
        else return null;// 添加失败
    }




}
