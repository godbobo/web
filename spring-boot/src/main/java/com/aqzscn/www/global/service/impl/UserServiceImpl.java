package com.aqzscn.www.global.service.impl;

import com.aqzscn.www.global.mapper.User;
import com.aqzscn.www.global.mapper.UserMapper;
import com.aqzscn.www.global.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务 包括Spring Security认证相关内容
 * @author Godbobo
 * @date 2019/5/26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 为SpringSecurity提供用户信息以判断用户身份
     * @param s 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 用户名未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userMapper.loadUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        user.setRoles(this.userMapper.getRolesByUid(user.getId()));
        return user;
    }

    @Override
    public boolean reg(User user) throws RuntimeException {
        return false;
    }
}
