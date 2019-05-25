package com.aqzscn.www.blog.service;

import com.aqzscn.www.blog.mapper.BlogTag;

import java.util.List;

/**
 * 标签服务
 * Created by Godbobo on 2019/5/25.
 */
public interface BlogTagService {

    boolean insert(BlogTag tag) throws RuntimeException; // 插入标签

    boolean deleteById(Long id) throws RuntimeException; // 删除标签

    List<BlogTag> selectAll() throws RuntimeException; // 获取全部标签

}
