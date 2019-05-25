package com.aqzscn.www.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BlogSeriesMapper {
    // 根据主键删除
    @Delete("delete from blog_series where id = #{id}")
    int deleteByPrimaryKey(@Param("id") Long id);

    // 插入记录
    @Insert("insert into blog_series (name, auto_index, abs, img, ignore_order) values (#{name}, #{autoIndex}, #{abs}, #{img}, #{ignoreOrder})")
    int insert(BlogSeries record);

    // 根据主键查找
    @Select("select * from blog_series where id = #{id}")
    BlogSeries selectByPrimaryKey(@Param("id") Long id);

    // 根据主键更新
    @Update("update blog_series set name = #{name} auto_index = #{autoIndex}, abs = #{abs}, img = #{img}, ignore_order = #{ignoreOrder} where id = #{id}")
    int updateByPrimaryKey(BlogSeries record);

    // 查询全部记录
    @Select("select * from blog_series")
    List<BlogSeries> selectAll();
}