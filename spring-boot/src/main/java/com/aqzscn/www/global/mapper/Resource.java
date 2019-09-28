package com.aqzscn.www.global.mapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Godbobo
 * @date 2019/6/5
 */
@Data
@ApiModel("文件资源（包括图片）")
@Table(name = "g_resource")
public class Resource {

    @Id
    @ApiModelProperty("资源主键")
    private Long id;

    @ApiModelProperty("文件名")
    private String fileName;

    private String path;

    private String uri;

    private Integer size;

    private Date createTime;

}
