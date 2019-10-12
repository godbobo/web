package com.aqzscn.www.global.service;

import com.aqzscn.www.global.domain.dto.MyPage;
import com.aqzscn.www.global.domain.vo.MockRequest;
import com.aqzscn.www.global.mapper.Mock;

/**
 * 模拟数据服务
 */
public interface MockService {

    /**
     * 获取模拟数据列表
     */
    MyPage selectMock(MockRequest mockRequest, Integer page, Integer rows);

    /**
     *  新增模拟数据
     */
    Boolean insertMock(Mock mock);

    /**
     * 修改模拟数据
     * @return Boolean 是否成功
     */
    Boolean updateMock(Mock mock);

    /**
     * 删除模拟数据
     * @return Boolean 是否成功
     */
    Boolean deleteMock(Mock mock);

    /**
     * 获取一条模拟数据
     */
    Mock selectOneMock(Mock mock);

}
