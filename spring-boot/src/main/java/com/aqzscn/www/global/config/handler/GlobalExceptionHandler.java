package com.aqzscn.www.global.config.handler;

import com.aqzscn.www.global.model.ReturnVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一处理异常
 * Created by Godbobo on 2019/5/8.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnVo ExceptionHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReturnVo vo = ReturnVo.fail(request.getMethod());
        response.setStatus(vo.getErrors().get(0).getCode().intValue()); // 设置不同状态时的响应状态码
        return vo;
    }

}
