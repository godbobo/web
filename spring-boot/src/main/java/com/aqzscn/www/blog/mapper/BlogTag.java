package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * @author Godbobo
 * @date 2019/05/31
 */
@Getter
@Setter
@ApiModel("标签实体类")
public class BlogTag {

    @Id
    private Long id;

    @ApiModelProperty(value = "标签名", required = true)
    @NotBlank(message = "{blog.tag.title.notblank}", groups = {ValidationGroup1.class})
    private String title;

    @ApiModelProperty(value = "字体颜色", required = true)
    @NotBlank(message = "{blog.tag.color.notblank}", groups = {ValidationGroup1.class})
    private String color;

    @ApiModelProperty(value = "背景颜色", required = true)
    @NotBlank(message = "{blog.tag.bg.notblank}", groups = {ValidationGroup1.class})
    private String bg;

}
