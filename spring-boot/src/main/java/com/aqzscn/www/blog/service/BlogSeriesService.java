package com.aqzscn.www.blog.service;

import com.aqzscn.www.blog.mapper.BlogSeries;

import java.util.List;

/**
 * 连载服务
 *
 * @author Godbobo
 * @date 2019/5/25.
 */
public interface BlogSeriesService {

    /**
     * 查询全部连载
     *
     * @return 连载列表
     * @throws RuntimeException 运行时异常
     */
    List<BlogSeries> selectAll() throws RuntimeException;

    /**
     * 新增连载
     *
     * @param series 连载信息
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean insert(BlogSeries series) throws RuntimeException;

    /**
     * 根据主键删除连载
     *
     * @param id 连载主键
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean deleteById(Long id) throws RuntimeException;

    /**
     * 根据主键列表删除连载
     *
     * @param idStr 连载主键列表
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean batchDeleteByIds(String idStr) throws RuntimeException;

    /**
     * 根据主键更新连载信息
     *
     * @param series 连载信息
     * @return 是否成功
     * @throws RuntimeException 运行时异常
     */
    boolean updateById(BlogSeries series) throws RuntimeException;

}
