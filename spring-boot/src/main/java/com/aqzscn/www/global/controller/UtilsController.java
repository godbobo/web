package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.co.GlobalCaches;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.mapper.Dispatch;
import com.aqzscn.www.global.mapper.Mock;
import com.aqzscn.www.global.service.MockService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
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

    private RestTemplate restTemplate;
    private MockService mockService;

    public UtilsController(HttpServletRequest request, HttpServletResponse response, RestTemplate restTemplate, MockService mockService) {
        super(request, response);
        this.mockService = mockService;
        this.restTemplate = restTemplate;
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

    @ApiOperation("获取模拟数据")
    @GetMapping("/dispatch/**")
    public String getMock() throws RuntimeException {
        String requestUrl = this.request.getRequestURL().toString();
        int b = requestUrl.lastIndexOf("/dispatch/");
        return mockRes(null);
    }

    @ApiOperation("中转post请求及获取模拟数据")
    @PostMapping("/dispatch/**")
    public String postDispatchAndMock(@RequestBody(required = false) String json) throws RuntimeException {
        return dispatch(json);
    }

    @ApiOperation("中转put请求及获取模拟数据,注意put方式没有返回值")
    @PutMapping("/dispatch/**")
    public String putDispatchAndMock(@RequestBody(required = false) String json) throws RuntimeException {
        return dispatch(json);
    }

    @ApiOperation("中转delete请求及获取模拟数据，注意delete方式没有返回值")
    @DeleteMapping("/dispatch/**")
    public String deleteDispatchAndMock(@RequestBody(required = false) String json) throws RuntimeException {
        return dispatch(json);
    }

    /**
     * 中转接口
     * @param json 请求体信息
     * @return String
     */
    private String dispatch(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Dispatch dispatch = GlobalCaches.DISPATCH;
            // 查询是否有对应的模拟数据接口，如果没有则走转发接口
            Long dispatchId = dispatch != null ? dispatch.getId() : null;
            String mkr = mockRes(dispatchId);
            if (StringUtils.isNotBlank(mkr)) {
                return mkr;
            }
            if (dispatch == null) {
                throw AppException.of("当前没有激活的转发服务，请激活后使用！");
            }
            // 判断是否需要处理请求数据
            String reqBody = "";
            if (StringUtils.isNotBlank(dispatch.getReqTargetParam()) && StringUtils.isNotBlank(json)) {
                // 对于请求数据，是否需要获取具体数据（仅支持第一层对象）
                JsonNode node = objectMapper.readTree(json);
                String objStr = node.get(dispatch.getReqTargetParam()).asText("");
                if (StringUtils.isNotBlank(dispatch.getReqPrefix())) {
                    reqBody = dispatch.getReqPrefix() + objStr;
                } else {
                    reqBody = objStr;
                }
            }
            // 判断是否需要添加转发接口后面的地址
            String endPath = "";
            if (dispatch.getUsePath() == 1) {
                endPath = getEndPath();
            }
            // 根据请求类型设置转发请求的类型
            ResponseEntity<String> responseEntity = null;
            String requestMethod = this.request != null ? this.request.getMethod() : "";
            if (requestMethod.equals(RequestMethod.PUT.name())) {
                this.restTemplate.put(dispatch.getServiceUrl() + endPath, reqBody, String.class);
            } else if (requestMethod.equals(RequestMethod.DELETE.name())) {
                this.restTemplate.delete(dispatch.getServiceUrl() + endPath, reqBody, String.class);
            } else {
                responseEntity = this.restTemplate.postForEntity(dispatch.getServiceUrl() + endPath, reqBody, String.class);
            }
            // 判断是否需要处理响应数据
            if (responseEntity != null && StringUtils.isNotBlank(dispatch.getResBody()) && StringUtils.isNotBlank(dispatch.getResDataKey())) {
                JsonNode node = objectMapper.readTree(dispatch.getResBody());
                Map<String, Object> map = new HashMap<>();
                Iterator<String> names = node.fieldNames();
                while (names.hasNext()) {
                    String name = names.next();
                    if (name.equals(dispatch.getResDataKey())) {
                        map.put(name, objectMapper.readTree(responseEntity.getBody()));
                    } else {
                        map.put(name, node.get(name));
                    }
                }
                return objectMapper.writeValueAsString(map);
            } else {
                return responseEntity == null ? "操作成功" : responseEntity.getBody();
            }
        } catch (Exception e) {
            throw AppException.of(e.getMessage());
        }
    }

    /**
     * 获取模拟数据
     * @param dispatchId 转发接口id 可能为null
     * @return String
     */
    private String mockRes(Long dispatchId) {
        String path = getEndPath();
        // 若路径为空，则不能通过模拟数据匹配，就不用查数据库了
        if (path.equals("")) {
            return null;
        }

        String method = this.request.getMethod();
        Mock mock = new Mock();
        mock.setDispatchId(dispatchId);
        mock.setPath(path);
        mock.setMethod(method);
        Mock resMock = this.mockService.selectOneMock(mock);
        return resMock == null ? null : resMock.getResbody();
    }

    /**
     * 获取转发接口后面匹配的路径
     */
    private String getEndPath() {
        String requestUrl = this.request.getRequestURL().toString();
        int b = requestUrl.lastIndexOf("/dispatch/");
        return requestUrl.substring(b + 10);
    }

}
