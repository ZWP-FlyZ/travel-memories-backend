package com.zwp.travelmemories.web.controller;

import com.zwp.travelmemories.comm.utils.EncryptionUtils;
import com.zwp.travelmemories.comm.vo.EpMediaInfoVo;
import com.zwp.travelmemories.comm.vo.EpTextInfoVo;
import com.zwp.travelmemories.comm.vo.EpointVo;
import com.zwp.travelmemories.service.EpointService;
import com.zwp.travelmemories.service.StorageService;
import com.zwp.travelmemories.web.vo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    StorageService storageService;

    /**
     * 获取自身所有事件点信息
     * @return
     */
    @GetMapping("/getall")
    @ResponseBody
    public ResponseResult<List<EpointVo>> getAllEpoint(HttpServletRequest request){
        UserDetailVo user = getCurrentLoginUserInfo();

        ResponseResult<List<EpointVo>> rr
                = ResponseResult.success(epointService.getAllEpointsByUid(user.getUid()));
        LOGGER.debug("user:[{}] uid:[{}] ask for all epoints success. epoints count={} ",
                user.getUsername(),user.getUid(),rr.getData().size());

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
            rr = new ResponseResult<>(ResponseCodes.PARAM_FORMATTING_ERROR,null);
            LOGGER.debug("user:[{}] uid:[{}] create epoints with error info {}",
                    user.getUsername(),user.getUid(),point);
        }else{

            EpointVo vo = epointService.addEpoint(toEpointVo(point,user.getUid()));
            if(vo!=null)
                // 创建事件点成功
                rr = ResponseResult.success(new EpointID(vo.getEpId()));
            else
                // 创建事件点失败
                rr = ResponseResult.failure("创建事件点失败");
        }
        return rr;
    }

    @PostMapping("/setting")
    @ResponseBody
    public ResponseResult updateEpoint(HttpServletRequest request,
                                       @Valid EpointUpdVo point,
                                       BindingResult result){
        UserDetailVo user = getCurrentLoginUserInfo();
        ResponseResult rr =null;
        if(result.hasErrors()){
            // 事件点属性有错误
            rr = new ResponseResult<>(ResponseCodes.PARAM_FORMATTING_ERROR,null);
            LOGGER.debug("user:[{}] uid:[{}] update epoints with error info {}",
                    user.getUsername(),user.getUid(),point);
        }else{
            boolean res = epointService.updateEpoint(toEpointVo(point,user.getUid()));
            if(res)
                // 创建事件点成功
                rr = ResponseResult.success();
            else{
                // 创建事件点失败
                rr = ResponseResult.failure("更新属性点属性失败");
                LOGGER.debug("user:[{}] uid:[{}] update epoints failure {}. 用户与事件点id不匹配.",
                        user.getUsername(),user.getUid(),point);
            }

        }
        return rr;
    }


    @GetMapping("/delete")
    @ResponseBody
    public ResponseResult deleteEpoint(HttpServletRequest request,
                                       Long epId){
        UserDetailVo user = getCurrentLoginUserInfo();
        ResponseResult rr =null;
        boolean res = epointService.deleteEpoint(epId,user.getUid());
        if(res) {
            // 删除事件点成功
            rr = ResponseResult.success();
            LOGGER.debug("user:[{}] uid:[{}] delete epoints success epId:[{}]"
                    ,user.getUsername(),user.getUid(),epId);
        }
        else{
            // 创建事件点失败
            rr = ResponseResult.failure("更新属性点属性失败");
            LOGGER.debug("user:[{}] uid:[{}] update epoints failure epId:[{}]. 用户与事件点id不匹配.",
                    user.getUsername(),user.getUid(),epId);
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

    @PostMapping("/update_textinfo")
    @ResponseBody
    public ResponseResult updateTextInfo(HttpServletRequest request,
                                         Long epId,
                                         @RequestParam("text") String epTiText){
        ResponseResult rr;
        UserDetailVo user = getCurrentLoginUserInfo();
        if(epId==null||epTiText==null||epTiText.length()>1800){
            // 更新文本信息失败，文本信息字符长度超限制
            rr = new ResponseResult(ResponseCodes.PARAM_FORMATTING_ERROR,null);
            LOGGER.debug("update text info error. epId is null or epTiText out of range.epTiText len="
                    +epTiText.length());
        }else{
            EpTextInfoVo vo = toEpTextInfoVo(user.getUid(),epId,epTiText);
            if(!epointService.updateEpointTextInfo(vo)){
                LOGGER.debug("update text info error. unknown error.epId:[{}] uId:[{}]"
                        ,epId,user.getUid());
                rr = ResponseResult.failure("更新文本信息发生错误，请确认用户是否已经创建事件点id。");
            }else{
                rr = ResponseResult.success();
                LOGGER.debug("update text info success. epId:[{}] uId:[{}]",epId,user.getUid());
            }
        }

        return rr;
    }

    @GetMapping("/get_mediainfo")
    @ResponseBody
    public ResponseResult<List<EpMediaInfoVo>> getMediaInfoList(HttpServletRequest request,
                                                                Long epId){
        ResponseResult<List<EpMediaInfoVo>> rr=null;
        UserDetailVo user = getCurrentLoginUserInfo();
        if(epId==null){
            rr = new ResponseResult(ResponseCodes.PARAM_FORMATTING_ERROR,null);
            LOGGER.debug("getMediaInfoList error. epId is null or file is null");
        }else{
            List<EpMediaInfoVo> ret =
                    epointService.getAllMediaInfoByEpId(user.getUid(),epId);
            LOGGER.debug("getMediaInfoList success. uId:{} epId:{} ret.size:{}",
                    user.getUid(),epId,ret.size());
            rr = ResponseResult.success(ret);
        }
        return rr;
    }


    @PostMapping("/upload_mediainfo")
    @ResponseBody
    @Transactional
    public ResponseResult<EpMediaInfoVo> uploadFile(HttpServletRequest request,
                                                    Long epId,
                                                    @RequestParam("file") MultipartFile file)
                                                    throws IOException{
        ResponseResult<EpMediaInfoVo> rr=null;
        if(epId==null||file==null||file.isEmpty()){
            rr = new ResponseResult(ResponseCodes.PARAM_FORMATTING_ERROR,null);
            file.getInputStream().close();
            LOGGER.debug("upload media info error. epId is null or file is null");
        }else {
            UserDetailVo user = getCurrentLoginUserInfo();
            LOGGER.debug("uId:{} epId:{} filename:{} file-type:{} file-size:{}",
                    user.getUid(),epId,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize());
            String newFileName = newFileName(user.getUid(),epId,
                    file.getOriginalFilename());
            EpMediaInfoVo info = new EpMediaInfoVo();
            info.setEpId(epId);
            info.setUId(user.getUid());
            // 根据文件类型设置，这里暂定为0
            info.setEpMiDesc("");
            info.setEpMiType(0);
            info.setEpMiPath(newFileName);
            if(epointService.addMediaInfo(info)){
                // 更新数据库成功
                try(InputStream is= file.getInputStream() ){
                    storageService.saveFile(newFileName,user.getUid(),is,true);
                }
                rr = ResponseResult.success();
            }else
                rr = ResponseResult.failure();
            rr.setData(info);
        }
        return rr;
    }

    @GetMapping("/delete_mediainfo")
    @ResponseBody
    @Transactional
    public ResponseResult deleteMediaInfo(HttpServletRequest request,
                                          Long epMiId,
                                          String epMiPath)
                                        throws IOException{
        ResponseResult<EpMediaInfoVo> rr=null;
        UserDetailVo user = getCurrentLoginUserInfo();
        if(epMiId==null||epMiPath==null||
                epMiPath.equals("")||epMiPath.length()>100){
            rr = new ResponseResult(ResponseCodes.PARAM_FORMATTING_ERROR,null);
            LOGGER.debug("delete media info error. format error!");
        }else {
            boolean res = epointService.deleteMediaInfo(user.getUid(),epMiId,epMiPath);
            if(res){
                storageService.deleteFile(epMiPath,user.getUid());
            }else
                LOGGER.debug("delete MediaInfo from database error. ");
            rr = ResponseResult.success();
        }
        return rr;
    }



    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename)
            throws FileNotFoundException {
        UserDetailVo user = getCurrentLoginUserInfo();
        ResponseEntity<Resource>  r=null;
        Resource file = storageService.getFile(filename,user.getUid());
        if(file.exists()){
            r = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .body(file);
        }
        else{
            r = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
//        System.err.println(r.getHeaders().getContentType());
        return  r;
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

    private EpointVo toEpointVo(EpointUpdVo vo,Long uId){
        EpointVo res = new EpointVo();
        res.setUId(uId);
        res.setEpId(vo.getEpId());
        res.setEpTitle(vo.getEpTitle());
        res.setEpAddr(vo.getEpAddr());
        res.setEpTime(vo.getEpTime());
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

    private String newFileName(Long uId,Long epId,String filename){
        String append =epId+"_"+ EncryptionUtils.salt()+"_";
        return append+filename;
    }



}
