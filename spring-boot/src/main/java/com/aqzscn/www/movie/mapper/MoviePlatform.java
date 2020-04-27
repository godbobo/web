package com.aqzscn.www.movie.mapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel("视频网站")
@Table(name = "mv_platform")
public class MoviePlatform {

    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("平台名")
    private String name;

    @ApiModelProperty("关联电影id")
    private Long movieId;

    @ApiModelProperty("手机端观看地址")
    private String mUrl;

    @ApiModelProperty("电脑端观看地址")
    private String pcUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(String pcUrl) {
        this.pcUrl = pcUrl;
    }
}
