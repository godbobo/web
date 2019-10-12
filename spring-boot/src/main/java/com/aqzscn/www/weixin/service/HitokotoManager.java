package com.aqzscn.www.weixin.service;

import com.aqzscn.www.global.component.SpringContextUtil;
import com.aqzscn.www.global.util.JacksonUtil;
import com.aqzscn.www.weixin.domain.CustomFilter;
import com.aqzscn.www.weixin.domain.vo.Hitokoto;
import com.aqzscn.www.weixin.utils.WrapperUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import weixin.popular.bean.message.EventMessage;

/**
 * 一言服务
 *
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/6 18:28
 */
@Component
public class HitokotoManager implements CustomFilter {

    private String res;

    private final String url = "https://v1.hitokoto.cn";

    private final String key = "3";

    private final String moduleName = "message.";

    private String redisPrefix;

    private RedisTemplate<String, Object> redisTemplate = SpringContextUtil.getBean(RedisTemplate.class);

    private RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);

    public HitokotoManager() {
        this.redisPrefix = SpringContextUtil.getProperty("spring.redis.keyPrefix.all", String.class) + SpringContextUtil.getProperty("spring.redis.keyPrefix.wxfunc", String.class);
    }

    @Override
    public boolean next(EventMessage eventMessage) {
        // 进入一言功能，30分钟内退出
        if (eventMessage.getContent().equals(this.key)) {
            WrapperUtil.enterModule(eventMessage, this.redisPrefix, this.moduleName, this.redisTemplate, this.key);
            this.res = "欢迎使用一言，回复以下数字使用对应功能：";
            return false;
        }
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(this.url + "?c=b", String.class);
        if (responseEntity.getBody() != null) {
            JacksonUtil jacksonUtil = new JacksonUtil();
            Hitokoto hitokoto = jacksonUtil.readValue(responseEntity.getBody(), Hitokoto.class);
            this.res = hitokoto.getHitokoto() + "\n\n  from " + hitokoto.getFrom();
            return false;
        } else {
            return true;
        }
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
