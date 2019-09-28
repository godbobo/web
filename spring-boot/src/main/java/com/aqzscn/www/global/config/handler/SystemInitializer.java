package com.aqzscn.www.global.config.handler;

import com.aqzscn.www.global.domain.co.GlobalCaches;
import com.aqzscn.www.global.mapper.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
    private final DispatchMapper dispatchMapper;
    private final DictMapper dictMapper;
    private final RestTemplate restTemplate;

    @Value("${myoptions.service.url}")
    private String serviceUrl;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    public SystemInitializer(RoleMapper roleMapper, ParamMapper paramMapper, RestTemplate restTemplate, DispatchMapper dispatchMapper, DictMapper dictMapper) {
        this.roleMapper = roleMapper;
        this.paramMapper = paramMapper;
        this.dispatchMapper = dispatchMapper;
        this.dictMapper = dictMapper;
        this.restTemplate = restTemplate;
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

        this.logger.info("正在获取字典列表...");
        List<Dict> dicts = this.dictMapper.selectDicts();
        List<Dict> types = this.dictMapper.selectTypes();
        for(Dict t : types) {
            GlobalCaches.DICTS.put(t.getCode(), new ArrayList<>());
        }
        for(Dict d : dicts) {
            GlobalCaches.DICTS.get(d.getCode()).add(d);
        }
        this.logger.info("字典表初始化完成，共获取到 {} 条记录，{} 种类别", dicts.size(), types.size());

        this.logger.info("正在初始化角色列表...");
        List<Role> roles = this.roleMapper.selectAll();
        for(Role r : roles) {
            GlobalCaches.ROLES.put(r.getName(), r.getId());
        }
        this.logger.info("角色列表初始化完成，共获取到 {} 条记录", roles.size());


        this.logger.info("正在初始化转发列表...");
        List<Dispatch> dispatches = this.dispatchMapper.selectAll();

        for (Dispatch d : dispatches) {
            if (d.getEnable() != null && d.getEnable().equals(1)) {
                GlobalCaches.DISPATCH = d;
                break;
            }
        }
        this.logger.info("转发列表初始化完成，共获取到 {} 条记录", dispatches.size());

        this.logger.info("正在获取本机公网地址...");
        try {
            ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(this.serviceUrl + this.contextPath + "/g/utils/public-host", String.class);
            if (StringUtils.isNoneBlank(responseEntity.getBody())) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseEntity.getBody());
                GlobalCaches.PUBLIC_IP = root.get("data").get("host").toString();
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        } finally {
            this.logger.info("本机公网IP地址为：{}", GlobalCaches.PUBLIC_IP);
        }


        long endTime = System.currentTimeMillis();
        this.logger.info("系统初始化完成，共花费 {} ms", endTime - startTime);
    }
}
