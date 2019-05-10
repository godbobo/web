package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.domain.User;
import com.aqzscn.www.global.model.co.AppException;
import com.aqzscn.www.global.model.vo.ReturnError;
import com.aqzscn.www.global.model.vo.ReturnVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制用户行为
 * Created by Godbobo on 2019/5/4.
 */
@RestController
@RequestMapping("/g/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final HttpServletRequest request;

    @Autowired
    public UserController(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 用户登录
     * @return token
     */
    @GetMapping("/token")
    public ReturnVo login(User user) throws RuntimeException {
        if (StringUtils.isAnyBlank(user.getLoginName(), user.getPassword())) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        logger.info("用户登录：" + user.getLoginName() + " - "+ user.getPassword());
        return ReturnVo.ok(request.getMethod());
    }

    /**
     * 注销登录
     * @return 是否成功
     */
    @DeleteMapping("/token")
    public ReturnVo logout(String token) throws RuntimeException {
        if (StringUtils.isBlank(token)) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        logger.info("删除token:" + token);
        return ReturnVo.ok(request.getMethod());
    }

    @PostMapping("/user")
    public ReturnVo register(User user) throws RuntimeException{
        if (StringUtils.isAnyBlank(user.getLoginName(), user.getUsername(), user.getPassword())){
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        logger.info("用户注册：" + user.getLoginName() + " - " + user.getPassword() + " - " + user.getUsername());
        return ReturnVo.ok(request.getMethod());
    }

    @PutMapping("/user")
    public ReturnVo updateUser(User user) throws RuntimeException{
        if (StringUtils.isAnyBlank(user.getLoginName(), user.getUsername(), user.getPassword())){
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        logger.info("修改用户信息：" + user.getLoginName() + " - " + user.getPassword() + " - " + user.getUsername());
        return ReturnVo.ok(request.getMethod());
    }

}
