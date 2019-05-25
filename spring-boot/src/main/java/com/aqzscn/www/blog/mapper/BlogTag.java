package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel("标签实体类")
public class BlogTag {

    private Long id;

    @ApiModelProperty(value = "标签名", required = true)
    @NotNull(message = "{blog.tag.title.notnull}", groups = {ValidationGroup1.class})
    private String title;

    @ApiModelProperty(value = "字体颜色", required = true)
    @NotNull(message = "{blog.tag.color.notnull}", groups = {ValidationGroup1.class})
    private String color;

    @ApiModelProperty(value = "背景颜色", required = true)
    @NotNull(message = "{blog.tag.bg.notnull}", groups = {ValidationGroup1.class})
    private String bg;

}
