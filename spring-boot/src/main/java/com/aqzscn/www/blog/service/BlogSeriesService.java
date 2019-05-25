package com.aqzscn.www.blog.service;

import com.aqzscn.www.blog.mapper.BlogSeries;

import java.util.List;

/**
 * Created by Godbobo on 2019/5/25.
 */
public interface BlogSeriesService {

    List<BlogSeries> selectAll() throws RuntimeException;

    boolean insert(BlogSeries series) throws RuntimeException;

}
