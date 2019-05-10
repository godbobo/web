package com.aqzscn.www.global.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 错误类型及说明
 * 这个枚举类的说明信息无论什么类型的请求均要提示用户，即设置到ReturnVo的msg属性上
 * Created by Godbobo on 2019/5/4.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReturnError implements IErrorCode {

    FAILED(500, "操作失败"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private Long code;
    private String title;

    ReturnError(long code, String title) {
        this.code = code;
        this.title = title;
    }

    @Override
    public Long getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }}
