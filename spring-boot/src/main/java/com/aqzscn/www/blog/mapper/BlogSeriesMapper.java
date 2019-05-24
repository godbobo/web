package com.aqzscn.www.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BlogSeriesMapper {
    // 根据主键删除
    @Delete("delete from blog_series where id = #{id}")
    int deleteByPrimaryKey(@Param("id") Long id);

    // 插入记录
    @Insert("insert into blog_series (id, name, auto_index, abstract, img, ignore_order) values (#{id}, #{name}, #{autoIndex}, #{abstract}, #{img}, #{ignoreOrder})")
    int insert(BlogSeries record);

    // 根据主键查找
    @Select("select * from blog_series where id = #{id}")
    BlogSeries selectByPrimaryKey(@Param("id") Long id);

    // 根据主键更新
    @Update("update blog_series set name = #{name} auto_index = #{autoIndex}, abstract = #{abstract}, img = #{img}, ignore_order = #{ignoreOrder} where id = #{id}")
    int updateByPrimaryKey(BlogSeries record);
}