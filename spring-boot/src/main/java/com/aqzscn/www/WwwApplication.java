package com.aqzscn.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
public class WwwApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(WwwApplication.class, args);
    }

    @Override
    protected void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true); // 设置自动后缀路径模式匹配
    }

}
