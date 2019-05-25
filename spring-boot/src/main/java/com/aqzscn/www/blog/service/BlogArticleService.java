package com.aqzscn.www.blog.service;

import com.aqzscn.www.blog.domain.vo.ArticleRequest;
import com.aqzscn.www.blog.mapper.BlogArticle;
import com.aqzscn.www.global.domain.dto.MyPage;
import com.aqzscn.www.global.domain.dto.PageRequest;

/**
 * Created by Godbobo on 2019/5/24.
 */
public interface BlogArticleService {

    boolean addArticles(BlogArticle article) throws RuntimeException;

    boolean deleteById(Long id) throws RuntimeException;

    MyPage select(ArticleRequest articleRequest) throws RuntimeException;

    BlogArticle selectById(Long id) throws RuntimeException;

}
