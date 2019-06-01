package com.aqzscn.www.global.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统参数表操作类
 *
 * @author Godbobo
 * @date 2019/6/1
 */
@Mapper
@Component
public interface ParamMapper  {

    /**
     * 查询全部参数
     *
     * @return 参数列表
     */
    List<Param> selectAll();

}
