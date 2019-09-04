package com.aqzscn.www.global.service;

import com.aqzscn.www.global.domain.dto.MyPage;

/**
 * 转发服务
 */
public interface DispatchService {

    /**
     * 获取转发列表（支持分页）
     * @param page 页码
     * @param rows 行数
     * @return MyPage
     */
    MyPage selectDispatch(Integer page, Integer rows);

}
