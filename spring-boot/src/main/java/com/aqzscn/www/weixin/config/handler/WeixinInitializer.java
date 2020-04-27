package com.aqzscn.www.weixin.config.handler;

import com.aqzscn.www.global.domain.co.GlobalCaches;
import com.aqzscn.www.weixin.domain.co.WeChatNames;
import org.apache.commons.lang3.StringUtils;
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

    @Value("${myoptions.weixin.enable}")
    private Boolean enableService;

    private final Logger logger = LoggerFactory.getLogger(WeixinInitializer.class);

    @Override
    public void run(ApplicationArguments args) {
        if (!this.enableService) {
            return;
        }
        this.logger.info("正在初始化微信服务...");
        String appId = GlobalCaches.PARAMS.get(WeChatNames.P_APPID);
        String appSecret = GlobalCaches.PARAMS.get(WeChatNames.P_APPSECRET);
        if (StringUtils.isAnyBlank(appId, appSecret)) {
            logger.error("未配置appid或appSecret，无法启动微信服务");
            return;
        }
        TokenManager.setDaemon(false);
        TokenManager.init(appId, appSecret);
        this.logger.info("微信服务初始化完成");
    }

}
