package com.aqzscn.www.blog.service.imp;

import com.aqzscn.www.blog.mapper.BlogArticle;
import com.aqzscn.www.blog.mapper.BlogArticleMapper;
import com.aqzscn.www.blog.service.BlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 博文服务
 * Created by Godbobo on 2019/5/24.
 */
@Service
@Transactional
public class BlogArticleServiceImp implements BlogArticleService {

    private final BlogArticleMapper articleMapper;

    @Autowired
    public BlogArticleServiceImp(BlogArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public boolean addArticles(BlogArticle article) throws RuntimeException {
        article.setUpdateTime(new Date());
        return this.articleMapper.insert(article) > 0;
    }
}
