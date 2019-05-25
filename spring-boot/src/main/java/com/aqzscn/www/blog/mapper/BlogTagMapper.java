package com.aqzscn.www.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 标签数据库操作接口
 * Created by Godbobo on 2019/5/25.
 */
@Mapper
@Component
public interface BlogTagMapper {

    // 新增标签
    @Insert("insert into blog_tag (id, title, color, bg) values (#{id}, #{title}, #{color}, #{bg})")
    int insert(BlogTag record);

    // 删除标签
    @Delete("delete from blog_tag where id = #{id}")
    int deleteById(@Param("id") Long id);

    // 查询所有标签
    @Select("select * from blog_tag")
    List<BlogTag> selectAll();

}
