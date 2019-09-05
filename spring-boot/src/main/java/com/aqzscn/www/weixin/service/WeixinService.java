package com.aqzscn.www.weixin.service;

import weixin.popular.bean.message.EventMessage;

/**
 * 微信服务处理
 */
public interface WeixinService {

    /**
     * 处理用户输入（分发到具体的服务）
     * @param eventMessage 消息体
     * @return 回复消息内容
     */
    String doDispatch(EventMessage eventMessage);

}
