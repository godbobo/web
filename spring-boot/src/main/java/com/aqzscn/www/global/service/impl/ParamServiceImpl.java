package com.aqzscn.www.global.service.impl;

import com.aqzscn.www.global.mapper.Param;
import com.aqzscn.www.global.mapper.ParamMapper;
import com.aqzscn.www.global.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Godbobo
 * @date 2019/6/1
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ParamServiceImpl implements ParamService {

    private final ParamMapper paramMapper;

    @Autowired
    public ParamServiceImpl(ParamMapper paramMapper) {
        this.paramMapper = paramMapper;
    }

    @Override
    public List<Param> getAll() throws RuntimeException {
        return this.paramMapper.selectAll();
    }
}
