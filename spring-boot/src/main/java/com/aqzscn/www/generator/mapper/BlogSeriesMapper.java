package com.aqzscn.www.generator.mapper;

import com.aqzscn.www.generator.pojo.BlogSeries;

public interface BlogSeriesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogSeries record);

    int insertSelective(BlogSeries record);

    BlogSeries selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogSeries record);

    int updateByPrimaryKey(BlogSeries record);
}