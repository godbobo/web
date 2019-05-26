package com.aqzscn.www.global.service;

import com.aqzscn.www.global.mapper.Role;

/**
 * 角色服务
 * @author Godbobo
 * @date 2019/5/26
 */
public interface RoleService {

    /**
     * 新增角色
     * @param role 角色信息
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean addRole(Role role) throws RuntimeException;

}
