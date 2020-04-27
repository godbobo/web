package com.aqzscn.www.movie.mapper;

import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
public interface MoviePostMapper extends Mapper<MoviePost> {

    /**
     * 查询全部电影id
     */
    List<MoviePost> selectAllMovieId();

    /**
     * 根据id更新电影
     */
    Integer updateMoviePostById(MoviePost moviePost);

}
