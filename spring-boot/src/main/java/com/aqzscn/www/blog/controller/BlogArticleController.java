package com.aqzscn.www.blog.controller;

import com.aqzscn.www.blog.mapper.BlogArticle;
import com.aqzscn.www.blog.service.BlogArticleService;
import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 博文前端控制器
 * Created by Godbobo on 2019/5/24.
 */
@RestController
@RequestMapping("/blog")
@Api(tags = "博客博文数据接口")
public class BlogArticleController {

    private final BlogArticleService articleService;
    private final HttpServletRequest request;

    @Autowired
    public BlogArticleController(BlogArticleService articleService, HttpServletRequest request) {
        this.articleService = articleService;
        this.request = request;
    }

    @PostMapping("/articles")
    @ApiOperation("新增文章")
    public ReturnVo addArticles(@Validated(ValidationGroup1.class) BlogArticle article, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        if (articleService.addArticles(article)) {
            return ReturnVo.ok(this.request.getMethod());
        } else {
            return ReturnVo.fail(this.request.getMethod());
        }
    }

}
