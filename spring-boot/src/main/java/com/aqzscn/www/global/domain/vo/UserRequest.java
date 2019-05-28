package com.aqzscn.www.global.domain.vo;

import com.aqzscn.www.global.mapper.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Godbobo
 * @date 2019/5/27
 */
@ApiModel("用户请求实体类")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends User {

    // 邮箱验证码
    @ApiModelProperty("邮箱验证码")
    private String mailCode;

}
