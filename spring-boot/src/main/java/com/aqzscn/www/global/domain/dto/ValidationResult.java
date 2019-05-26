package com.aqzscn.www.global.domain.dto;

/**
 * 参数校验失败结果实例
 *
 * @author Godbobo
 * @date 2019/5/10.
 */
public class ValidationResult implements IErrorCode {

    private String title;

    private Long code = 400L;

    public ValidationResult(String title){
        this.title = title;
    }

    public ValidationResult(Long code, String title){
        this.code = code;
        this.title = title;
    }

    @Override
    public Long getCode() {
        return code;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
