package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.blog.domain.po.ArticleRequest;
import com.aqzscn.www.blog.domain.vo.ArticleVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 博文数据库操作类
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Mapper
@Component
public interface BlogArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleRequest record);

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