package com.aqzscn.www.global.mapper;

import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.Table;
import java.util.List;

/**
 * 系统参数表操作类
 *
 * @author Godbobo
 * @date 2019/6/1
 */
@Component
public interface ParamMapper extends Mapper<Param> {

    /**
     * 查询全部参数
     *
     * @return 参数列表
     */
    List<Param> selectAll();

}
