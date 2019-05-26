package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.mapper.Role;
import com.aqzscn.www.global.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 角色请求接口
 * @author Godbobo
 * @date 2019/5/26
 */
@RestController
@RequestMapping("/g")
@Api(tags = "角色数据接口")
public class RoleController extends BaseController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService, HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    @ApiOperation("新增角色")
    public ReturnVo add(@Validated @RequestBody Role role, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return response(this.roleService.addRole(role));
    }

}
