package com.aqzscn.www.blog.service.impl;

import com.aqzscn.www.blog.mapper.BlogSeries;
import com.aqzscn.www.blog.mapper.BlogSeriesMapper;
import com.aqzscn.www.blog.service.BlogSeriesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 博客连载服务
 *
 * @author Godbobo
 * @date 2019/5/25.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogSeriesServiceImpl implements BlogSeriesService {

    private final BlogSeriesMapper seriesMapper;

    @Value("${myoptions.series.defaultimg")
    private String defaultImg;

    @Autowired
    public BlogSeriesServiceImpl(BlogSeriesMapper seriesMapper) {
        this.seriesMapper = seriesMapper;
    }

    @Override
    public List<BlogSeries> selectAll() throws RuntimeException {
        return this.seriesMapper.selectAll();
    }

    @Override
    public boolean insert(BlogSeries series) throws RuntimeException {
        if (StringUtils.isBlank(series.getImg())) {
            series.setImg(defaultImg);
        }
        return this.seriesMapper.insert(series) > 0;
    }

    @Override
    public boolean deleteById(Long id) throws RuntimeException {
        return seriesMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean batchDeleteByIds(String idStr) throws RuntimeException {
        List<String> idList = Arrays.asList(idStr.split(","));
        return seriesMapper.batchDeleteByIds(idList) > 0;
    }

    @Override
    public boolean updateById(BlogSeries series) throws RuntimeException {
        return seriesMapper.updateByPrimaryKey(series) > 0;
    }
}
