package com.aqzscn.www.weixin.service.impl;

import com.aqzscn.www.weixin.domain.CustomFilter;
import com.aqzscn.www.weixin.service.GarbageManager;
import com.aqzscn.www.weixin.service.MoneyManager;
import com.aqzscn.www.weixin.service.WeixinService;
import org.springframework.stereotype.Service;
import weixin.popular.bean.message.EventMessage;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeixinServiceImpl implements WeixinService {

    private final List<CustomFilter> filters = new ArrayList<>();

    public WeixinServiceImpl() {
        // 目前是手动添加过滤器，后面可尝试自定义注解，然后通过Spring容器来获取所有带有指定注解的类来动态读取配置
        filters.add(new MoneyManager());
        filters.add(new GarbageManager());
    }

    @Override
    public String doDispatch(EventMessage eventMessage) {
        String str = eventMessage.getContent();
        for(CustomFilter filter : this.filters) {
            if(!filter.next(eventMessage)) {
                str = filter.getResult();
                break;
            }
        }
        return str;
    }

}
