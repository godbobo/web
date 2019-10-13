package com.aqzscn.www.global.mapper;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Table(name = "g_user")
public class User implements UserDetails {
    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("姓名")
    @NotBlank(message = "{user.realname.notblank}", groups = {ValidationGroup1.class})
    private String realName;

    @ApiModelProperty("密码")
    @NotBlank(message = "{user.password.notblank}", groups = {ValidationGroup1.class})
    private String password;

    @ApiModelProperty("用户名")
    @NotBlank(message = "{user.username.notblank}")
    private String username;

    @ApiModelProperty("性别")
    @NotNull(message = "{user.gender.notnull}", groups = {ValidationGroup1.class})
    private Integer gender;

    @ApiModelProperty("电话")
    private String tel;

    @ApiModelProperty("邮箱")
    @NotBlank(message = "{user.email.notblank}", groups = {ValidationGroup1.class})
    @Email(message = "{user.email.email}", groups = {ValidationGroup1.class})
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
        if (roles != null) {
            for (Role role: roles){
                // 基于数据库的角色认证需要加上 ROLE_ 前缀
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
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
        return locked != null && locked == 0;
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
        return enabled != null && enabled == 1;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }
}