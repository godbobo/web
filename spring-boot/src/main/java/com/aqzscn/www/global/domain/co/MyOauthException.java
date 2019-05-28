package com.aqzscn.www.global.domain.co;

import com.aqzscn.www.global.config.security.MyOauthExceptionSerializer;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义Oauth2异常
 * 用于自定义异常返回信息
 *
 * @author Godbobo
 * @date 2019/5/28
 */
@Getter
@JsonSerialize(using = MyOauthExceptionSerializer.class)
public class MyOauthException extends OAuth2Exception {

    private ReturnError error;

    public MyOauthException(ReturnError error) {
        super(error.getTitle());
        this.error = error;
    }

    public MyOauthException(String msg, Throwable t) {
        super(msg, t);
    }

    public MyOauthException(String msg) {
        super(msg);
    }
}
