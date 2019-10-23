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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/roles")
    @ApiOperation("获取角色列表")
    public ReturnVo selectRole() throws RuntimeException {
        ReturnVo vo = new ReturnVo();
        Map<String, Object> res = new HashMap<>(1);
        res.put("lst", roleService.selectAllRoles());
        vo.setData(res);
        return vo;
    }

    @DeleteMapping("/roles")
    @ApiOperation("删除角色")
    public ReturnVo deleteRole(Role role) throws RuntimeException {
        return null;
    }

}
