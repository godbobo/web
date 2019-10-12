package com.aqzscn.www.global.mapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@ApiModel("模拟数据表")
@Table(name = "g_mock")
public class Mock implements Serializable {

    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("访问地址")
    private String path;

    @ApiModelProperty("响应结果")
    private String resbody;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("所属转发服务")
    private Long dispatchId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getResbody() {
        return resbody;
    }

    public void setResbody(String resbody) {
        this.resbody = resbody;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }
}
