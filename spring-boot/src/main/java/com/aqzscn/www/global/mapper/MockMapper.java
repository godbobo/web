package com.aqzscn.www.global.mapper;

import com.aqzscn.www.global.domain.vo.MockRequest;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
public interface MockMapper extends Mapper<Mock> {

    List<MockRequest> selectMocks(MockRequest mockRequest);

}
