package com.aqzscn.www.global.model;

/**
 * 错误类型及说明
 * 这个枚举类的说明信息无论什么类型的请求均要提示用户，即设置到ReturnVo的msg属性上
 * Created by Godbobo on 2019/5/4.
 */
public enum ReturnError implements IErrorCode {

        FAILED(500, "操作失败"),
        VALIDATE_FAILED(404, "参数检验失败"),
        UNAUTHORIZED(401, "暂未登录或token已经过期"),
        FORBIDDEN(403, "没有相关权限");
        private Long code;
        private String title;

        ReturnError(long code, String title) {
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
