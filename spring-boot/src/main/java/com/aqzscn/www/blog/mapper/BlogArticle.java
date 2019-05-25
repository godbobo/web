package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ApiModel("博文实体类")
public class BlogArticle {
    private Long id;

    @ApiModelProperty(value = "博文标题")
    @NotNull(message = "{blog.article.title.notnull}", groups = {ValidationGroup1.class})
    private String title;

    @ApiModelProperty(value = "博文摘要")
    @NotNull(message = "{blog.article.abstractTxt.notnull}", groups = {ValidationGroup1.class})
    private String abstractTxt;

    @ApiModelProperty(value = "博文配图")
    private String abstractImg;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "浏览量")
    private Long viewNum = 0L;

    @ApiModelProperty(value = "所属系列")
    private Long series;

    @ApiModelProperty(value = "排序")
    private Integer seriesOrder;

    @ApiModelProperty(value = "正文")
    @NotNull(message = "{blog.article.content.notnull}", groups = {ValidationGroup1.class})
    private String content;

}