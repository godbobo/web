package com.aqzscn.www.global.domain;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "用户实体类")
public class User {
//    @ApiModelProperty(value = "密码", required = true)
//    @NotNull(message = "{user.password.notnull}", groups = {ValidationGroup1.class, ValidationGroup2.class})
//    private String password; // 密码
//    @NotNull(message = "{user.username.notnull}", groups = ValidationGroup1.class)
//    @ApiModelProperty(value = "用户名")
//    private String username; // 用户名
//    @NotNull(message = "{user.loginName.notnull}", groups = {ValidationGroup1.class, ValidationGroup2.class})
//    @ApiModelProperty(value = "登录名", required = true)
//    private String loginName; // 登录名

    private Long id;

    private String loginName;

    private String password;

    private String username;

    private Integer gender;

    private String tel;

    private String email;

    private String headImg;

}