package com.aqzscn.www.global.mapper;

import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.Table;
import java.util.List;

/**
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/6 19:18
 */
@Component
public interface DictMapper extends Mapper<Dict> {

    /**
     * 获取字典列表
     */
    List<Dict> selectDicts();

    /**
     * 获取字典类别
     */
    List<Dict> selectTypes();

}
