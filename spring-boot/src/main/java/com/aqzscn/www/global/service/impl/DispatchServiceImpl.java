package com.aqzscn.www.global.service.impl;

import com.aqzscn.www.global.mapper.Dispatch;
import com.aqzscn.www.global.mapper.DispatchMapper;
import com.aqzscn.www.global.service.DispatchService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DispatchServiceImpl implements DispatchService {

    private final DispatchMapper dispatchMapper;

    public DispatchServiceImpl(DispatchMapper dispatchMapper) {
        this.dispatchMapper = dispatchMapper;
    }

    @Override
    public Page<Dispatch> selectDispatch(Integer page, Integer rows) {
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        return (Page<Dispatch>) dispatchMapper.selectDispatch();

    }
}
