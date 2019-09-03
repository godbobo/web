package com.aqzscn.www.blog.service.impl;

import com.aqzscn.www.blog.domain.po.ArticleRequest;
import com.aqzscn.www.blog.mapper.*;
import com.aqzscn.www.blog.service.BlogArticleService;
import com.aqzscn.www.global.component.FileService;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.MyPage;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 博文服务
 *
 * @author Godbobo
 * @date 2019/5/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogArticleServiceImpl implements BlogArticleService {

    private final BlogArticleMapper articleMapper;
    private final BlogSeriesMapper seriesMapper;
    private final FileService fileService;


    @Autowired
    public BlogArticleServiceImpl(BlogArticleMapper articleMapper, BlogSeriesMapper seriesMapper, FileService fileService) {
        this.articleMapper = articleMapper;
        this.seriesMapper = seriesMapper;
        this.fileService = fileService;
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
                for (BlogTag tag : articleRequest.getTagList()) {
                    if (this.articleMapper.insertTag(articleRequest.getId(), tag.getId()) == 0) {
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
    public List<BlogArticle> selectAll(ArticleRequest articleRequest) throws RuntimeException {
        return articleMapper.select(articleRequest);
    }

    @Override
    public List<BlogArticle> selectNoSeries() throws RuntimeException {
        return articleMapper.selectNoSeries();
    }

    @Override
    public void exportFile(String idStr) throws RuntimeException {
        ArticleRequest articleRequest = new ArticleRequest();
        // 设置非基本信息，否则无法输出content
        articleRequest.setBase(false);
        articleRequest.setIdList(Arrays.asList(idStr.split(",")));
        List<BlogArticle> articleList = articleMapper.select(articleRequest);
        if (articleList != null && articleList.size() > 0) {
            List<String> contentList = new ArrayList<>();
            List<String> nameList = new ArrayList<>();
            for(BlogArticle ba: articleList) {
                contentList.add(ba.getContent());
                nameList.add(ba.getTitle());
            }
            fileService.downloadPlains(contentList, nameList, ".md");
        }
    }

    @Override
    public boolean updateArticleSeriesMap(Long aId, Long sId) throws RuntimeException {
        BlogSeries series = seriesMapper.selectByPrimaryKey(sId);
        if (series==null) {
            throw AppException.of(ReturnError.VALIDATE_FAILED, "连载信息不存在");
        }
        // 自动设置在连载中的顺序
        BlogArticle article = this.articleMapper.selectLastInSeries(sId);
        if (article!=null) {
            articleMapper.updateSeriesMapById(aId, sId, article.getSeriesOrder() + 1);
        }else {
            // 如果没有找到，则设置顺序为0
            articleMapper.updateSeriesMapById(aId, sId, 0);
        }
        return true;
    }

    @Override
    public BlogArticle selectById(Long id) throws RuntimeException {
        return this.articleMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean batchDeleteById(String idStr) throws RuntimeException {
        List<String> idList = Arrays.asList(idStr.split(","));
        return this.articleMapper.batchDeleteById(idList) > 0;
    }
}
