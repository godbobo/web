package com.aqzscn.www.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Created by Godbobo on 2019/5/10.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.aqzscn.www")) // 配置扫描的controller位置
                .paths(PathSelectors.any()) // 配置路径
                .build()
                .apiInfo(new ApiInfoBuilder() // 配置接口基本信息
                        .description("接口文档")
                        .contact(new Contact("郑博", "https://github.com/godbobo/", "980742324@qq.com")) // 配置作者信息
                        .version("v1.0")
                        .title("API测试文档")
                        .license("MIT")
                        .build());
    }
}
