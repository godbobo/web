package com.aqzscn.www.global.domain.co;

import com.aqzscn.www.global.mapper.Dict;
import com.aqzscn.www.global.mapper.Dispatch;

import java.util.List;
import java.util.Map;
import java.util.Vector;
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

    // 字典表
    public static Map<String, List<Dict>> DICTS = new ConcurrentHashMap<>();

    // 转发服务配置
    public static Dispatch DISPATCH = null;
}
