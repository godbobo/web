package com.aqzscn.www.weixin.service;

import com.aqzscn.www.weixin.domain.CustomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.popular.bean.message.EventMessage;

/**
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/5 20:53
 */
public class MoneyManager implements CustomFilter {

    private final Logger logger = LoggerFactory.getLogger(MoneyManager.class);

    private String res;

    @Override
    public String getResult() {
        return this.res;
    }

    @Override
    public boolean next(EventMessage eventMessage) {
        this.logger.info("我是记账相关服务管理者");
        this.res = eventMessage.getContent() + ":money";
        return true;
    }
}
