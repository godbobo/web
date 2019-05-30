package com.aqzscn.www.global.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Godbobo
 * @date 2019/5/28
 */
@ApiModel("复杂请求包装类")
@Data
public class ComplexRequest {

    @ApiModelProperty("请求类型")
    private String qType;

}
