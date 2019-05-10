package com.aqzscn.www.global.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.aqzscn.www.global.model.vo.RequestMethod.*;


/**
 * json 格式数据载体
 * 前端在拦截响应时首先读取 HTTP 状态码
 * 如果大于等于200小于300即为请求成功，将data中的数据继续传递给下一级处理
 * 请求失败则将errors中的错误信息传递给下一级，由下一级决定要自动处理还是提示用户
 * 如果提示信息存在，则将提示内容显示给用户，否则不显示
 * Created by Godbobo on 2019/5/4.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnVo {

    /* 使用 HTTP 状态码返回笼统的错误信息，在该列表中返回具体的错误信息
     * 比如登录时返回 http 400 bad request
     * 那么具体的错误信息就应该是是用户名密码格式不对或者匹配失败
     */
    private List<IErrorCode> errors; // 错误信息

    private Object data; // 返回数据，与 errors 不共存

    // 一般情况下，GET请求不需要提示用户，POST/PUT/DELETE则需要提示用户
    private String msg; // 提示信息

    /*
     * 下面提供常用的json数据
     */
    public static ReturnVo ok(String method) {
        if (method.equals(GET)) {
            return new ReturnVo(); // 返回空的返回体，因为没有任何消息需要显示，且可以根据HTTP状态码判断是否成功
        }else {
            return new ReturnVo(null, null, getSuccessRequestMsg(method));
        }
    }

    // 操作失败则不会有数据返回，且一定有提示信息
    public static ReturnVo fail(String method) {
        List<IErrorCode> list = new ArrayList<>();
        list.add(ReturnError.FAILED);
        return new ReturnVo(list, null, getErrorRequestMsg(method));
    }

    // 其他失败信息
    public static ReturnVo fail(ReturnError error) {
        List<IErrorCode> list = new ArrayList<>();
        list.add(error);
        return new ReturnVo(list, null, error.getTitle());
    }

    // 根据请求方法获取对应的成功提示消息
    private static String getSuccessRequestMsg(String method) {
        switch (method){
            case POST:return ReturnMsg.POST_SUCCESS.getTitle();
            case PUT:return ReturnMsg.PUT_SUCCESS.getTitle();
            case DELETE:return ReturnMsg.DELETE_SUCCESS.getTitle();
            default:return ReturnMsg.SUCCESS.getTitle();
        }
    }

    // 根据请求方法获取对应的失败提示消息
    private static String getErrorRequestMsg(String method) {
        switch (method){
            case POST:return ReturnMsg.POST_FAIL.getTitle();
            case PUT:return ReturnMsg.PUT_FAIL.getTitle();
            case DELETE:return ReturnMsg.DELETE_FAIL.getTitle();
            default:return ReturnError.FAILED.getTitle();
        }
    }

}
