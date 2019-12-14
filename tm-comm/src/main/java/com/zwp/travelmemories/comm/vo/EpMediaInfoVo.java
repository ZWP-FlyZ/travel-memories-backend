package com.zwp.travelmemories.comm.vo;

import lombok.Data;

/**
 * @program: travelmemories
 * @description: 事件点媒体信息
 * @author: zwp-flyz
 * @create: 2019-12-13 15:04
 * @version: v1.0
 **/
@Data
public class EpMediaInfoVo {

    private Long epMiId;
    private Integer epMiType;
    private Long uId;
    private Long epId;
    private String epMiDesc;
    private String epMiPath;

}
