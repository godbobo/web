package com.aqzscn.www.global.service;

import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.mapper.User;

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

}
