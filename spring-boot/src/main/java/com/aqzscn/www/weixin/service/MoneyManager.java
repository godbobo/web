package com.aqzscn.www.weixin.service;

import com.aqzscn.www.global.component.SpringContextUtil;
import com.aqzscn.www.weixin.domain.CustomFilter;
import com.aqzscn.www.weixin.utils.WrapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import weixin.popular.bean.message.EventMessage;

/**
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/5 20:53
 */
@Component
public class MoneyManager implements CustomFilter {

    private final Logger logger = LoggerFactory.getLogger(MoneyManager.class);

    private String res;

    private final String key = "1";

    private final String moduleName = "message.";

    private String redisPrefix;

    private RedisTemplate<String, Object> redisTemplate = SpringContextUtil.getBean(RedisTemplate.class);

    public MoneyManager() {
        this.redisPrefix = SpringContextUtil.getProperty("spring.redis.keyPrefix.all", String.class) + SpringContextUtil.getProperty("spring.redis.keyPrefix.wxfunc", String.class);
    }

    @Override
    public boolean next(EventMessage eventMessage) {
        if (eventMessage.getContent().equals(this.key)) {
            WrapperUtil.enterModule(eventMessage, this.redisPrefix, this.moduleName, this.redisTemplate, this.key);
            this.res = "欢迎使用记账，回复以下数字使用对应功能：";
            return false;
        }
        this.logger.info("我是记账相关服务管理者");
        this.res = eventMessage.getContent() + ":money";
        return true;
    }


    @Override
    public String getResult() {
        return this.res;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
