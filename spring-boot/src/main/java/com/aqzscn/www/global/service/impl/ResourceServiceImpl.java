package com.aqzscn.www.global.service.impl;

import com.aqzscn.www.global.component.FileService;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.mapper.Resource;
import com.aqzscn.www.global.mapper.ResourceMapper;
import com.aqzscn.www.global.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Godbobo
 * @date 2019/6/5
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceServiceImpl implements ResourceService {

    private final ResourceMapper resourceMapper;
    private final FileService fileService;

    @Autowired
    public ResourceServiceImpl(ResourceMapper resourceMapper, FileService fileService) {
        this.resourceMapper = resourceMapper;
        this.fileService = fileService;
    }

    @Override
    public Resource insert(String type, MultipartFile file) throws RuntimeException {
        Resource resource = fileService.uploadFile(type, file);
        if (resource == null) {
            throw AppException.of("无法存储资源信息！");
        }
        return resource;
    }
}
