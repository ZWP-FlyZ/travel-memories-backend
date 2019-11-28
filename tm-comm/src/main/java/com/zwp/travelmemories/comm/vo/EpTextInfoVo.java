package com.zwp.travelmemories.comm.vo;

import lombok.Data;

/**
 * @program: travelmemories
 * @description: 事件点文本信息
 * @author: zwp-flyz
 * @create: 2019-11-28 10:09
 * @version: v1.0
 **/
@Data
public class EpTextInfoVo {

    private Long epTiId;
    private Long epId;
    private Long uId;
    private Long epTiLastTime;
    private String epTiText;// 文本信息

}
