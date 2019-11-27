package com.zwp.travelmemories.web.controller;

import com.zwp.travelmemories.comm.vo.UserVo;
import com.zwp.travelmemories.service.AccountService;
import com.zwp.travelmemories.web.vo.LogonVo;
import com.zwp.travelmemories.web.vo.ResponseCodes;
import com.zwp.travelmemories.web.vo.ResponseResult;
import com.zwp.travelmemories.web.vo.UserDetailVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;

/**
 * @program: travelmemories
 * @description: 处理用户登录和登出的控制器
 * @author: zwp-flyz
 * @create: 2019-11-22 11:48
 * @version: v1.0
 **/
@RestController
public class AuthorizedController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorizedController.class);

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder encoder;

    @Data
    @AllArgsConstructor
    public class UserID {

        private Long uId;

    }

    /**
     * 用户通过认证成功后处理
     * @param request 请求
     * @return 结果
     */
    @PostMapping("/login")
    public ResponseResult<UserID> login(HttpServletRequest request){
        Authentication aut =
                SecurityContextHolder.getContext().getAuthentication();
        UserDetailVo user = (UserDetailVo)aut.getPrincipal();
        try{
            // 登录成功后的操作
            LOGGER.info("login success->username:[{}],ip:[{}],port:[{}]",
                    user.getUsername(),request.getRemoteHost(),request.getRemotePort());
        }catch (Exception e){
            // 更新登录信息失败
            LOGGER.error("unknown error -> username:{} ",user.getUsername());
            LOGGER.error("update login status error. ",e);

            // 重要，将保留在holder中的认证信息清空
            SecurityContextHolder.getContext().setAuthentication(null);
            return ResponseResult.failure("登录失败！");
        }
        return ResponseResult.success(new UserID(user.getUid()));
    }

    @PostMapping("/login_failure")
    public ResponseResult login_failure(HttpServletRequest request){
        String username = request.getParameter("username");
        LOGGER.info("login failure->username:[{}],ip:[{}],port:[{}]",
                username,request.getRemoteHost(),request.getRemotePort());
        return ResponseResult.failure("用户名或者密码错误");
    }

    /**
     * 注册
     * @param request
     * @return
     */
    @PostMapping("/logon")
    public ResponseResult logon(HttpServletRequest request,
                                @Valid LogonVo user,
                                BindingResult ckResult){
        ResponseResult rr = null;
        if(ckResult.hasErrors()){
            //注册信息不合法
            rr = new ResponseResult(ResponseCodes.LOGON_FORMATTING_ERROR,
                    ckResult.getAllErrors().get(0).getDefaultMessage());
            LOGGER.debug("user {} logon failure. error info :"+
                    ckResult.getAllErrors().get(0).getDefaultMessage(),user.getUsername());
        }else{
            try {
                UserVo uvo = new UserVo();
                uvo.setUsername(user.getUsername());
                uvo.setPassword(encoder.encode(user.getPassword()));
                if(accountService.addUser(uvo)){
                    //注册成功
                    rr = ResponseResult.success();
                    LOGGER.info("user:[{}] logon success!");
                }else
                {
                    //注册失败
                    rr = new ResponseResult(ResponseCodes.LOGON_USERNAME_REPEAT,null);
                    LOGGER.info("user:[{}] logon failure, username repeat!");
                }

            }catch (Exception e){
                rr = ResponseResult.failure("注册失败，未知错误！");
                LOGGER.error("unknown exception ",e);
            }
        }// end else
        return rr;
    }

}
