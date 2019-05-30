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

    public static Map<String, Long> ROLES = new ConcurrentHashMap<>();

}
