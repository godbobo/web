package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Godbobo
 * @date 2019/05/31
 */
@Getter
@Setter
@ApiModel("博文连载")
public class BlogSeries {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "连载名")
    @NotBlank(message = "{blog.series.name.notblank}", groups = {ValidationGroup1.class})
    private String name;

    @ApiModelProperty(value = "是否为章节名添加索引")
    private Integer autoIndex = 0;

    @ApiModelProperty(value = "简介")
    @NotBlank(message = "{blog.series.abs.notblank}", groups = {ValidationGroup1.class})
    private String abs;

    @ApiModelProperty(value = "配图")
    @NotBlank(message = "{blog.series.img.notblank}", groups = {ValidationGroup1.class})
    private String img;

    @ApiModelProperty(value = "是否忽略博文排序")
    private Integer ignoreOrder = 0;
}