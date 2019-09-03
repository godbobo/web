package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件资源控制器
 *
 * @author Godbobo
 * @date 2019/6/5
 */
@RestController
@Api(tags = "文件资源服务")
@RequestMapping("/g")
public class ResourceController extends BaseController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(HttpServletRequest request, HttpServletResponse response, ResourceService resourceService) {
        super(request, response);
        this.resourceService = resourceService;
    }

    @ApiOperation("新增文件")
    @PostMapping("/resource/{type}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "type", value = "文件类型"),
            @ApiImplicitParam(paramType = "query", name = "file", value = "文件")
    })
    public ReturnVo insert(@PathVariable String type, MultipartFile file) throws RuntimeException {
        if (StringUtils.isBlank(type) || file == null) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        ReturnVo vo = new ReturnVo();
        vo.setData(resourceService.insert(type, file));
        return vo;
    }


}
