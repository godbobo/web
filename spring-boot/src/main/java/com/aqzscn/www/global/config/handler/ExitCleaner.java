package com.aqzscn.www.global.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 退出应用时执行一些清理工作
 *
 * @author Godbobo
 * @date 2019/5/31
 */
@Component
public class ExitCleaner {

    private final Logger logger = LoggerFactory.getLogger(ExitCleaner.class);

    @PreDestroy
    public void destroy() {
        this.logger.info("正在关闭...");
    }

}
