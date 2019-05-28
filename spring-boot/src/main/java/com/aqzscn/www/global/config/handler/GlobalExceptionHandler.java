package com.aqzscn.www.global.config.handler;

import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.IErrorCode;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.domain.dto.ValidationResult;
import org.apache.commons.lang3.StringUtils;
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
        ReturnVo vo;
        if (e.getError() != ReturnError.FAILED) {
            // 已经定义的异常
            vo = ReturnVo.fail(e.getError(), e.getResults());
        } else {
            // 未定义的异常
            vo = ReturnVo.fail(request.getMethod());
        }
        StringBuilder eStr = new StringBuilder("发生异常：");
        for(IErrorCode ie: vo.getErrors()){
            eStr.append(ie.getTitle()).append(",");
        }
        logger.error(eStr.substring(0, eStr.length() - 1));
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
        vo.getErrors().add(new ValidationResult(500L, e.getMessage()));
        // 设置不同状态时的响应状态码
        response.setStatus(vo.getErrors().get(0).getCode().intValue());
        return vo;
    }

}
