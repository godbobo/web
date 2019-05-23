package com.aqzscn.www.global.model.co;

import com.aqzscn.www.global.model.vo.ReturnError;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by Godbobo on 2019/5/10.
 */
@Getter
public class AppException extends RuntimeException {

    private ReturnError error;

    private List<ObjectError> results;

    private AppException(String message) {
        super(message);
        this.error = ReturnError.FAILED;
    }
    private AppException(ReturnError error) {
        super(error.getTitle());
        this.error = error;
    }

    // 若传入results则一定为参数校验失败异常
    private AppException(List<ObjectError> results) {
        super(ReturnError.VALIDATE_FAILED.getTitle());
        this.error = ReturnError.VALIDATE_FAILED;
        this.results = results;
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

    // 参数校验异常
    public static AppException of(List<ObjectError> results) {
        return new AppException(results);
    }

    // 错误类型已存在，但需要自定义错误说明
    public static AppException of(ReturnError error, @NonNull String message) {
        return new AppException(error, message);
    }

}
