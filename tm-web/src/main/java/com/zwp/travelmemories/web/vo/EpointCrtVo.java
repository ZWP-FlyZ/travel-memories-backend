package com.zwp.travelmemories.web.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @program: travelmemories
 * @description: 用于前端上传事件点信息使用的对象
 * @author: zwp-flyz
 * @create: 2019-11-27 16:19
 * @version: v1.0
 **/
@Data
public class EpointCrtVo {

    @Length(min=0,max=50)
    private String epTitle;
    @Length(min=0,max=64)
    private String epAddr;
    @NotNull
    @Range(min=-90,max = 90)
    private Double epLat;
    @NotNull
    @Range(min=-180,max=180)
    private Double epLng;
    @NotNull
    @Range(min=0,max=2)
    private Integer epType;
    @NotNull
    @Range(min=0)
    private Long epTime;// 事件发生时间

}
