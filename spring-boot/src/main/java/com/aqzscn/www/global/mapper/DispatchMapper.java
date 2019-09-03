package com.aqzscn.www.global.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 转发列表
 */
@Mapper
@Component
public interface DispatchMapper {

    List<Dispatch> selectDispatch();

}
