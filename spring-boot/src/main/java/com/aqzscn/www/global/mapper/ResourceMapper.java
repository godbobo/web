package com.aqzscn.www.global.mapper;

import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.Table;

/**
 * 文件资源数据操作
 *
 * @author Godbobo
 * @date 2019/6/5
 */
@Component
@Table(name = "g_resource")
public interface ResourceMapper extends Mapper<Resource> {

    /**
     * 插入资源
     *
     * @param record 资源信息
     * @return 影响行数
     */
    int insert(Resource record);

    /**
     * 根据主键更新资源
     *
     * @param record 资源信息
     * @return 影响行数
     */
    int updateByPrimaryKey(Resource record);
}