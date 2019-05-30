package com.aqzscn.www.global.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Godbobo
 * @date 2019/5/25.
 */
@Data
@ApiModel("分页请求包装类")
public class PageRequest {

    @ApiModelProperty(value = "请求页码", required = true)
    @NotNull(message = "{pagerequest.pageNum.notnull}")
    private int pageNum = 1;

    @ApiModelProperty(value = "每页显示数量", required = true)
    @NotNull(message = "{pagerequest.pageSize.notnull}")
    private int pageSize = 2;

}
