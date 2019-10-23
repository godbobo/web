package com.aqzscn.www.global.service;

import com.aqzscn.www.global.domain.dto.MyPage;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.mapper.Role;
import com.aqzscn.www.global.mapper.User;

import java.util.List;

/**
 * 用户服务
 * @author Godbobo
 * @date 2019/5/26
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean reg(User user) throws RuntimeException;

    /**
     * 查询用户列表
     * @param user 用户查询参数
     * @param page 页码
     * @param pageSize 每页显示数量
     * @return MyPage
     */
    MyPage selectUser(User user, Integer page, Integer pageSize) throws RuntimeException;

    /**
     * 激活用户
     *
     * @param code 邮箱验证码
     * @param id 用户id
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean active(String code, Long id) throws RuntimeException;

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     * @throws RuntimeException 运行时异常
     */
    ReturnVo getUserInfo(String username) throws RuntimeException;

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 是否成功
     */
    boolean updateUser(User user) throws RuntimeException;

    /**
     * 根据用户id获取角色列表
     * @param id 用户id
     */
    List<Role> selectRoleByUid(Long id);

    /**
     * 修改用户角色
     * @param uid 用户id
     * @param roleName 角色名
     */
    boolean updateRolesByUid(Long uid, String roleName);

}
