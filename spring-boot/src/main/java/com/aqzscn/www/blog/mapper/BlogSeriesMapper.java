package com.aqzscn.www.blog.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 文章连载数据库操作
 *
 * @author Godbobo
 * @date 2019/06/02
 */
@Component
public interface BlogSeriesMapper extends Mapper<BlogSeries> {

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
     * 查询全部记录
     *
     * @return 连载列表
     */
    List<BlogSeries> selectAll();
}