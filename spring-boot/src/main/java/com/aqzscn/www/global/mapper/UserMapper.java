package com.aqzscn.www.global.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户数据接口
 * @author Godbobo
 * @date 2019/5/26
 */
@Mapper
@Component
public interface UserMapper {
    /**
     * 根据登录名获取用户信息
     * @param username 登录名
     * @return User
     */
    User loadUserByUsername(String username);

    /**
     * 根据用户id获取角色列表信息
     * @param id 用户id（不同于登录名）
     * @return List<Role>
     */
    List<Role> getRolesByUid(Long id);

    /**
     * 新增用户
     * @param user 用户信息
     * @return 影响行数
     */
    int insert(User user);

}