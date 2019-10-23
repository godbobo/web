package com.aqzscn.www.global.mapper;

import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.Table;
import java.util.List;

/**
 * 用户数据接口
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Component
public interface UserMapper extends Mapper<User> {
    /**
     * 根据登录名获取用户信息
     *
     * @param username 登录名
     * @return User
     */
    User loadUserByUsername(String username);

    /**
     * 根据用户id获取角色列表信息
     *
     * @param id 用户id（不同于登录名）
     * @return List<Role>
     */
    List<Role> getRolesByUid(Long id);

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 为用户设置角色
     * @param uid 用户主键
     * @param rid 角色主键
     * @return 影响行数
     */
    int setRoles(Long uid, Long rid);

    /**
     * 删除用户角色
     * @param uid 用户主键
     * @param rid 角色主键
     */
    int deleteRoles(Long uid, Long rid);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 影响行数
     */
    int update(User user);

    // 查询用户列表
    List<User> selectUser(User user);

}