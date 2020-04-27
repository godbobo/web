package com.aqzscn.www.global.config;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(httpComponentsClientHttpRequestFactory());
        // 设置消息转换器的编码格式，防止发出的请求中文乱码
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
//        for(HttpMessageConverter<?> httpMessageConverter : list) {
//            if (httpMessageConverter instanceof StringHttpMessageConverter) {
//                ((StringHttpMessageConverter)  httpMessageConverter).setDefaultCharset(StandardCharsets.UTF_8);
//            }
//        }
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClientBuilder().build());
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(15000);
        return  factory;
    }

    @Bean
    public HttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(30); // 线程池最大连接数
        connectionManager.setDefaultMaxPerRoute(20); // 并发数
        return connectionManager;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 设置Http连接管理器
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        return httpClientBuilder;
    }

}
