package com.aqzscn.www.global.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 文件资源数据操作
 *
 * @author Godbobo
 * @date 2019/6/5
 */
@Component
@Mapper
public interface ResourceMapper {

    /**
     * 根据主键删除资源
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入资源
     *
     * @param record 资源信息
     * @return 影响行数
     */
    int insert(Resource record);

    /**
     * 根据主键获取资源信息
     *
     * @param id 主键
     * @return 资源信息
     */
    Resource selectByPrimaryKey(Long id);

    /**
     * 根据主键更新资源
     *
     * @param record 资源信息
     * @return 影响行数
     */
    int updateByPrimaryKey(Resource record);
}