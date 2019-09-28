package com.aqzscn.www.global.config.aspect;

import com.aqzscn.www.global.util.JacksonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 记录请求参数及响应体信息
 *
 * @author Godbobo
 * @date 2019/5/31
 */
@Aspect
@Component
public class LogAspect {

//    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private final JacksonUtil jacksonUtil = new JacksonUtil();

    @Pointcut("execution(public * com.aqzscn.www.global.controller..*.*(..)) || execution(public * com.aqzscn.www.blog.controller..*.*(..)) || execution(public * com.aqzscn.www.weixin.controller..*.*(..))")
    public void log() {}

    @Around(value = "log()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String uuid = UUID.randomUUID().toString();
        // 获取切面类对应的Logger
        Logger logger = LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass());
        Object result = null;
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null && attributes.getRequest() != null) {
            request = attributes.getRequest();
            String method = request.getMethod();
            String ip = request.getRemoteHost();
            String url = request.getRequestURL().toString();
            List<String> params = new ArrayList<>();
            for (Object obj : proceedingJoinPoint.getArgs()) {
                if (obj instanceof BindingResult) {
                    // 不转换参数验证结果
                } else {
                    params.add(jacksonUtil.toJson(obj));
                }
            }
            logger.info("[{}] {} {} {} 参数: {}", uuid, ip, method, url,  params.toString());
        } else {
            logger.info("[{}] 未获取到请求信息", uuid);
        }

        long startTime = System.currentTimeMillis();

        result = proceedingJoinPoint.proceed();

        // 请求处理完毕，记录响应内容
        long endTime = System.currentTimeMillis();
        String resultData;
        if (result == null) {
            resultData = "";
        }else if (result instanceof String) {
            resultData = result.toString();
        }else {
            resultData = jacksonUtil.toJson(result);
        }
        int status = 500;
        if (attributes != null && attributes.getResponse() != null) {
            status = attributes.getResponse().getStatus();
        }
        logger.info("[{}] 耗时 {}ms STATUS: {} 返回信息: {}", uuid, endTime - startTime,status, resultData);
        return result;
    }

}
