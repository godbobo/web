package com.aqzscn.www.movie.service.impl;

import com.aqzscn.www.movie.domain.MoviePostRequest;
import com.aqzscn.www.movie.mapper.MoviePost;
import com.aqzscn.www.movie.mapper.MoviePostMapper;
import com.aqzscn.www.movie.service.MovieService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class MovieServiceImpl implements MovieService {

    private final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
    private final MoviePostMapper postMapper;
    private final RestTemplate restTemplate;
    private String baseMovieUrl = "http://127.0.0.1:8000/movie/";

    public MovieServiceImpl(MoviePostMapper postMapper, RestTemplate restTemplate) {
        this.postMapper = postMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean updateMovieInfo() {
        // 1. 获取电影排行信息 内地、北美、全球  当天  当年  全部信息
        String[] areas = {"china", "NorthAmerica", "global"};
        Map<String, MoviePostRequest> mprMap = new HashMap<>();
        for (String area : areas) {
            // 获取当天电影排行
            String rankStr = getMtimeRank(area, "MovieRankingDay", 0);
            parseMovieRankList(mprMap, rankStr);
            // 获取当年电影排行
            for (int p = 0; p < 10; p++) {
                rankStr = getMtimeRank(area, "MovieRankingYear", p);
                parseMovieRankList(mprMap, rankStr);
            }
        }
        // 票房排行中的电影信息是不完善的，需要循环获取电影详情信息
        ObjectMapper mapper = new ObjectMapper();

        for (String key : mprMap.keySet()) {
            try {
                String detailStr = getMtimeDetail(key);
                if (detailStr != null) {
                    JsonNode movieNode = mapper.readTree(detailStr);
                    mprMap.get(key).setMovieTime(movieNode.get("movieTime").asText(""));
                    mprMap.get(key).setGenre(movieNode.get("genre").asText(""));
                    mprMap.get(key).setIntro(movieNode.get("intro").asText(""));
                    mprMap.get(key).setCommentList(movieNode.get("commentList").toString());
                    mprMap.get(key).setCover(movieNode.get("cover").asText());
                    mprMap.get(key).setPlatforms(movieNode.get("platforms").toString());
                }
            } catch (Exception e) {
                this.logger.error(e.getMessage());
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
        return true;
    }

    /**
     * 将排行结果转换为电影信息插入map中
     *
     * @param map 电影集合
     * @param str json字符串
     */
    private void parseMovieRankList(Map<String, MoviePostRequest> map, String str) {
        if (map == null || str == null) {
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
                        if (!map.containsKey(moviePostRequest.getMtimeId())) {
                            map.put(moviePostRequest.getMtimeId(), moviePostRequest);
                        }
                    } catch (Exception ife) {
                        this.logger.error(ife.getMessage());
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取时光网票房信息
     *
     * @param area     地区  "china", "NorthAmerica", "global"
     * @param rankType 排行类型 MovieRankingDay MovieRankingYear
     * @param page     页数 0开始 MovieRankingDay 只有第一页
     */
    private String getMtimeRank(String area, String rankType, int page) {
        Map<String, Object> rankMap = new HashMap<>();
        rankMap.put("area", area);
        rankMap.put("rankType", rankType);
        rankMap.put("page", page);
        String url = baseMovieUrl + "rank?area={area}&rankType={rankType}&page={page}";
        try {
            ResponseEntity<String> res = restTemplate.getForEntity(url, String.class, rankMap);
            if (res.getStatusCode() == HttpStatus.OK && StringUtils.isNotBlank(res.getBody())) {
                return res.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取电影详情
     *
     * @param id 时光网电影id
     */
    private String getMtimeDetail(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        String url = baseMovieUrl + "detail/" + id;
        ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
        if (res.getStatusCode() == HttpStatus.OK && StringUtils.isNotBlank(res.getBody())) {
            return res.getBody();
        } else {
            return null;
        }
    }

}


