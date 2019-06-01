package com.aqzscn.www.global.domain.co;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统缓存数据
 *
 * @author Godbobo
 * @date 2019/5/28
 */
public class GlobalCaches {

    // 用户角色列表
    public static Map<String, Long> ROLES = new ConcurrentHashMap<>();

    // 系统参数列表
    public static Map<String, String> PARAMS = new ConcurrentHashMap<>();
}
