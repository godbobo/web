package com.aqzscn.www.global.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 定时清除系统生成中的缓存内容
 * 需要在项目启动类上使用@EnableScheduling开启定时任务
 *
 * @author Godbobo
 * @date 2019/6/2
 */
@Component
public class ClearCacheSchedule {

    @Value("${myfile.export}")
    private String exportPath;

    private final Logger logger = LoggerFactory.getLogger(ClearCacheSchedule.class);

    /**
     * 定时清除批量导出文件时创建的临时目录
     * 每天凌晨3点执行
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void clearExportDirectory() {
        logger.info("开始执行定时任务：删除导出文件时的临时目录");
        File exportDirectory = new File(exportPath);
        int dNum = 0;
        if (exportDirectory.exists()) {
            // 获取导出目录下的文件夹
            File[] files = exportDirectory.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory() && (f.list() == null || f.list().length==0)) {
                        f.delete();
                        dNum++;
                    }
                }
            }
        }
        logger.info("执行完毕，共删除 {} 个目录", dNum);
    }

}
