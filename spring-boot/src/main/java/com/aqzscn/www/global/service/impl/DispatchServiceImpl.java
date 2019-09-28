package com.aqzscn.www.global.service.impl;

import com.aqzscn.www.global.domain.co.GlobalCaches;
import com.aqzscn.www.global.domain.dto.MyPage;
import com.aqzscn.www.global.domain.vo.DispatchRequest;
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
        dispatch.setEnable(0);
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

    @Override
    public Boolean switchDispatch(Dispatch dispatch) {
        // 首先判断是否有该服务
        Dispatch storeDispatch = this.dispatchMapper.selectByPrimaryKey(dispatch);
        if (storeDispatch != null) {
            // 关闭当前启用的转发服务并开启新的转发服务
            DispatchRequest dispatchRequest = new DispatchRequest();
            dispatchRequest.setQEnable(1);
            dispatchRequest.setEnable(0);
            // 当前可能没有启用的服务，此时影响行数为0，因此在操作这一步时不需要判断是否有影响行数
            this.dispatchMapper.updateByDispatch(dispatchRequest);
            dispatchRequest.setQEnable(null);
            dispatchRequest.setEnable(1);
            dispatchRequest.setId(dispatch.getId());
            if (this.dispatchMapper.updateByDispatch(dispatchRequest) > 0) {
                storeDispatch.setEnable(1);
                GlobalCaches.DISPATCH = storeDispatch;
                return true;
            }
        }
        return false;
    }


}
