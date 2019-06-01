package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.blog.domain.po.ArticleRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 博文数据库操作类
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Mapper
@Component
public interface BlogArticleMapper {

    /**
     * 根据主键删除文章
     *
     * @param id 文章主键
     * @return 影响行数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键列表批量删除文章
     *
     * @param list 包含主键的列表
     * @return 影响行数
     */
    int batchDeleteById(List<String> list);

    /**
     * 插入文章
     *
     * @param record 文章实体
     * @return 影响行数
     */
    int insert(ArticleRequest record);

    /**
     * 根据主键查询文章
     *
     * @param id 文章主键
     * @return 文章实体
     */
    BlogArticle selectByPrimaryKey(Long id);

    BlogArticle selectByTimeAndTitle(BlogArticle record);

    /**
     * 根据条件查询文章
     * @param articleRequest 文章实体类
     * @return 博文列表
     */
    Page<BlogArticle> select(ArticleRequest articleRequest);


    int updateByPrimaryKeyWithBLOBs(BlogArticle record);

    /**
     * 与标签关联的操作
     */
    int insertTag(Long articleId, Long tagId);

    /**
     * 与连载关联的操作
     */
    BlogArticle selectLastInSeries(Long seriesId);
}