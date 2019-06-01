package com.aqzscn.www.global.component;

import com.aqzscn.www.global.domain.co.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件上传下载服务
 *
 * @author Godbobo
 * @date 2019/6/1
 */
@Component
public class FileService {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    public FileService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 下载纯文本文件
     *
     * @param content 文件内容
     * @param fileName 文件名
     * @param fileType 文件类型（需要手动加.）
     */
    public void downloadPlain(String content, String fileName, String fileType) throws RuntimeException {
        if (StringUtils.isAnyBlank(content, fileName, fileType)) {
            throw AppException.of("文件信息不完整，无法下载！");
        }
        BufferedOutputStream buffer = null;
        String enter = "\r\n";
        ServletOutputStream outputStream = null;
        try {
            response.setContentType("text/plain");
            // 必须进行编码，否则中文文件名会变成下划线
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + fileType );
            outputStream = response.getOutputStream();
            buffer = new BufferedOutputStream(outputStream);
            // 写入内容
            buffer.write(content.getBytes(StandardCharsets.UTF_8));
            buffer.flush();
            buffer.close();
            outputStream.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
