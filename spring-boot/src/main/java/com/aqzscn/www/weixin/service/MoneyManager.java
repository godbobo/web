package com.aqzscn.www.weixin.service;

import com.aqzscn.www.global.util.LettuceUtil;
import com.aqzscn.www.weixin.domain.CustomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import weixin.popular.bean.message.EventMessage;

import java.util.Calendar;

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

    @Value("${spring.redis.keyPrefix.all}${spring.redis.keyPrefix.wxfunc}")
    private String redisPrefix;

    @Autowired
    private RedisTemplate  redisTemplate;

    @Override
    public String getResult() {
        return this.res;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public boolean next(EventMessage eventMessage) {
        if (eventMessage.getContent().equals(this.key)) {
            String k = this.redisPrefix + eventMessage.getFromUserName();
            this.redisTemplate.opsForValue().set(k, this.key);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 30);
            this.redisTemplate.expireAt(k, calendar.getTime());
            this.res = "欢迎使用记账，回复以下数字使用对应功能：";
            return false;
        }
        this.logger.info("我是记账相关服务管理者");
        this.res = eventMessage.getContent() + ":money";
        return true;
    }
}
