package com.aqzscn.www.global.service.impl;

import com.aqzscn.www.global.domain.co.GlobalCaches;
import com.aqzscn.www.global.mapper.Role;
import com.aqzscn.www.global.mapper.RoleMapper;
import com.aqzscn.www.global.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色服务
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public boolean addRole(Role role) throws RuntimeException {
        if (this.roleMapper.insert(role) > 0) {
            GlobalCaches.ROLES.put(role.getName(), role.getId());
            return true;
        }else {
            return false;
        }
    }
}
