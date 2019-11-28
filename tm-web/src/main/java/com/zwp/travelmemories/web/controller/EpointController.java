package com.zwp.travelmemories.web.controller;

import com.zwp.travelmemories.comm.vo.EpTextInfoVo;
import com.zwp.travelmemories.comm.vo.EpointVo;
import com.zwp.travelmemories.service.EpointService;
import com.zwp.travelmemories.web.vo.EpointCrtVo;
import com.zwp.travelmemories.web.vo.ResponseCodes;
import com.zwp.travelmemories.web.vo.ResponseResult;
import com.zwp.travelmemories.web.vo.UserDetailVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: travelmemories
 * @description: 事件点控制器
 * @author: zwp-flyz
 * @create: 2019-11-27 15:40
 * @version: v1.0
 **/
@Controller
@RequestMapping("/epoint")
public class EpointController extends BaseController{

    private final static Logger LOGGER = LoggerFactory.getLogger(EpointController.class);

    @Autowired
    EpointService epointService;


    /**
     * 获取自身所有事件点信息
     * @return
     */
    @GetMapping("/getall")
    @ResponseBody
    public ResponseResult<List<EpointVo>> getAllEpoint(HttpServletRequest request,Long uId){
        UserDetailVo user = getCurrentLoginUserInfo();
        ResponseResult<List<EpointVo>> rr = null;
        if(uId==null||!uId.equals(user.getUid())){
            //访问不属于自己的事件点
            rr = new ResponseResult<>(ResponseCodes.EP_OP_PERMISSION_DENIED,null);
            LOGGER.debug("user:[{}] uid:[{}] ask for all epoints denied. target uid:[{}] ",
                    user.getUsername(),user.getUid(),uId);
        }else{
            rr = ResponseResult.success(epointService.getAllEpointsByUid(uId));
            LOGGER.debug("user:[{}] uid:[{}] ask for all epoints success. epoints count={} ",
                    user.getUsername(),user.getUid(),rr.getData().size());
        }
        return rr;
    }

    /**
     * 创建事件点
     * @param request
     * @param point 创建的事件点
     * @return 事件点id
     *
     */
    @PostMapping("/create")
    @ResponseBody
    public ResponseResult<EpointID> createEpoint(HttpServletRequest request,
                                                 @Valid EpointCrtVo point,
                                                 BindingResult result){
        UserDetailVo user = getCurrentLoginUserInfo();
        ResponseResult<EpointID> rr =null;
        if(result.hasErrors()){
            // 事件点有错误
            rr = new ResponseResult<>(ResponseCodes.EP_CREATE_FORMATTING_ERROR,null);
            LOGGER.debug("user:[{}] uid:[{}] create epoints with error info {}",
                    user.getUsername(),user.getUid(),point);
        }else{

            EpointVo vo = epointService.addEpoint(toEpointVo(point,user.getUid()));
            if(vo!=null)
                // 创建事件点成功
                rr = ResponseResult.success(new EpointID(vo.getEpId()));
            else
                // 创建事件点失败
                rr = ResponseResult.failure();
        }
        return rr;
    }


    /**
     * 获取事件点文本信息，当事件点文本信息不存在时，返回null
     * @param request
     * @param epId
     * @return
     */
    @GetMapping("/get_textinfo")
    @ResponseBody
    public ResponseResult<EpTextInfoVo> getEpointTextInfo(HttpServletRequest request,
                                                          Long epId){
        UserDetailVo user = getCurrentLoginUserInfo();
        ResponseResult<EpTextInfoVo> rr =
                ResponseResult.success(epointService.getEpointTextInfo(epId,user.getUid()));
        LOGGER.debug("user:[{}] uid:[{}] ask for epoint textInfo success. epId:[{}]",
                user.getUsername(),user.getUid(),epId);
        return rr;
    }

    @PostMapping("update_textinfo")
    @ResponseBody
    public ResponseResult updateTextInfo(HttpServletRequest request,
                                         Long epId,
                                         @RequestParam("text") String epTiText){
        ResponseResult rr;
        UserDetailVo user = getCurrentLoginUserInfo();
        if(epTiText==null||epTiText.length()>1800){
            // 更新文本信息失败，文本信息字符长度超限制
            rr = new ResponseResult(ResponseCodes.EP_TI_FORMATTING_ERROR,null);
            LOGGER.debug("update text info error. epTiText out of range.epTiText len="
                    +epTiText.length());
        }else{
            EpTextInfoVo vo = toEpTextInfoVo(user.getUid(),epId,epTiText);
            if(!epointService.updateEpointTextInfo(vo)){
                LOGGER.debug("update text info error. unknown error.epId:[{}] uId:[{}]"
                        ,epId,user.getUid());
                rr = ResponseResult.failure("更新文本信息发生未知错误，请确认用户id和事件点id是否正常。");
            }else{
                rr = ResponseResult.success();
                LOGGER.debug("update text info success. epId:[{}] uId:[{}]",epId,user.getUid());
            }
        }

        return rr;
    }








    @Data
    @AllArgsConstructor
    class EpointID{
        private Long epId;
    }

    private EpointVo toEpointVo(EpointCrtVo vo,Long uId){
        EpointVo res = new EpointVo();
        res.setUId(uId);
        res.setEpTitle(vo.getEpTitle());
        res.setEpAddr(vo.getEpAddr());
        res.setEpLat(vo.getEpLat());
        res.setEpLng(vo.getEpLng());
        res.setEpTime(vo.getEpTime());
        res.setEpCreTime(System.currentTimeMillis());
        res.setEpType(vo.getEpType());
        res.setEpStatus(0);
        return res;
    }

    private EpTextInfoVo toEpTextInfoVo(Long uId,Long epId,String epTiText){
        EpTextInfoVo vo = new EpTextInfoVo();
        vo.setEpId(epId);
        vo.setUId(uId);
        vo.setEpTiLastTime(System.currentTimeMillis());
        vo.setEpTiText(epTiText);
        return vo;
    }



}
