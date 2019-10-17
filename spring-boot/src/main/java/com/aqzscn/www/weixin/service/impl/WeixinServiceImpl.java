package com.aqzscn.www.weixin.service.impl;

import com.aqzscn.www.weixin.domain.CustomFilter;
import com.aqzscn.www.weixin.service.GarbageManager;
import com.aqzscn.www.weixin.service.HitokotoManager;
import com.aqzscn.www.weixin.service.MoneyManager;
import com.aqzscn.www.weixin.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import weixin.popular.bean.message.EventMessage;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextUtil")
public class WeixinServiceImpl implements WeixinService {

    private final List<CustomFilter> filters = new ArrayList<>();

    @Value("${spring.redis.keyPrefix.all}${spring.redis.keyPrefix.wxfunc}")
    private String redisPrefix;

    @Value("${myoptions.weixin.enable}")
    private Boolean enableService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public WeixinServiceImpl() {
        // 目前是手动添加过滤器，后面可尝试自定义注解，然后通过Spring容器来获取所有带有指定注解的类来动态读取配置
        filters.add(new MoneyManager());
        filters.add(new GarbageManager());
        filters.add(new HitokotoManager());
    }

    @Override
    public String doDispatch(EventMessage eventMessage) {
        if (!this.enableService) {
            return "微信服务未启动，请联系管理员。";
        }
        String str = "欢迎关注贯耳症！回复以下数字可使用对应功能：\n" +
                "1 记账\n" +
                "2 垃圾分类查询\n" +
                "3 一言(感动人心的句子)";
        String moduleName = "message.";
        Object obj = this.redisTemplate.opsForValue().get(redisPrefix + moduleName + eventMessage.getFromUserName());
        if (obj != null) {
            // 如果缓存中获取到用户正在使用的操作，就直接进入指定模块
            String menuId = obj.toString();
            for (CustomFilter filter : this.filters) {
                if (filter.getKey().equals(menuId)) {
                    filter.next(eventMessage);
                    str = filter.getResult();
                    break;
                }
            }
        } else {
            // 否则依据每个模块的判定规则进入
            for (CustomFilter filter : this.filters) {
                if (!filter.next(eventMessage)) {
                    str = filter.getResult();
                    break;
                }
            }
        }
        return str;
    }

}
