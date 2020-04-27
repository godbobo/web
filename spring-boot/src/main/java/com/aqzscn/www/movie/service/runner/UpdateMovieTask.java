package com.aqzscn.www.movie.service.runner;

import com.aqzscn.www.global.component.SpringContextUtil;
import com.aqzscn.www.movie.domain.MoviePostRequest;
import com.aqzscn.www.movie.mapper.MoviePost;
import com.aqzscn.www.movie.mapper.MoviePostMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateMovieTask implements Runnable {

    private String baseUrl;

    private RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);

    private MoviePostMapper postMapper = SpringContextUtil.getBean(MoviePostMapper.class);

    private Map<String, MoviePostRequest> mprMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(UpdateMovieTask.class);

    private static int count = 0;

    public UpdateMovieTask(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public void run() {
        // 1. 获取电影排行信息 内地、北美、全球  当天  当年  全部信息
        String[] areas = {"china", "NorthAmerica", "global"};
        ExecutorService exec = Executors.newFixedThreadPool(2);
        for (String area : areas) {
            // 获取当天电影排行
            exec.execute(new MovieRankTask(area, "MovieRankingDay", 0));
            // 获取当年电影排行
            for (int p = 0; p < 10; p++) {
                exec.execute(new MovieRankTask(area, "MovieRankingYear", p));
            }
        }
        // 等待线程任务完成
        exec.shutdown();
        while (!exec.isTerminated()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        exec = Executors.newCachedThreadPool();
        for (String key : mprMap.keySet()) {
            exec.execute(new MovieDetailTask(mprMap.get(key).getMtimeId(), key));
        }
        // 等待线程任务完成
        exec.shutdown();
        while (!exec.isTerminated()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<MoviePost> requests = postMapper.selectAllMovieId();
        Set<String> mtimeIdSet = new HashSet<>(requests.size());
        for (MoviePost mp : requests) {
            mtimeIdSet.add(mp.getMtimeId());
        }
        for (String movieKey : mprMap.keySet()) {
            if (mtimeIdSet.contains(movieKey)) {
                MoviePost moviePost = mprMap.get(movieKey);
                moviePost.setMtimeId(movieKey);
                postMapper.updateMoviePostById(moviePost);
            } else {
                postMapper.insert(mprMap.get(movieKey));
            }
        }
    }

    private class MovieRankTask implements Runnable {

        private String area;

        private String rankType;

        private Integer page;

        MovieRankTask(String area, String rankType, Integer page) {
            this.area = area;
            this.rankType = rankType;
            this.page = page;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<String, Object> rankMap = new HashMap<>();
            rankMap.put("area", area);
            rankMap.put("rankType", rankType);
            rankMap.put("page", page);
            String url = baseUrl + "rank?area={area}&rankType={rankType}&page={page}";
            try {
                ResponseEntity<String> res = restTemplate.getForEntity(url, String.class, rankMap);
                if (res.getStatusCode() == HttpStatus.OK && StringUtils.isNotBlank(res.getBody())) {
                    String str = res.getBody();
                    if (str == null) {
                        return;
                    }
                    // 获取到票房内容后将其转换为pojo对象
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.setDateFormat(new SimpleDateFormat("yyyy年MM月dd日"));
                    try {
                        JsonNode node = mapper.readTree(str);
                        if (node.isArray()) {
                            for (JsonNode obj : node) {
                                try {
                                    MoviePostRequest moviePostRequest = mapper.convertValue(obj, MoviePostRequest.class);
                                    // 舍弃重复电影的插入
                                    if (!mprMap.containsKey(moviePostRequest.getMtimeId())) {
                                        mprMap.put(moviePostRequest.getMtimeId(), moviePostRequest);
                                    }
                                } catch (Exception ife) {
                                    logger.error(ife.getMessage());
                                }

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class MovieDetailTask implements Runnable {

        private String id;

        private String key;

        MovieDetailTask(String id, String key) {
            this.id = id;
            this.key = key;
        }

        @Override
        public void run() {
            System.out.println(count++ + "次执行。。");
            if (StringUtils.isBlank(id)) {
                return;
            }
            String url = baseUrl + "detail/" + id;
            ObjectMapper  mapper = new ObjectMapper();
            ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
            if (res.getStatusCode() == HttpStatus.OK && StringUtils.isNotBlank(res.getBody())) {
                String detailStr = res.getBody();
                if (detailStr != null) {
                    try {
                        JsonNode  movieNode = mapper.readTree(detailStr);
                        mprMap.get(key).setMovieTime(movieNode.get("movieTime").asText(""));
                        mprMap.get(key).setGenre(movieNode.get("genre").asText(""));
                        mprMap.get(key).setIntro(movieNode.get("intro").asText(""));
                        mprMap.get(key).setCommentList(movieNode.get("commentList").toString());
                        mprMap.get(key).setCover(movieNode.get("cover").asText());
                        mprMap.get(key).setPlatforms(movieNode.get("platforms").toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
