package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.aqzscn.www.global.config.validation.ValidationGroup3;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.PageRequest;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.domain.vo.UserRequest;
import com.aqzscn.www.global.mapper.User;
import com.aqzscn.www.global.service.UserService;
import com.aqzscn.www.global.util.JacksonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 用户请求接口
 * 省点事，先不加注销登录的接口 让前端把token删除了就算注销
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

    @ApiOperation("获取用户信息")
    @GetMapping("/userinfo")
    public String getUserInfo() throws RuntimeException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof User){
            ReturnVo vo = new ReturnVo();
            vo.setData(userDetails);
            return JacksonUtil.me().filter("UserFilter", "password").toJson(vo);
        }
        return JacksonUtil.me().filter("UserFilter", "password").toJson(this.userService.getUserInfo(userDetails.getUsername()));
    }

    @GetMapping("/token-page")
    public ModelAndView authorizationForTest() {
        return new ModelAndView("get-authorization-for-test.html");
    }

    @GetMapping("/users")
    @ApiOperation("查询用户")
    public String selectUsers(User user, PageRequest pageRequest) throws RuntimeException {
        ReturnVo vo = new ReturnVo();
        vo.setData(userService.selectUser(user, pageRequest.getPageNum(), pageRequest.getPageSize()));
        // 由于User类添加了JsonFilter，因此不能用默认的对象转换方式了
        return JacksonUtil.me().toJson(vo);
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
    public ReturnVo updateUser(@RequestBody User user, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        // 判断当前用户登陆身份，只有管理员可以修改用户状态
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User)authentication.getPrincipal();
        String roleStr = currentUser.getAuthorities().toString();
        if (!roleStr.contains("ROLE_ADMIN")) {
            user.setEnabled(null);
            user.setLocked(null);
        }
        return response(userService.updateUser(user));
    }

    @ApiOperation("激活用户的操作）")
    @PutMapping("/access")
    public ReturnVo active(String code, String username) {
        // 在Oauth授权中，没有激活就没办法登录，也就没办法直接使用修改用户信息的接口，因此新增一个接口暴露给未登录用户
        // 把激活用户换一个说法就是修改用户的访问权限 算是勉强符合Restful API的规范把

        return null;
    }

    @GetMapping("/roles/{uid}")
    @ApiOperation("根据用户id获取角色列表")
    @ApiImplicitParam(name = "uid", value = "用户id", required = true, paramType = "path")
    public ReturnVo getRolesByUid(@PathVariable Long uid) {
        ReturnVo vo = new ReturnVo();
        Map<String, Object> res = new HashMap<>(1);
        res.put("lst", userService.selectRoleByUid(uid));
        vo.setData(res);
        return vo;
    }

    @PutMapping("/user/roles")
    @ApiOperation(value = "修改用户角色", notes = "已有角色则删除，没有则增加")
    public ReturnVo switchUserRoles(@Validated(ValidationGroup3.class)@RequestBody UserRequest userRequest,BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return response(userService.updateRolesByUid(userRequest.getId(), userRequest.getRoleName()));
    }

}
