package com.aqzscn.www.blog.service.imp;

import com.aqzscn.www.blog.mapper.BlogSeries;
import com.aqzscn.www.blog.mapper.BlogSeriesMapper;
import com.aqzscn.www.blog.service.BlogSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 博客连载服务
 * Created by Godbobo on 2019/5/25.
 */
@Service
@Transactional
public class BlogSeriesServiceImp implements BlogSeriesService {

    private final BlogSeriesMapper seriesMapper;

    @Autowired
    public BlogSeriesServiceImp(BlogSeriesMapper seriesMapper) {
        this.seriesMapper = seriesMapper;
    }

    @Override
    public List<BlogSeries> selectAll() throws RuntimeException {
        return this.seriesMapper.selectAll();
    }

    @Override
    public boolean insert(BlogSeries series) throws RuntimeException {
        return this.seriesMapper.insert(series) > 0;
    }
}
