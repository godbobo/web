package com.aqzscn.www.blog.service.impl;

import com.aqzscn.www.blog.mapper.BlogTag;
import com.aqzscn.www.blog.mapper.BlogTagMapper;
import com.aqzscn.www.blog.service.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Godbobo on 2019/5/25.
 */
@Service
@Transactional
public class BlogTagServiceImpl implements BlogTagService {

    private final BlogTagMapper tagMapper;

    @Autowired
    public BlogTagServiceImpl(BlogTagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public boolean insert(BlogTag tag) throws RuntimeException {
        return this.tagMapper.insert(tag) > 0;
    }

    @Override
    public boolean deleteById(Long id) throws RuntimeException {
        return this.tagMapper.deleteById(id) > 0;
    }

    @Override
    public List<BlogTag> selectAll() throws RuntimeException {
        return this.tagMapper.selectAll();
    }
}
