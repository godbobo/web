package com.aqzscn.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 启动类
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@SpringBootApplication
@EnableScheduling
public class WwwApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(WwwApplication.class, args);
    }

    @Override
    protected void configurePathMatch(PathMatchConfigurer configurer) {
        // 设置自动后缀路径模式匹配
        configurer.setUseTrailingSlashMatch(true);
    }

    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
     * 需要重新指定静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
