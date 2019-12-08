package com.zwp.travelmemories.web.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @program: travelmemories
 * @description: 更新时用的事件点对象
 * @author: zwp-flyz
 * @create: 2019-12-08 15:15
 * @version: v1.0
 **/
@Data
public class EpointUpdVo {
    @NotNull
    private Long epId;
    @Length(min=0,max=50)
    private String epTitle;
    @Length(min=0,max=64)
    private String epAddr;
    @NotNull
    @Range(min=0,max=2)
    private Integer epType;
    @NotNull
    @Range(min=0)
    private Long epTime;// 事件发生时间

}
