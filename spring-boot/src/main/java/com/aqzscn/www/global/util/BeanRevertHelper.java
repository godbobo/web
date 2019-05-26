package com.aqzscn.www.global.util;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * 在将对象转换为JSON数据时过滤某些属性
 * @author Godbobo
 * @date 2019/5/26
 */
public class BeanRevertHelper {

    /**
     * 将对象转换为json字符串
     * 并隐藏敏感信息
     * @param object 对象
     * @return json字符串
     */
    public static String hideUserInfo(Object object) {
        JacksonUtil jacksonUtil = new JacksonUtil();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("UserFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "realName", "username", "gender", "email", "headImg", "sign", "authorities"));
        return jacksonUtil.toJson(filterProvider, object);
    }

}
