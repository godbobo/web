package com.aqzscn.www.global.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Godbobo on 2019/5/4.
 */
@Getter
@Setter
public class User {

    private String password; // 密码
    private String username; // 用户名
    private String loginName; // 登录名

}
