package com.aqzscn.www.blog.controller;

import com.aqzscn.www.blog.mapper.BlogTag;
import com.aqzscn.www.blog.service.BlogTagService;
import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.aqzscn.www.global.controller.BaseController;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Godbobo on 2019/5/25.
 */
@RestController
@RequestMapping("/blog")
@Api(tags = "博客标签数据接口")
public class BlogTagController extends BaseController {

    private final BlogTagService tagService;

    @Autowired
    public BlogTagController(BlogTagService tagService, HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.tagService = tagService;
    }

    @PostMapping("/tags")
    @ApiOperation("新增标签")
    public ReturnVo addTags(@Validated(ValidationGroup1.class) BlogTag tag, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return response(this.tagService.insert(tag));
    }

    @DeleteMapping("/tags/{id}")
    @ApiImplicitParam(paramType = "path", name = "id", value = "标签主键", required = true)
    @ApiOperation("删除标签")
    public ReturnVo deleteTags(@PathVariable Long id) throws RuntimeException {
        if (id == null) {
            throw AppException.of(ReturnError.VALIDATE_FAILED);
        }
        return response(this.tagService.deleteById(id));
    }

    @GetMapping("/tags")
    @ApiOperation("获取全部标签")
    public ReturnVo getAllTags() throws RuntimeException {
        ReturnVo returnVo = new ReturnVo();
        returnVo.setData(this.tagService.selectAll());
        return returnVo;
    }

}
