package com.aqzscn.www.blog.mapper;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("博文连载")
public class BlogSeries {
    private Long id;

    private String name;

    private Integer autoIndex;

    private String abs;

    private String img;

    private Integer ignoreOrder;
}