package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.domain.co.GlobalCaches;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 小工具
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/18 20:26
 */
@RestController
@RequestMapping("/g/utils")
@Api(tags = "实用小工具")
public class UtilsController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UtilsController.class);

    public UtilsController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @ApiOperation("获取公网ip地址，FRP转发服务生效，独立部署时未经测试")
    @GetMapping("/public-host")
    public ReturnVo publicHost() {
        ReturnVo vo = new ReturnVo();
        String fip = request.getHeader("X-Forwarded-For");
        String rip = request.getHeader("X-Real-IP");
        String ip = "";
        // 先尝试获取header中的ip地址，如果存在，则一定是真实地址，最后再使用来源地址
        if (StringUtils.isNotBlank(rip)) {
            ip = rip;
        } else if (StringUtils.isNotBlank(fip)) {
            ip = fip;
        } else {
            ip = request.getRemoteHost();
        }
        Map<String, String > reqs = new HashMap<>();
        reqs.put("host", ip);
        reqs.put("serverHost", GlobalCaches.PUBLIC_IP);
        vo.setData(reqs);
        return vo;
    }

}
