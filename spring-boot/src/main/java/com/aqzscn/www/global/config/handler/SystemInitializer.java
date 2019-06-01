package com.aqzscn.www.global.config.handler;

import com.aqzscn.www.global.domain.co.GlobalCaches;
import com.aqzscn.www.global.mapper.Param;
import com.aqzscn.www.global.mapper.ParamMapper;
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
    private final ParamMapper paramMapper;

    @Autowired
    public SystemInitializer(RoleMapper roleMapper, ParamMapper paramMapper) {
        this.roleMapper = roleMapper;
        this.paramMapper = paramMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long startTime = System.currentTimeMillis();
        this.logger.info("正在获取系统参数列表...");
        List<Param> params = this.paramMapper.selectAll();
        for (Param p: params){
            GlobalCaches.PARAMS.put(p.getLabel(), p.getVal());
        }
        this.logger.info("系统参数列表初始化完成，共获取到 {} 条记录", params.size());
        this.logger.info("正在初始化角色列表...");
        List<Role> roles = this.roleMapper.selectAll();
        for(Role r : roles) {
            GlobalCaches.ROLES.put(r.getName(), r.getId());
        }
        this.logger.info("角色列表初始化完成，共获取到 {} 条记录", roles.size());
        long endTime = System.currentTimeMillis();
        this.logger.info("系统初始化完成，共花费 {} ms", endTime - startTime);
    }
}
