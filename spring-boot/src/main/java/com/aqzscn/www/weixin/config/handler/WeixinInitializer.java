package com.aqzscn.www.weixin.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import weixin.popular.support.TokenManager;

@Component
@Order(2)
public class WeixinInitializer implements ApplicationRunner {

    @Value("${myoptions.weixin.appid}")
    private String appId;

    @Value("${myoptions.weixin.appsecret}")
    private String appSecret;

    @Value("${myoptions.weixin.enable}")
    private Boolean enableService;

    private final Logger logger = LoggerFactory.getLogger(WeixinInitializer.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!this.enableService) {
            return;
        }
        this.logger.info("正在初始化微信服务...");
        TokenManager.setDaemon(false);
        TokenManager.init(this.appId, this.appSecret);
        this.logger.info("微信服务初始化完成");
    }

}
