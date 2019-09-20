package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.domain.dto.ReturnVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Godbobo on 2019/5/25.
 */
public class BaseController {

    protected final HttpServletRequest request;
    private final HttpServletResponse response;

    public BaseController(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    protected ReturnVo response(boolean b) {
        if (b) {
            return ReturnVo.ok(this.request.getMethod());
        } else {
            ReturnVo vo = ReturnVo.fail(this.request.getMethod());
            this.response.setStatus(vo.getErrors().get(0).getCode().intValue());
            return vo;
        }
    }

}
