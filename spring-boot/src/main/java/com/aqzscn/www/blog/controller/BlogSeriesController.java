package com.aqzscn.www.blog.controller;

import com.aqzscn.www.blog.mapper.BlogSeries;
import com.aqzscn.www.blog.service.BlogSeriesService;
import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.aqzscn.www.global.controller.BaseController;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Godbobo on 2019/5/25.
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
    public ReturnVo add(@Validated(ValidationGroup1.class)BlogSeries series, BindingResult result) throws RuntimeException{
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return response(this.seriesService.insert(series));
    }

}
