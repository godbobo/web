package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.config.validation.ValidationGroup2;
import com.aqzscn.www.global.domain.dto.PageRequest;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.domain.vo.MockRequest;
import com.aqzscn.www.global.mapper.Mock;
import com.aqzscn.www.global.service.MockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/g")
@Api(tags = "模拟数据接口")
public class MockController extends BaseController {

    private MockService mockService;

    public MockController(MockService mockService, HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.mockService = mockService;
    }

    @ApiOperation("获取模拟数据列表")
    @GetMapping("/mocks")
    public ReturnVo getMocks(@RequestBody(required = false) MockRequest mockRequest, @Validated(ValidationGroup2.class) PageRequest pageRequest) throws RuntimeException {
        ReturnVo vo = new ReturnVo();
        vo.setData(this.mockService.selectMock(mockRequest, pageRequest.getPageNum(), pageRequest.getPageSize()));
        return vo;
    }

    @ApiOperation("新增模拟数据")
    @PostMapping("/mocks")
    public ReturnVo insertMocks(@RequestBody MockRequest mockRequest) throws RuntimeException {
        return response(this.mockService.insertMock(mockRequest));
    }

    @ApiOperation("删除模拟数据")
    @DeleteMapping("/mocks")
    public ReturnVo deleteMocks(@RequestBody Mock mock) throws RuntimeException {
        return response(this.mockService.deleteMock(mock));
    }

    @ApiOperation("修改模拟数据")
    @PutMapping("/mocks")
    public ReturnVo updateMocks(@RequestBody MockRequest mockRequest) throws RuntimeException {
        return response(this.mockService.updateMock(mockRequest));
    }

}
