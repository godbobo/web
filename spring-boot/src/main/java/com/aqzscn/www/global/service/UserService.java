package com.aqzscn.www.global.service;

import com.aqzscn.www.global.mapper.User;

/**
 * 用户服务
 * @author Godbobo
 * @date 2019/5/26
 */
public interface UserService {

    /**
     * 用户注册
     * @param user 用户信息
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean reg(User user) throws RuntimeException;

}
