package com.aqzscn.www.global.util;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.text.SimpleDateFormat;

/**
 * Jackson工具类
 *
 * @author Godbobo
 * @date 2019/05/26
 */
public class JacksonUtil {

    // 时间戳转换时间格式设置
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ObjectMapper mapper;

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        mapper = new ObjectMapper();
        mapper.setDateFormat(dateFormat);
        // 允许对象忽略json中不存在的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许出现特殊字符和转义符
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 允许出现单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 忽视为空的属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static JacksonUtil me() {
        return new JacksonUtil();
    }

    /**
     * 过滤对象中的某些字段
     * @param filterName 过滤器名称
     * @param properties 要过滤掉的属性列表
     */
    public JacksonUtil filter(String filterName, String... properties) {
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter(filterName,
                SimpleBeanPropertyFilter.serializeAllExcept(properties));
        mapper.setFilterProvider(filterProvider);
        return this;
    }

    /**
     * 转换对象为json串
     * @param obj 要转换的对象
     * @return json
     */
    public String toJson(Object obj) {
        try {
            // 检查对象是否包含jsonfilter注解,如果有则使用过滤器转换
            if (obj != null) {
                Class clazz = obj.getClass();
                JsonFilter filter = (JsonFilter) clazz.getAnnotation(JsonFilter.class);
                if (filter != null) {
                    String filterName = filter.value();
                    filter(filterName, "");
                }
            }
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("转换json字符失败!");
        }
    }

    /**
     * 转换对象为json串
     * @param obj 要转换的对象
     * @param properties 要过滤的字段
     * @return json
     */
    public String toJson(Object obj, String... properties) {
        try {
            // 检查对象是否包含jsonfilter注解,如果有则使用过滤器转换
            Class clazz = obj.getClass();
            JsonFilter filter = (JsonFilter) clazz.getAnnotation(JsonFilter.class);
            if (filter != null) {
                String filterName = filter.value();
                filter(filterName, properties);
            }
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("转换json字符失败!");
        }
    }

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * (1)转换为普通JavaBean：readValue(json,Student.class)
     * (2)转换为List,如List<Student>,将第二个参数传递为Student
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     */
    public <T> T readValue(String jsonStr, Class<T> valueType) {
        try {
            return mapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json数组转List
     */
    public static <T> T readValueList(String jsonStr, TypeReference<T> valueTypeRef){
        try {
            return mapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}