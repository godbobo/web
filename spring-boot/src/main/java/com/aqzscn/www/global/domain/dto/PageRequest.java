package com.aqzscn.www.global.domain.dto;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
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

    @ApiModelProperty(value = "请求页码")
    @NotNull(message = "{pagerequest.pageNum.notnull}", groups = {ValidationGroup1.class})
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示数量")
    @NotNull(message = "{pagerequest.pageSize.notnull}", groups = {ValidationGroup1.class})
    private Integer pageSize;

}
