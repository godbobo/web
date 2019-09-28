package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.aqzscn.www.global.config.validation.ValidationGroup2;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * VG1:添加时的验证
 * VG2:更新时的验证
 *
 * @author Godbobo
 * @date 2019/05/31
 */
@Getter
@Setter
@ApiModel("博文连载")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogSeries {
    @Id
    @ApiModelProperty(value = "主键")
    @NotNull(message = "{blog.series.id.notnull", groups = {ValidationGroup2.class})
    private Long id;

    @ApiModelProperty(value = "连载名")
    @NotBlank(message = "{blog.series.name.notblank}", groups = {ValidationGroup1.class})
    private String name;

    @ApiModelProperty(value = "是否为章节名添加索引")
    private Integer autoIndex = 0;

    @ApiModelProperty("文章数量（非数据表字段）")
    private Integer articleNum = 0;

    @ApiModelProperty(value = "简介")
    @NotBlank(message = "{blog.series.abs.notblank}", groups = {ValidationGroup1.class})
    private String abs;

    @ApiModelProperty(value = "配图")
    @NotBlank(message = "{blog.series.img.notblank}")
    private String img;

    @ApiModelProperty(value = "是否忽略博文排序")
    private Integer ignoreOrder = 0;
}