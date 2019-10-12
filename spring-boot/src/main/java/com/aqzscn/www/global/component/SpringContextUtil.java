package com.aqzscn.www.global.component;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    // 获取当前环境参数  exp: dev,prod,test
    public static String getActiveProfile() {
        String[] profiles = context.getEnvironment().getActiveProfiles();
        if( ! ArrayUtils.isEmpty(profiles)){
            return profiles[0];
        }
        return "";
    }

    // 获取配置文件中的值
    public static <T> T getProperty(String pathName, Class<T> clazz) {
        return getApplicationContext().getEnvironment().getProperty(pathName, clazz);
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
