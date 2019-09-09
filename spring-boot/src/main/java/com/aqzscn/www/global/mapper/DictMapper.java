package com.aqzscn.www.global.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/6 19:18
 */
@Component
@Mapper
public interface DictMapper {

    /**
     * 获取字典列表
     */
    List<Dict> selectDicts();

    /**
     * 获取字典类别
     */
    List<Dict> selectTypes();

}
