package com.aqzscn.www.global.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间相关工具类
 *
 * @author Godbobo
 * @date 2019/4/5.
 */
public class DateUtil {

    // 格式化时间
    public static String format(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    // 延后或提前时间
    public static Date push(Long mills) {
        return new Date(System.currentTimeMillis() + mills);
    }
    public static Date pushByMinute(int m) {
        return push(m * 60 * 1000L);
    }

}
