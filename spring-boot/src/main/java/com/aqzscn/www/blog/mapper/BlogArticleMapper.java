package com.aqzscn.www.blog.mapper;

import com.aqzscn.www.blog.domain.po.ArticleRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 博文数据库操作类
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Mapper
@Component
public interface BlogArticleMapper {

    /**
     * 根据主键删除文章
     *
     * @param id 文章主键
     * @return 影响行数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键列表批量删除文章
     *
     * @param list 包含主键的列表
     * @return 影响行数
     */
    int batchDeleteById(List<String> list);

    /**
     * 插入文章
     *
     * @param record 文章实体
     * @return 影响行数
     */
    int insert(ArticleRequest record);

    /**
     * 根据主键查询文章
     *
     * @param id 文章主键
     * @return 文章实体
     */
    BlogArticle selectByPrimaryKey(Long id);

    /**
     * 根据条件查询文章
     * @param articleRequest 文章实体类
     * @return 博文列表
     */
    Page<BlogArticle> select(ArticleRequest articleRequest);

    /**
     * 查询不属于连载中的文章
     *
     * @return 文章列表
     */
    List<BlogArticle> selectNoSeries();


    /**
     * 根据主键更新文章
     *
     * @param record 文章信息
     * @return 影响行数
     */
    int updateByPrimaryKeyWithBLOBs(BlogArticle record);

    /**
     * 更新与连载间的关系
     *
     * @param aId 文章id
     * @param sId 连载id
     * @param aOrder 文章在连载中的顺序
     * @return 影响行数
     */
    int updateSeriesMapById(Long aId, Long sId, Integer aOrder);

    /**
     * 将文章和标签关联起来
     *
     * @param articleId 文章id
     * @param tagId 标签id
     * @return 影响行数
     */
    int insertTag(Long articleId, Long tagId);

    /**
     * 选择在连载中排序最后的文章
     *
     * @param seriesId 连载id
     * @return 文章
     */
    BlogArticle selectLastInSeries(Long seriesId);
}