package com.aqzscn.www;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.redis.keyPrefix.all}${spring.redis.keyPrefix.wxfunc}")
    private String redisPrefix;

    @Test
    public void set() {
        redisTemplate.opsForValue().set(redisPrefix + "set1", "testValue1");
        redisTemplate.opsForSet().add(redisPrefix + "set2", "asdf");
        redisTemplate.opsForHash().put("hash1", "name1", "lms1");
        redisTemplate.opsForHash().put("hash1", "name2", "lms2");
        redisTemplate.opsForHash().put("hash1", "name3", "lms3");
        System.out.println(redisTemplate.opsForValue().get("test:set"));
        System.out.println(redisTemplate.opsForHash().get("hash1", "name1"));
    }
}