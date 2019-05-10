package com.aqzscn.www.global.model.co;

import com.aqzscn.www.global.model.vo.ReturnError;
import lombok.Getter;
import lombok.NonNull;

/**
 * Created by Godbobo on 2019/5/10.
 */
@Getter
public class AppException extends RuntimeException {

    private ReturnError error;

    private AppException(String message) {
        super(message);
        this.error = ReturnError.FAILED;
    }
    private AppException(ReturnError error) {
        super(error.getTitle());
        this.error = error;
    }

    private AppException(ReturnError error, String message) {
        super(message);
        this.error = error;
    }

    // 针对自定义的错误消息
    public static AppException of(@NonNull String message) {
        return new AppException(message);
    }

    // 针对系统中已有的错误类型
    public static AppException of(ReturnError error){
        return new AppException(error);
    }

    // 错误类型已存在，但需要自定义错误说明
    public static AppException of(ReturnError error, @NonNull String message) {
        return new AppException(error, message);
    }

}
