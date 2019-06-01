package com.aqzscn.www.blog.domain.po;

import com.aqzscn.www.blog.mapper.BlogArticle;
import com.aqzscn.www.blog.mapper.BlogTag;
import com.aqzscn.www.global.domain.dto.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Godbobo
 * @date 2019/05/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("博文数据请求对象")
public class ArticleRequest extends BlogArticle {

    @ApiModelProperty("是否请求基本数据（不包括文章）")
    private boolean isBase = true;

    @ApiModelProperty(value = "标签列表")
    private List<BlogTag> tagList;

    @ApiModelProperty(value = "分页对象")
    private PageRequest pageRequest;

}
