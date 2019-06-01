package com.aqzscn.www.blog.controller;

import com.aqzscn.www.blog.domain.po.ArticleRequest;
import com.aqzscn.www.blog.service.BlogArticleService;
import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.aqzscn.www.global.controller.BaseController;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.aqzscn.www.global.domain.dto.ReturnVo;
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
 * 博文前端控制器
 *
 * @author Godbobo
 * @date 2019/5/24.
 */
@RestController
@RequestMapping("/blog")
@Api(tags = "博客博文数据接口")
public class BlogArticleController extends BaseController {

    private final BlogArticleService articleService;
    private final Logger logger = LoggerFactory.getLogger(BlogArticleController.class);

    @Autowired
    public BlogArticleController(BlogArticleService articleService, HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    @ApiOperation("新增文章")
    public ReturnVo addArticles(@Validated(ValidationGroup1.class) @RequestBody ArticleRequest articleRequest, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return response(articleService.addArticles(articleRequest));
    }

    @DeleteMapping("/articles/{id}")
    @ApiOperation("根据主键删除文章")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键")
    public ReturnVo deleteArticlesById(@PathVariable Long id) throws RuntimeException {
        if (id == null) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        return response(articleService.deleteById(id));
    }

    @GetMapping("/articles")
    @ApiOperation("根据条件查询文章列表")
    public ReturnVo selectLst(@Validated ArticleRequest articleRequest, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        if (StringUtils.isBlank(articleRequest.getTitle())) {
            articleRequest.setTitle(null);
        }
        ReturnVo returnVo = new ReturnVo();
        returnVo.setData(this.articleService.select(articleRequest));
        return returnVo;
    }

    @GetMapping("/articles/{id}")
    @ApiOperation("根据主键查找文章")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键")
    public ReturnVo selectById(@PathVariable Long id) throws RuntimeException {
        if (id == null) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        ReturnVo vo = new ReturnVo();
        vo.setData(this.articleService.selectById(id));
        return vo;
    }

}
