package com.aqzscn.www.global.domain.vo;

import com.aqzscn.www.global.config.validation.ValidationGroup3;
import com.aqzscn.www.global.mapper.User;
import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author Godbobo
 * @date 2019/5/27
 */
@ApiModel("用户请求实体类")
@JsonFilter("UserFilter")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends User {

    @ApiModelProperty("角色名")
    @NotBlank(message = "角色名不能为空", groups = {ValidationGroup3.class})
    private String roleName;

    // 邮箱验证码
    @ApiModelProperty("邮箱验证码")
    private String mailCode;

}
