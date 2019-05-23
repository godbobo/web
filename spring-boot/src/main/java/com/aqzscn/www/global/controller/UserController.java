package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.domain.User;
import com.aqzscn.www.global.domain.validation.ValidationGroup1;
import com.aqzscn.www.global.domain.validation.ValidationGroup2;
import com.aqzscn.www.global.model.co.AppException;
import com.aqzscn.www.global.model.vo.ReturnError;
import com.aqzscn.www.global.model.vo.ReturnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制用户行为
 * Created by Godbobo on 2019/5/4.
 */
@RestController
@RequestMapping("/g/user")
@Api(tags = "用户数据接口")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final HttpServletRequest request;

    @Autowired
    public UserController(HttpServletRequest request) {
        this.request = request;
    }

    @ApiOperation(value = "用户登录")
    @GetMapping("/token")
    public ReturnVo login(@Validated(ValidationGroup2.class) User user, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        logger.info("用户登录：" + user.getLoginName() + " - "+ user.getPassword());
        return ReturnVo.ok(request.getMethod());
    }

    @ApiOperation(value = "注销登录")
    @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true)
    @DeleteMapping("/token")
    public ReturnVo logout(String token) throws RuntimeException {
        if (StringUtils.isBlank(token)) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        logger.info("删除token:" + token);
        return ReturnVo.ok(request.getMethod());
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/user")
    public ReturnVo register(@Validated(ValidationGroup1.class) User user , BindingResult result) throws RuntimeException{
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        logger.info("用户注册：" + user.getLoginName() + " - " + user.getPassword() + " - " + user.getUsername());
        return ReturnVo.ok(request.getMethod());
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/user")
    public ReturnVo updateUser(@RequestBody User user) throws RuntimeException{
        if (StringUtils.isAnyBlank(user.getLoginName(), user.getUsername(), user.getPassword())){
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        logger.info("修改用户信息：" + user.getLoginName() + " - " + user.getPassword() + " - " + user.getUsername());
        return ReturnVo.ok(request.getMethod());
    }

}
