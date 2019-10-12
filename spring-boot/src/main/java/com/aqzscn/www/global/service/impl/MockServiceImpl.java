package com.aqzscn.www.global.service.impl;

import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.MyPage;
import com.aqzscn.www.global.domain.vo.MockRequest;
import com.aqzscn.www.global.mapper.Mock;
import com.aqzscn.www.global.mapper.MockMapper;
import com.aqzscn.www.global.service.MockService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MockServiceImpl implements MockService {

    private final MockMapper mockMapper;


    public MockServiceImpl(MockMapper mockMapper) {
        this.mockMapper = mockMapper;
    }

    @Override
    public MyPage selectMock(MockRequest mockRequest, Integer page, Integer rows) {
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
            Page<MockRequest> pi = (Page<MockRequest>) this.mockMapper.selectMocks(mockRequest);
            return new MyPage(pi.toPageInfo());
        } else {
            MyPage p = new MyPage();
            List<MockRequest> list = this.mockMapper.selectMocks(mockRequest);
            p.setLst(list);
            return p;
        }
    }

    @Override
    public Boolean insertMock(Mock mock) {
        // 插入之前需要判断是否已有同一路径的模拟数据
        Mock cpMock = new Mock();
        cpMock.setMethod(mock.getMethod());
        cpMock.setPath(mock.getPath());
        cpMock.setDispatchId(mock.getDispatchId());
        Mock resMock = this.mockMapper.selectOne(cpMock);
        if (resMock != null && resMock.getId() != null) {
            throw AppException.of("已存在相同模拟数据，请勿重复添加");
        }
        return this.mockMapper.insert(mock) > 0;
    }

    @Override
    public Boolean updateMock(Mock mock) {
        return this.mockMapper.updateByPrimaryKey(mock) > 0;
    }

    @Override
    public Boolean deleteMock(Mock mock) {
        return this.mockMapper.delete(mock) > 0;
    }

    @Override
    public Mock selectOneMock(Mock mock) {
        if (mock == null) {
            return null;
        }
        return this.mockMapper.selectOne(mock);
    }
}
