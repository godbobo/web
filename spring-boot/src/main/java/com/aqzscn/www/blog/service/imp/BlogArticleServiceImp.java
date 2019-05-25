package com.aqzscn.www.blog.service.imp;

import com.aqzscn.www.blog.domain.vo.ArticleRequest;
import com.aqzscn.www.blog.mapper.*;
import com.aqzscn.www.blog.service.BlogArticleService;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.MyPage;
import com.aqzscn.www.global.domain.dto.ReturnError;
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
    private final BlogSeriesMapper seriesMapper;


    @Autowired
    public BlogArticleServiceImp(BlogArticleMapper articleMapper, BlogSeriesMapper seriesMapper) {
        this.articleMapper = articleMapper;
        this.seriesMapper = seriesMapper;
    }

    @Override
    public boolean addArticles(ArticleRequest articleRequest) throws RuntimeException {
        // 验证是否是连载文章
        if (articleRequest.getSeries()!=null) {
            BlogSeries series = seriesMapper.selectByPrimaryKey(articleRequest.getSeries());
            if (series==null) {
                throw AppException.of(ReturnError.VALIDATE_FAILED, "连载信息不存在");
            }
            // 自动设置在连载中的顺序
            BlogArticle article = this.articleMapper.selectLastInSeries(articleRequest.getSeries());
            if (article!=null) {
                articleRequest.setSeriesOrder(article.getSeriesOrder() + 1);
            }else {
                articleRequest.setSeriesOrder(0);
            }
        }
        articleRequest.setUpdateTime(new Date());
        if (this.articleMapper.insert(articleRequest) > 0) {
            // 如果没有标签，结束流程，否则获取博文id并添加标签
            if (articleRequest.getTagList() != null && articleRequest.getTagList().size() > 0) {
                BlogArticle article = this.articleMapper.selectByTimeAndTitle(articleRequest);
                for (BlogTag tag : articleRequest.getTagList()) {
                    if (this.articleMapper.insertTag(article.getId(), tag.getId()) == 0) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
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
