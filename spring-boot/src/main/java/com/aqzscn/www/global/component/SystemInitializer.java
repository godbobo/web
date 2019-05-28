package com.aqzscn.www.global.component;

import com.aqzscn.www.global.domain.co.GlobalCaches;
import com.aqzscn.www.global.mapper.Role;
import com.aqzscn.www.global.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统启动时执行一些初始化操作
 *
 * @author Godbobo
 * @date 2019/5/28
 */
@Component
@Order(1)
public class SystemInitializer implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(SystemInitializer.class);

    private final RoleMapper roleMapper;

    @Autowired
    public SystemInitializer(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.logger.info("正在初始化角色列表...");
        List<Role> roles = this.roleMapper.selectAll();
        for(Role r : roles) {
            GlobalCaches.ROLES.put(r.getName(), r.getId());
        }
    }
}
