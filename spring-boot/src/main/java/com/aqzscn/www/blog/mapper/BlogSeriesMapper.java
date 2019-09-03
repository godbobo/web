package com.aqzscn.www.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文章连载数据库操作
 *
 * @author Godbobo
 * @date 2019/06/02
 */
@Mapper
@Component
public interface BlogSeriesMapper {
    /**
     * 根据主键删除
     *
     * @param id 连载主键
     * @return 影响行数
     */
    int deleteByPrimaryKey(@Param("id") Long id);

    /**
     * 根据主键列表批量删除
     *
     * @param list 主键列表
     * @return 影响行数
     */
    int batchDeleteByIds(List<String> list);

    /**
     * 插入记录
     *
     * @param record 连载信息
     * @return 影响行数
     */
    int insert(BlogSeries record);

    /**
     * 根据主键查找
     *
     * @param id 主键
     * @return 连载实体
     */
    BlogSeries selectByPrimaryKey(@Param("id") Long id);

    /**
     * 根据主键更新
     *
     * @param record 连载信息
     * @return 影响行数
     */
    int updateByPrimaryKey(BlogSeries record);

    /**
     * 查询全部记录
     *
     * @return 连载列表
     */
    List<BlogSeries> selectAll();
}