package com.aqzscn.www.blog.controller;

import com.aqzscn.www.blog.mapper.BlogSeries;
import com.aqzscn.www.blog.service.BlogSeriesService;
import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.aqzscn.www.global.config.validation.ValidationGroup2;
import com.aqzscn.www.global.controller.BaseController;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Godbobo
 * @date 2019/5/25.
 */
@RestController
@RequestMapping("/blog")
@Api(tags = "博客连载数据接口")
public class BlogSeriesController extends BaseController {

    private final BlogSeriesService seriesService;

    @Autowired
    public BlogSeriesController(BlogSeriesService seriesService, HttpServletRequest request, HttpServletResponse response){
        super(request, response);
        this.seriesService = seriesService;
    }

    @GetMapping("/series")
    @ApiOperation("获取全部连载信息")
    public ReturnVo selectAll() throws RuntimeException {
        ReturnVo vo = new ReturnVo();
        vo.setData(this.seriesService.selectAll());
        return vo;
    }

    @PostMapping("/series")
    @ApiOperation("添加连载")
    public ReturnVo add(@Validated(ValidationGroup1.class)@RequestBody BlogSeries series, BindingResult result) throws RuntimeException{
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return response(this.seriesService.insert(series));
    }

    @DeleteMapping("/series/{id}")
    @ApiOperation("根据主键删除连载")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键")
    public ReturnVo deleteById(@PathVariable Long id) throws RuntimeException{
        if(id == null) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        return response(seriesService.deleteById(id));
    }

    @DeleteMapping("/series")
    @ApiOperation("根据主键列表删除连载")
    @ApiImplicitParam(paramType = "query", name = "idStr", value = "主键列表")
    public ReturnVo deleteByIds(@RequestParam String idStr) throws RuntimeException {
        if(StringUtils.isBlank(idStr)){
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        return response(seriesService.batchDeleteByIds(idStr));
    }

    @PutMapping("/series")
    @ApiOperation("根据主键更新连载信息")
    public ReturnVo updateById(@RequestBody @Validated(ValidationGroup2.class) BlogSeries series, BindingResult result) throws RuntimeException{
        if(result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return response(seriesService.updateById(series));
    }


}
