package com.aqzscn.www.global.controller;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.aqzscn.www.global.config.validation.ValidationGroup2;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.dto.MyPage;
import com.aqzscn.www.global.domain.dto.PageRequest;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.mapper.Dispatch;
import com.aqzscn.www.global.service.DispatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(tags = "请求中转服务")
@RequestMapping("/g")
public class DispatchController extends BaseController {

    private final RestTemplate restTemplate;
    private DispatchService dispatchService;

    public DispatchController(HttpServletRequest request, HttpServletResponse response, RestTemplate restTemplate, DispatchService dispatchService) {
        super(request, response);
        this.restTemplate = restTemplate;
        this.dispatchService = dispatchService;
    }

    @ApiOperation("获取中转服务列表")
    @GetMapping("/dispatches")
    public ReturnVo selectDispatches(@Validated(ValidationGroup2.class) PageRequest pageRequest) throws RuntimeException {
        ReturnVo vo = new ReturnVo();
        MyPage page = dispatchService.selectDispatch(pageRequest.getPageNum(), pageRequest.getPageSize());
        vo.setData(page);
        return vo;
    }


    @ApiOperation("新增中转服务")
    @PostMapping("/dispatches")
    public ReturnVo insertDispatch(@Validated(ValidationGroup2.class) @RequestBody Dispatch dispatch, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return this.response(this.dispatchService.insertDispatch(dispatch));
    }

    @ApiOperation("修改中转服务")
    @PutMapping("/dispatches")
    public ReturnVo updateDispatch(@Validated(ValidationGroup1.class) @RequestBody Dispatch dispatch, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return this.response(this.dispatchService.updateDispatch(dispatch));
    }

    @ApiOperation("删除中转服务")
    @DeleteMapping("/dispatches")
    public ReturnVo deleteDispatch(@Validated(ValidationGroup1.class) @RequestBody Dispatch dispatch, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return this.response(this.dispatchService.deleteDispatch(dispatch));
    }

    @ApiOperation("切换中转服务")
    @PutMapping("/dispatch-status")
    public ReturnVo switchService(@Validated(ValidationGroup1.class) @RequestBody Dispatch dispatch, BindingResult result) throws RuntimeException {
        if (result.hasErrors()) {
            throw AppException.of(result.getAllErrors());
        }
        return this.response(this.dispatchService.switchDispatch(dispatch));
    }


//    @ApiOperation("中转post请求(仅支持POST JSON数据)")
//    @PostMapping("/dispatch/**")
//    public String post(@RequestBody String json) throws RuntimeException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            Dispatch dispatch = GlobalCaches.DISPATCH;
//            // 判断当前转发服务是否激活
//            if (dispatch == null) {
//                throw AppException.of("当前没有激活的转发服务，请激活后使用！");
//            }
//            // 判断是否需要处理请求数据
//            String reqBody = json;
//            if (StringUtils.isNotBlank(dispatch.getReqTargetParam())) {
//                // 对于请求数据，是否需要获取具体数据（仅支持第一层对象）
//                JsonNode node = objectMapper.readTree(json);
//                String objStr = objectMapper.writeValueAsString(node.get(dispatch.getReqTargetParam()));
//                if (StringUtils.isNotBlank(dispatch.getReqPrefix())) {
//                    reqBody = "jsonRest=" + objStr;
//                } else {
//                    reqBody = objStr;
//                }
//            }
//            // URL地址
//            ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(dispatch.getServiceUrl(), reqBody, String.class);
//            // 判断是否需要处理响应数据
//            if (StringUtils.isNotBlank(dispatch.getResBody()) && StringUtils.isNotBlank(dispatch.getResDataKey())) {
//                JsonNode node = objectMapper.readTree(dispatch.getResBody());
//                Map<String, Object> map = new HashMap<>();
//                Iterator<String> names = node.fieldNames();
//                while (names.hasNext()) {
//                    String name = names.next();
//                    if (name.equals(dispatch.getResDataKey())) {
//                        map.put(name, objectMapper.readTree(responseEntity.getBody()));
//                    } else {
//                        map.put(name, node.get(name));
//                    }
//                }
//                return objectMapper.writeValueAsString(map);
//            } else {
//                return responseEntity.getBody();
//            }
//        } catch (Exception e) {
//            throw AppException.of(e.getMessage());
//        }
//    }

}
