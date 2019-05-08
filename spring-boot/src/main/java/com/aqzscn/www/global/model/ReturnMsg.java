package com.aqzscn.www.global.model;

/**
 * 错误类型及说明
 * 这个枚举类的说明信息在非GET请求时要提示用户，即设置到ReturnVo的msg属性上
 * Created by Godbobo on 2019/5/4.
 */
public enum  ReturnMsg implements IErrorCode {

    SUCCESS(200, "操作成功"),
    POST_FAIL(500, "添加失败"),
    POST_SUCCESS(200, "添加成功"),
    PUT_FAIL(500, "修改失败"),
    PUT_SUCCESS(200, "修改成功"),
    DELETE_FAIL(500, "删除失败"),
    DELETE_SUCCESS(200, "删除成功");

    private Long code;
    private String title;

    ReturnMsg(long code, String title) {
        this.code = code;
        this.title = title;
    }

    public Long getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }
}
