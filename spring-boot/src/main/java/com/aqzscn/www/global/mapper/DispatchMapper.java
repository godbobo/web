package com.aqzscn.www.global.mapper;

import com.aqzscn.www.global.domain.vo.DispatchRequest;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * 转发列表
 */
@Component
public interface DispatchMapper extends Mapper<Dispatch> {

    /**
     * 修改转发服务
     * @param dispatchRequest 转发服务查询对象
     * @return 影响行数
     */
    Integer updateByDispatch(DispatchRequest dispatchRequest);

}
