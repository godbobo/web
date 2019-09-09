package com.aqzscn.www.weixin.domain;

import weixin.popular.bean.message.EventMessage;

/**
 * 自定义过滤器
 * 为实现微信消息被动回复而做
 */
public interface CustomFilter {

    /**
     * 获取返回值
     * @return 消息内容
     */
    String getResult();

    /**
     * 获取功能代码
     */
    String getKey();

    /**
     * 是否执行下一个过滤器
     * @param eventMessage 微信消息体
     * @return 布尔值
     */
    boolean next(EventMessage eventMessage);

}
