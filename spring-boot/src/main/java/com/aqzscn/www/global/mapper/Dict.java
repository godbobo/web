package com.aqzscn.www.global.mapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/6 19:14
 */
@ApiModel("字典表")
@Table(name = "g_dict")
public class Dict implements Serializable {

    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("字典编码")
    private String code;

    @ApiModelProperty("字典类型（为空则是某个类型的字典名）")
    private String type;

    @ApiModelProperty("类型名称")
    private String typeName;

    @ApiModelProperty("补充属性1")
    private String o1;

    @ApiModelProperty("补充属性2")
    private String o2;

    @ApiModelProperty("补充属性3")
    private String o3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getO1() {
        return o1;
    }

    public void setO1(String o1) {
        this.o1 = o1;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

}
