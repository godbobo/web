package com.aqzscn.www.global.mapper;

import com.aqzscn.www.global.config.validation.ValidationGroup1;
import com.aqzscn.www.global.config.validation.ValidationGroup2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@ApiModel("转发服务表")
@Table(name = "g_dispatch")
public class Dispatch implements Serializable {

    @Id
    @ApiModelProperty("主键")
    @NotNull(message = "{dispatch.service.id.notnull}", groups = {ValidationGroup1.class})
    private Long id;

    @ApiModelProperty("服务名")
    @NotBlank(message = "{dispatch.service.serviceName.notnull}", groups = {ValidationGroup2.class})
    private String serviceName;

    @ApiModelProperty("服务地址")
    @NotBlank(message = "{dispatch.service.serviceUrl.notnull}", groups = {ValidationGroup2.class})
    private String serviceUrl;

    @ApiModelProperty("新请求体键名")
    private String reqTargetParam;

    @ApiModelProperty("请求体前缀")
    private String reqPrefix;

    @ApiModelProperty("自定义响应体")
    private String resBody;

    @ApiModelProperty("自定义响应体数据键名")
    private String resDataKey;

    @ApiModelProperty("是否启用")
    private Integer enable;

    @ApiModelProperty("是否拼接路径")
    private Integer usePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getReqTargetParam() {
        return reqTargetParam;
    }

    public void setReqTargetParam(String reqTargetParam) {
        this.reqTargetParam = reqTargetParam;
    }

    public String getReqPrefix() {
        return reqPrefix;
    }

    public void setReqPrefix(String reqPrefix) {
        this.reqPrefix = reqPrefix;
    }

    public String getResBody() {
        return resBody;
    }

    public void setResBody(String resBody) {
        this.resBody = resBody;
    }

    public String getResDataKey() {
        return resDataKey;
    }

    public void setResDataKey(String resDataKey) {
        this.resDataKey = resDataKey;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getUsePath() {
        return usePath;
    }

    public void setUsePath(Integer usePath) {
        this.usePath = usePath;
    }
}
