package com.aqzscn.www.global.config.handler;

import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一处理异常
 * @author Godbobo
 * @date 2019/5/8.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 捕获自定义的异常
    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public ReturnVo exceptionHandler(HttpServletRequest request, HttpServletResponse response, AppException e) {
        logger.error(e.getMessage());
        ReturnVo vo;
        if (e.getError() != ReturnError.FAILED) {
            // 已经定义的异常
            // 目前只会发生参数校验异常，其他异常等配置过SpringSecurity后再说
            vo = ReturnVo.fail(e.getError(), e.getResults());
        } else {
            // 未定义的异常
            vo = ReturnVo.fail(request.getMethod());
        }
        // 设置不同状态时的响应状态码
        response.setStatus(vo.getErrors().get(0).getCode().intValue());
        return vo;
    }

    // 捕获全局异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnVo exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(e.getMessage());
        ReturnVo vo =  ReturnVo.fail(request.getMethod());
        // 设置不同状态时的响应状态码
        response.setStatus(vo.getErrors().get(0).getCode().intValue());
        return vo;
    }

}
