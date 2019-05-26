package com.aqzscn.www.global.mapper;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Setter
@ApiModel("用户实体类")
@JsonFilter("UserFilter")
public class User implements UserDetails {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("姓名")
    @NotBlank(message = "{user.realName.notblank}")
    private String realName;

    @ApiModelProperty("密码")
    @NotBlank(message = "{user.password.notblank}")
    private String password;

    @ApiModelProperty("用户名")
    @NotBlank(message = "{user.username.notblank}")
    private String username;

    @ApiModelProperty("性别")
    @NotBlank(message = "{user.gender.notblank}")
    private Integer gender;

    @ApiModelProperty("电话")
    private String tel;

    @ApiModelProperty("邮箱")
    @NotBlank(message = "{user.email.notblank}")
    @Email(message = "{user.email.email}")
    private String email;

    @ApiModelProperty("头像")
    private String headImg;

    @ApiModelProperty("是否锁定")
    private Integer locked;

    @ApiModelProperty("是否可用")
    private Integer enabled;

    private List<Role> roles;

    @ApiModelProperty("签名")
    private String sign;

    @ApiModelProperty("注册时间")
    private Date regTime;

    /**
     * 获取当前用户对象所具有的角色信息
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role: roles){
            // 基于数据库的角色认证需要加上 ROLE_ 前缀
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }

    /**
     * 判断账户是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 判断账户是否锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return locked == 0;
    }

    /**
     * 判断账户密码是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 当前账户是否可用
     */
    @Override
    public boolean isEnabled() {
        return enabled == 1;
    }

    public Long getId() {
        return id;
    }

    /**
     * 因为这里的username是作为用户的真实姓名字段来使用的，因此需要调整登录名和姓名的输出
     * 获取登录名
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 获取用户姓名
     */
    public String getRealName() {
        return realName;
    }

    @Override
    public String getPassword() {
        return password;
    }


    public Integer getGender() {
        return gender;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getHeadImg() {
        return headImg;
    }

    public Integer getLocked() {
        return locked;
    }

    public String getSign() {return sign;}

    public Date getRegTime() {
        return regTime;
    }
}