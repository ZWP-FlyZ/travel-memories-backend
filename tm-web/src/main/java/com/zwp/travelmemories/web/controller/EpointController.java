package com.zwp.travelmemories.web.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            // 创建事件点成功
            EpointVo vo = epointService.addEpoint(toEpointVo(point,user.getUid()));

            rr = ResponseResult.success(new EpointID(vo.getEpId()));
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


}
