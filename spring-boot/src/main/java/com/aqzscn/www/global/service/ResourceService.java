package com.aqzscn.www.global.service;

import com.aqzscn.www.global.mapper.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件资源服务
 *
 * @author Godbobo
 * @date 2019/6/5
 */
public interface ResourceService {

    /**
     * 新增文件
     *
     * @param type 文件类型
     * @param file 文件流
     * @return 文件信息
     * @throws RuntimeException 运行时异常
     */
    Resource insert(String type, MultipartFile file) throws RuntimeException;

}
