package com.aqzscn.www.blog.service;

import com.aqzscn.www.blog.mapper.BlogArticle;

/**
 * Created by Godbobo on 2019/5/24.
 */
public interface BlogArticleService {

    boolean addArticles(BlogArticle article) throws RuntimeException;

}
