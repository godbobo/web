package com.aqzscn.www.global.mapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 角色实体类
 *
 * 该类必须实现Serializable接口，否则授权服务在反序列化该类时就会产生异常
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@ApiModel("角色实体类")
public class Role implements Serializable {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("角色名（英文表示）")
    @NotBlank(message = "{role.name.notblank}")
    private String name;

    @ApiModelProperty("备注（角色中文信息）")
    @NotBlank(message = "{role.remark.notblank}")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}