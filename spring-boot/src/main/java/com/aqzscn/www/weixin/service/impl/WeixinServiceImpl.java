package com.aqzscn.www.weixin.service.impl;

import com.aqzscn.www.global.util.LettuceUtil;
import com.aqzscn.www.weixin.domain.CustomFilter;
import com.aqzscn.www.weixin.service.GarbageManager;
import com.aqzscn.www.weixin.service.HitokotoManager;
import com.aqzscn.www.weixin.service.MoneyManager;
import com.aqzscn.www.weixin.service.WeixinService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import weixin.popular.bean.message.EventMessage;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeixinServiceImpl implements WeixinService {

    private final List<CustomFilter> filters = new ArrayList<>();

    @Autowired
    private LettuceUtil lettuceUtil;

    @Value("${spring.redis.keyPrefix.all}${spring.redis.keyPrefix.wxfunc}")
    private String redisPrefix;

    public WeixinServiceImpl() {
        // 目前是手动添加过滤器，后面可尝试自定义注解，然后通过Spring容器来获取所有带有指定注解的类来动态读取配置
        filters.add(new MoneyManager());
        filters.add(new GarbageManager());
        filters.add(new HitokotoManager());
    }

    @Override
    public String doDispatch(EventMessage eventMessage) {
        String str = "欢迎关注贯耳症！回复以下数字可使用对应功能：\n" +
                "1 记账\n" +
                "2 垃圾分类查询\n" +
                "3 一言(感动人心的句子)";
        String menuId = lettuceUtil.get(redisPrefix + eventMessage.getFromUserName());
        if (StringUtils.isNotBlank(menuId)) {
            for(CustomFilter filter : this.filters) {
                if (filter.getKey().equals(menuId)) {
                    filter.next(eventMessage);
                    str = filter.getResult();
                    break;
                }
            }
        } else {
            for(CustomFilter filter : this.filters) {
                if(!filter.next(eventMessage)) {
                    str = filter.getResult();
                    break;
                }
            }
        }
        return str;
    }

}
