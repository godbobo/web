package com.aqzscn.www.weixin.utils;

import org.springframework.data.redis.core.RedisTemplate;
import weixin.popular.bean.message.EventMessage;

import java.util.Calendar;

/**
 * 封装请求方法
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/29 17:58
 */
public class WrapperUtil {

    /**
     * 进入某个模块后的设置
     * @param eventMessage 微信消息对象
     * @param redisPrefix redis前缀
     * @param moduleName 模块名
     * @param redisTemplate redis操作类
     * @param key 模块代码
     */
    public static void enterModule(EventMessage eventMessage, String redisPrefix, String moduleName, RedisTemplate<String, Object> redisTemplate, String key) {
        String k = redisPrefix + moduleName + eventMessage.getFromUserName();
        redisTemplate.opsForValue().set(k, key);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        redisTemplate.expireAt(k, calendar.getTime());
    }

}
