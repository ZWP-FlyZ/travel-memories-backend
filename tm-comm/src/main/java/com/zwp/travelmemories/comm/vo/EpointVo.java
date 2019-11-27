package com.zwp.travelmemories.comm.vo;

import lombok.Data;

/**
 * @program: travelmemories
 * @description: 事件点
 * @author: zwp-flyz
 * @create: 2019-11-27 15:05
 * @version: v1.0
 **/
@Data
public class EpointVo {
    private Long epId;
    private Long uId;
    private String epTitle;
    private String epAddr;
    private Double epLat;
    private Double epLng;
    private Integer epType;
    private Long epTime;// 事件发生时间
    private Long epCreTime;// 时间点创建时间
    private Integer epStatus;// 事件点状态
}
