package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author Godbobo
 * @date 2019/05/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("博文实体类")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogArticle {
    @Id
    private Long id;

    @ApiModelProperty(value = "博文标题")
    @NotBlank(message = "{blog.article.title.notblank}", groups = {ValidationGroup1.class})
    private String title;

    @ApiModelProperty(value = "博文摘要")
    private String abstractTxt;

    @ApiModelProperty(value = "博文配图")
    private String abstractImg;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "浏览量")
    private Long viewNum = 0L;

    @ApiModelProperty(value = "所属系列")
    private Long series;

    @ApiModelProperty("连载名称")
    private String seriesName;

    @ApiModelProperty(value = "排序")
    private Integer seriesOrder;

    @ApiModelProperty(value = "正文")
    @NotBlank(message = "{blog.article.content.notblank}", groups = {ValidationGroup1.class})
    private String content;

}