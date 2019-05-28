package com.aqzscn.www.global.component;

import com.aqzscn.www.global.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 操作Redis工具类
 *
 * @author Godbobo
 * @date 2019/4/6.
 */
@Component
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;
    private final JacksonUtil jacksonUtil = new JacksonUtil();

    @Autowired
    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /* === String === */

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, String val) {
        redisTemplate.opsForValue().set(key, val);
    }

    /* === List === */

    // 根据键名获取全部list元素
    public <T> List<T> getList(String key, Class<T> clazz) {
        List<T> resList = new ArrayList<>();
        List<String> strList = redisTemplate.opsForList().range(key, 0, getListSize(key));
        if (strList != null) {
            for (String json : strList) {
                T t = jacksonUtil.readValue(json, clazz);
                resList.add(t);
            }
        }
        return resList;
    }

    // 新增元素
    public <T> void addList(String key, T t) {
        String json = jacksonUtil.toJson(t);
        redisTemplate.opsForList().rightPush(key, json);
    }

    // 删除元素
    public <T> void removeListItem(String key, T t) {
        String json = jacksonUtil.toJson(t);
        redisTemplate.opsForList().remove(key, 0, json);
    }

    // 获取列表长度
    public Long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /* === Key === */

    // 设置过期时间
    public void expireAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    // 判断key是否存在
    public boolean isExist(String key) {
        Boolean b = redisTemplate.hasKey(key);
        return b != null && b;
    }

    // 获取所有的key
    public Set<String> getAllKey(String pre) {
        return redisTemplate.keys(pre + "*");
    }
}
