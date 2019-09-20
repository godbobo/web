package com.aqzscn.www.weixin.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weixin.popular.support.TokenManager;

import javax.annotation.PreDestroy;

@Component
public class WeixinCleaner {

    private final Logger logger = LoggerFactory.getLogger(WeixinCleaner.class);

    @Value("${myoptions.weixin.enable}")
    private Boolean enableService;

    @PreDestroy
    public void destroy() {
        if (!this.enableService) {
            return;
        }
        TokenManager.destroyed();
        this.logger.info("微信服务已关闭");
    }

}
