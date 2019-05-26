package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.aqzscn.www.global.config.validation.ValidationGroup2;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.mapper.User;
import com.aqzscn.www.global.service.UserService;
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
import javax.servlet.http.HttpServletResponse;

/**
 * 用户请求接口
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@RestController
@RequestMapping("/g")
@Api(tags = "用户数据接口")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.userService = userService;
    }

    @ApiOperation(value = "用户登录")
    @GetMapping("/token")
    public ReturnVo login(@Validated(ValidationGroup2.class) User user, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return response(true);
    }

    @ApiOperation(value = "注销登录")
    @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true)
    @DeleteMapping("/token")
    public ReturnVo logout(String token) throws RuntimeException {
        if (StringUtils.isBlank(token)) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        return response(true);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/users")
    public ReturnVo reg(@Validated(ValidationGroup1.class) @RequestBody User user, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return response(this.userService.reg(user));
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/user")
    public ReturnVo updateUser(@RequestBody User user) throws RuntimeException {
        if (StringUtils.isAnyBlank(user.getRealName(), user.getUsername(), user.getPassword())) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        logger.info("修改用户信息：" + user.getRealName() + " - " + user.getPassword() + " - " + user.getUsername());
        return response(true);
    }

}
