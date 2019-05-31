package com.aqzscn.www.global.config.aspect;

import com.aqzscn.www.global.util.JacksonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 记录请求参数及响应体信息
 *
 * @author Godbobo
 * @date 2019/5/31
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private final JacksonUtil jacksonUtil = new JacksonUtil();

    @Pointcut("execution(public * com.aqzscn.www.global.controller..*.*(..)) || execution(public * com.aqzscn.www.blog.controller..*.*(..))")
    public void log() {}

    @Around(value = "log()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String method = request.getMethod();
            String ip = request.getRemoteHost();
            String url = request.getRequestURL().toString();
            logger.info("{} {} {} 参数: {}", ip, method, url, Arrays.toString(proceedingJoinPoint.getArgs()));
            long startTime = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long endTime = System.currentTimeMillis();
            String resultData;
            if (result instanceof String) {
                resultData = result.toString();
            }else {
                resultData = jacksonUtil.toJson(result);
            }
            int status = attributes.getResponse().getStatus();
            logger.info("耗时 {}ms STATUS: {} 返回信息: {}",endTime - startTime,status, resultData);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return result;
    }

}
