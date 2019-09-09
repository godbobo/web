package com.aqzscn.www.weixin.service;

import com.aqzscn.www.global.util.JacksonUtil;
import com.aqzscn.www.global.util.LettuceUtil;
import com.aqzscn.www.weixin.domain.CustomFilter;
import com.aqzscn.www.weixin.domain.vo.Hitokoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import weixin.popular.bean.message.EventMessage;

import java.util.Calendar;
import java.util.Date;

/**
 * 一言服务
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/6 18:28
 */
@Component
public class HitokotoManager implements CustomFilter {

    private String res;

    private final String url = "https://v1.hitokoto.cn";

    private final RestTemplate restTemplate = new RestTemplate();

    private final String key = "3";

    @Value("${spring.redis.keyPrefix.all}${spring.redis.keyPrefix.wxfunc}")
    private String redisPrefix;

    private LettuceUtil lettuceUtil = new LettuceUtil();

//    public HitokotoManager(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

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
        // 进入一言功能，30分钟内退出
        if (eventMessage.getContent().equals(this.key)) {
            String k = this.redisPrefix + eventMessage.getFromUserName();
            this.lettuceUtil.set(key, this.key);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 30);
            this.lettuceUtil.expireAt(k, calendar.getTime());
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
}
