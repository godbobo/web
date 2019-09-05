package com.aqzscn.www.weixin.service;

import com.aqzscn.www.weixin.domain.CustomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.popular.bean.message.EventMessage;

/**
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/5 20:54
 */
public class GarbageManager implements CustomFilter {

    private final Logger logger = LoggerFactory.getLogger(GarbageManager.class);

    private String res;

    @Override
    public String getResult() {
        return this.res;
    }

    @Override
    public boolean next(EventMessage eventMessage) {
        this.logger.info("我是垃圾分类相关服务管理者");
        this.res = eventMessage.getContent() + ":garbage";
        return false;
    }
}
