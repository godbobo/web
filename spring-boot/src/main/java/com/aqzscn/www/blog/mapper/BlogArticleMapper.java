package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.blog.domain.vo.ArticleRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BlogArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleRequest record);

    int insertSelective(BlogArticle record);

    BlogArticle selectByPrimaryKey(Long id);

    BlogArticle selectByTimeAndTitle(BlogArticle record);

    Page<BlogArticle> select(ArticleRequest articleRequest);

    int updateByPrimaryKeySelective(BlogArticle record);

    int updateByPrimaryKeyWithBLOBs(BlogArticle record);

    int updateByPrimaryKey(BlogArticle record);

    /**
     * 与标签关联的操作
     */
    int insertTag(Long articleId, Long tagId);

    /**
     * 与连载关联的操作
     */
    BlogArticle selectLastInSeries(Long seriesId);
}