package com.aqzscn.www.blog.service;

import com.aqzscn.www.blog.domain.po.ArticleRequest;
import com.aqzscn.www.blog.mapper.BlogArticle;
import com.aqzscn.www.global.domain.dto.MyPage;

import java.util.List;

/**
 * 文章服务
 *
 * @author Godbobo
 * @date 2019/5/24.
 */
public interface BlogArticleService {

    /**
     * 新增文章
     *
     * @param articleRequest 文章请求参数列表
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean addArticles(ArticleRequest articleRequest) throws RuntimeException;

    /**
     * 根据主键删除文章
     *
     * @param id 文章主键
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean deleteById(Long id) throws RuntimeException;

    /**
     * 根据条件查询文章
     *
     * @param articleRequest 查询条件及分页对象
     * @return 分页结果
     * @throws RuntimeException 运行时异常
     */
    MyPage select(ArticleRequest articleRequest) throws RuntimeException;

    /**
     * 根据条件查询全部文章
     *
     * @param articleRequest 查询条件
     * @return 文章列表
     * @throws RuntimeException 运行时异常
     */
    List<BlogArticle> selectAll(ArticleRequest articleRequest) throws RuntimeException;

    /**
     * 查询没有在连载中的文章
     *
     * @return 文章列表
     * @throws RuntimeException 运行时异常
     */
    List<BlogArticle> selectNoSeries() throws RuntimeException;

    /**
     * 导出文章
     *
     * @param idStr ,分割的主键列表
     * @throws RuntimeException 运行时异常
     */
    void exportFile(String idStr) throws RuntimeException;

    /**
     * 更新文章和连载之间的关系
     *
     * @param aId 文章id
     * @param sId 连载id
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean updateArticleSeriesMap(Long aId, Long sId) throws RuntimeException;

    /**
     * 根据主键查询文章
     *
     * @param id 文章主键
     * @return 文章实体
     * @throws RuntimeException 运行时异常
     */
    BlogArticle selectById(Long id) throws RuntimeException;

    /**
     * 根据主键列表批量删除文章
     *
     * @param idStr ,分割的主键列表
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean batchDeleteById(String idStr) throws RuntimeException;

}
