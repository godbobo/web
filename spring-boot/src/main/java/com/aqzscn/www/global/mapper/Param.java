package com.aqzscn.www.global.mapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Godbobo
 * @date 2019/6/1
 */
@Data
@ApiModel("系统参数表")
public class Param implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("参数名")
    @NotBlank(message = "{param.label.notblank}")
    private String label;

    @ApiModelProperty("值")
    @NotBlank(message = "{param.val.notblank}")
    private String val;

    @ApiModelProperty("中文名")
    @NotBlank(message = "{param.zh.notblank}")
    private String zh;

}
