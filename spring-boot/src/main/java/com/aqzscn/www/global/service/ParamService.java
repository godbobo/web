package com.aqzscn.www.global.service;

import com.aqzscn.www.global.mapper.Param;

import java.util.List;

/**
 * 系统参数服务
 *
 * @author Godbobo
 * @date 2019/6/1
 */
public interface ParamService {

    /**
     * 获取全部参数
     *
     * @return 参数列表
     * @throws RuntimeException 运行时异常
     */
    List<Param> getAll() throws RuntimeException;

}
