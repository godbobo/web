package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.blog.domain.vo.ArticleRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BlogArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogArticle record);

    int insertSelective(BlogArticle record);

    BlogArticle selectByPrimaryKey(Long id);

    Page<BlogArticle> select(ArticleRequest articleRequest);

    int updateByPrimaryKeySelective(BlogArticle record);

    int updateByPrimaryKeyWithBLOBs(BlogArticle record);

    int updateByPrimaryKey(BlogArticle record);
}