package com.aqzscn.www.blog.service.imp;

import com.aqzscn.www.blog.domain.vo.ArticleRequest;
import com.aqzscn.www.blog.mapper.BlogArticle;
import com.aqzscn.www.blog.mapper.BlogArticleMapper;
import com.aqzscn.www.blog.service.BlogArticleService;
import com.aqzscn.www.global.domain.dto.MyPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Override
    public boolean deleteById(Long id) throws RuntimeException {
        return this.articleMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public MyPage select(ArticleRequest articleRequest) throws RuntimeException {
        // 启动分页辅助类
        PageHelper.startPage(articleRequest.getPageRequest());
        PageInfo<BlogArticle> pageInfo = new PageInfo<>(this.articleMapper.select(articleRequest));
        return new MyPage(pageInfo);
    }

    @Override
    public BlogArticle selectById(Long id) throws RuntimeException {
        return this.articleMapper.selectByPrimaryKey(id);
    }
}
