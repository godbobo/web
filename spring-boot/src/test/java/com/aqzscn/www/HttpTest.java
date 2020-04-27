package com.aqzscn.www;

import com.aqzscn.www.global.util.JacksonUtil;
import com.aqzscn.www.global.util.StringUtil;
import com.aqzscn.www.movie.service.runner.UpdateMovieTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class HttpTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testGet() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet("http://127.0.0.1:8000/movie/rank/");

        CloseableHttpResponse response = null;

        try {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000)
                    .setRedirectsEnabled(true).build();

            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-type", "application/json;charset=utf-8");
            httpGet.setHeader("Accept", "application/json");
            response = httpClient.execute(httpGet);

            HttpEntity httpEntity = response.getEntity();
            System.out.println("响应状态：" + response.getStatusLine());
            if (httpEntity != null) {
                System.out.println("响应长度："+ httpEntity.getContentLength());
                System.out.println("响应内容："+ EntityUtils.toString(httpEntity));
//                System.out.println("encode:"+ new String(EntityUtils.toByteArray(httpEntity), StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testRank() {
        String url =  "http://127.0.0.1:8000/movie/rank/";
        try {
            ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
            if (res.getStatusCode() == HttpStatus.OK && StringUtils.isNotBlank(res.getBody())) {
                System.out.println(StringUtil.getEncoding(res.getBody()));
                System.out.println(new String(res.getBody().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void MovieTest () {
        new UpdateMovieTask("http://127.0.0.1:8000/movie/").run();
        System.out.println("任务开始执行了");
    }

}
