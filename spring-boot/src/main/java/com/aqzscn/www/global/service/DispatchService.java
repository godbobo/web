package com.aqzscn.www.global.service;

import com.aqzscn.www.global.mapper.Dispatch;
import com.github.pagehelper.Page;

/**
 * 转发服务
 */
public interface DispatchService {

    Page<Dispatch> selectDispatch(Integer page, Integer rows);

}
