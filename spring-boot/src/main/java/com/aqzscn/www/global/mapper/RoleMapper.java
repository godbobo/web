package com.aqzscn.www.global.mapper;

import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.Table;
import java.util.List;

/**
 * 角色数据接口
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Component
@Table(name = "g_role")
public interface RoleMapper extends Mapper<Role> {

    /**
     * 新增角色
     * @param role 角色信息
     * @return 影响行数
     */
    int insert(Role role);

    /**
     * 获取全部角色
     *
     * @return 角色列表
     */
    List<Role> selectAll();

}