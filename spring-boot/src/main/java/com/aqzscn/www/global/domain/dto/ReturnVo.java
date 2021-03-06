package com.aqzscn.www.global.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import static com.aqzscn.www.global.domain.dto.RequestMethod.*;


/**
 * json 格式数据载体
 * 前端在拦截响应时首先读取 HTTP 状态码
 * 如果大于等于200小于300即为请求成功，将data中的数据继续传递给下一级处理
 * 请求失败则将errors中的错误信息传递给下一级，由下一级决定要自动处理还是提示用户
 * 如果提示信息存在，则将提示内容显示给用户，否则不显示
 *
 * 若要保证SpringBoot默认转换的JSON不包含null值，需要在返回的类中一个个配置@JsonInclude(JsonInclude.Include.NON_NULL)
 * 尝试过注入HttpMessageConverter、application.yml配置等方法均不起作用，只好麻烦一点了，但毕竟能减少网络传输量，也算功德无量了
 *
 * @author Godbobo
 * @date 2019/5/4.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnVo {

    /* 使用 HTTP 状态码返回笼统的错误信息，在该列表中返回具体的错误信息
     * 比如登录时返回 http 400 bad request
     * 那么具体的错误信息就应该是是用户名密码格式不对或者匹配失败
     */
    @ApiModelProperty(value = "errors", name = "错误信息列表")
    private List<IErrorCode> errors;
    @ApiModelProperty(value = "data", name = "返回数据，与 errors 不共存")
    private Object data;
    // 一般情况下，GET请求不需要提示用户，POST/PUT/DELETE则需要提示用户
    @ApiModelProperty(value = "msg", name = "提示信息")
    private String msg;

    /*
     * 下面提供常用的json数据
     */
    public static ReturnVo ok(String method) {
        if (method.equals(GET)) {
            // 返回空的返回体，因为没有任何消息需要显示，且可以根据HTTP状态码判断是否成功
            return new ReturnVo();
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
    public static ReturnVo fail(ReturnError error,List<ObjectError> errors) {
        List<IErrorCode> list = new ArrayList<>();
        list.add(error);
        if (error.equals(ReturnError.VALIDATE_FAILED)){
            list.addAll(validationResults(errors));
        }
        return new ReturnVo(list, null, error.getTitle());
    }

    // 参数校验失败时封装错误信息列表
    private static List<IErrorCode> validationResults(List<ObjectError> errors) {
        List<IErrorCode> allResults = new ArrayList<>();
        if(errors!=null){
            for (ObjectError error: errors){
                allResults.add(new ValidationResult(error.getDefaultMessage()));
            }
        }
        return allResults;
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
