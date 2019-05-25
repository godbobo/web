package com.aqzscn.www.blog.domain.vo;

import com.aqzscn.www.blog.mapper.BlogArticle;
import com.aqzscn.www.global.domain.dto.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("博文数据请求对象")
public class ArticleRequest extends BlogArticle {

    @ApiModelProperty(value = "分页对象")
    private PageRequest pageRequest;

}
