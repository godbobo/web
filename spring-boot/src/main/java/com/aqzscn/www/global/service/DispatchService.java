package com.aqzscn.www.global.service;

import com.aqzscn.www.global.domain.dto.MyPage;
import com.aqzscn.www.global.mapper.Dispatch;

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

    /**
     * 新增转发服务
     * @param dispatch 转发服务对象
     * @return Boolean 是否成功
     */
    Boolean insertDispatch(Dispatch dispatch);

    /**
     * 更新转发服务
     * @param dispatch 转发服务对象
     * @return Boolean 是否成功
     */
    Boolean updateDispatch(Dispatch dispatch);

    /**
     * 删除转发服务
     * @param dispatch 转发服务对象
     * @return Boolean 是否成功
     */
    Boolean deleteDispatch(Dispatch dispatch);

}
