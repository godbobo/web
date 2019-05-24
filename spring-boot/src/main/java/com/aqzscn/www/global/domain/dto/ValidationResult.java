package com.aqzscn.www.global.domain.dto;

/**
 * Created by Godbobo on 2019/5/10.
 */
public class ValidationResult implements IErrorCode {

    private Long code = 400L;
    private String title;

    ValidationResult(String title){
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
