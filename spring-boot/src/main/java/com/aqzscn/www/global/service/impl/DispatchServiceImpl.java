package com.aqzscn.www.global.service.impl;

import com.aqzscn.www.global.domain.dto.MyPage;
import com.aqzscn.www.global.mapper.Dispatch;
import com.aqzscn.www.global.mapper.DispatchMapper;
import com.aqzscn.www.global.service.DispatchService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DispatchServiceImpl implements DispatchService {

    private final DispatchMapper dispatchMapper;

    public DispatchServiceImpl(DispatchMapper dispatchMapper) {
        this.dispatchMapper = dispatchMapper;
    }

    @Override
    public MyPage selectDispatch(Integer page, Integer rows) {
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
            Page<Dispatch> pi = (Page<Dispatch>) dispatchMapper.selectAll();
            return new MyPage(pi.toPageInfo());
        } else {
            MyPage p = new MyPage();
            p.setLst(dispatchMapper.selectAll());
            return p;
        }
    }

    @Override
    public Boolean insertDispatch(Dispatch dispatch) {
        // 避免主键冲突
        dispatch.setId(null);
        return this.dispatchMapper.insert(dispatch) > 0;
    }

    @Override
    public Boolean updateDispatch(Dispatch dispatch) {
        return this.dispatchMapper.updateByPrimaryKey(dispatch) > 0;
    }

    @Override
    public Boolean deleteDispatch(Dispatch dispatch) {
        return this.dispatchMapper.delete(dispatch) > 0;
    }


}
