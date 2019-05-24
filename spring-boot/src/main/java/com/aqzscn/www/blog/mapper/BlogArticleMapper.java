package com.aqzscn.www.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BlogArticleMapper {
    // 根据主键删除
    @Delete("delete from blog_article where id = #{id}")
    int deleteByPrimaryKey(@Param("id") Long id);

    // 插入记录
    @Insert("insert into blog_article (id, title, abstract_txt, abstract_img, update_time, view_num, series, series_order, content) values (#{id}, #{title}, #{abstractTxt}, #{abstractImg}, #{updateTime}, #{viewNum}, #{series}, #{seriesOrder}, #{content})")
    int insert(BlogArticle record);

    // 根据主键查找
    @Select("select * from blog_article where id = #{id}")
    BlogArticle selectByPrimaryKey(@Param("id") Long id);

    @Update("update blog_article set title = #{title}, abstract_txt = #{abstractTxt}, abstract_img = #{abstractImg}, update_time = #{updateTime}, view_num = #{viewNum}, series = #{series}, series_order = #{seriesOrder} where id = #{id}")
    int updateByPrimaryKey(BlogArticle record);
}