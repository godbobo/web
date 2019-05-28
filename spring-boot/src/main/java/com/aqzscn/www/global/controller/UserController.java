package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
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
        System.out.println("用户注册");
        return response(this.userService.reg(user));
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/users")
    public ReturnVo updateUser(@RequestBody User user) throws RuntimeException {
        if (StringUtils.isAnyBlank(user.getRealName(), user.getUsername(), user.getPassword())) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        logger.info("修改用户信息：" + user.getRealName() + " - " + user.getPassword() + " - " + user.getUsername());
        return response(true);
    }

    @ApiOperation("激活用户的操作）")
    @PutMapping("/access")
    public ReturnVo active(String code, String username) {
        // 在Oauth授权中，没有激活就没办法登录，也就没办法直接使用修改用户信息的接口，因此新增一个接口暴露给未登录用户
        // 把激活用户换一个说法就是修改用户的访问权限 算是勉强符合Restful API的规范把

        return null;
    }

}
