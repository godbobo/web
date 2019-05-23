package com.aqzscn.www.global.domain;

import com.aqzscn.www.global.domain.validation.ValidationGroup1;
import com.aqzscn.www.global.domain.validation.ValidationGroup2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by Godbobo on 2019/5/4.
 */
@Getter
@Setter
@ApiModel(value = "用户实体类")
public class User {

    @ApiModelProperty(value = "密码", required = true)
    @NotNull(message = "{user.password.notnull}", groups = {ValidationGroup1.class, ValidationGroup2.class})
    private String password; // 密码
    @NotNull(message = "{user.username.notnull}", groups = ValidationGroup1.class)
    @ApiModelProperty(value = "用户名")
    private String username; // 用户名
    @NotNull(message = "{user.loginName.notnull}", groups = {ValidationGroup1.class, ValidationGroup2.class})
    @ApiModelProperty(value = "登录名", required = true)
    private String loginName; // 登录名

}
