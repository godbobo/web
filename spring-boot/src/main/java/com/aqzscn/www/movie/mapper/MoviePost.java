package com.aqzscn.www.movie.mapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@ApiModel("字典表")
@Table(name = "mv_post")
public class MoviePost {

    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("时光网标识")
    private String mtimeId;

    @ApiModelProperty("电影中文名")
    private String movieCnName;

    @ApiModelProperty("电影英文名")
    private String movieEnName;

    @ApiModelProperty("分类")
    private String genre;

    @ApiModelProperty("描述")
    private String intro;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("时长")
    private String movieTime;

    @ApiModelProperty("上映时间")
    private Date date;

    @ApiModelProperty("导演")
    private String director;

    @ApiModelProperty("主演")
    private String star;

    @ApiModelProperty("评分")
    private String point;

    @ApiModelProperty("票房")
    private String totalNum;

    @ApiModelProperty("评论列表（json字符串形式）")
    private String commentList;

    @ApiModelProperty("影视平台列表（json字符串形式）")
    private String platforms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMtimeId() {
        return mtimeId;
    }

    public void setMtimeId(String mtimeId) {
        this.mtimeId = mtimeId;
    }

    public String getMovieCnName() {
        return movieCnName;
    }

    public void setMovieCnName(String movieCnName) {
        this.movieCnName = movieCnName;
    }

    public String getMovieEnName() {
        return movieEnName;
    }

    public void setMovieEnName(String movieEnName) {
        this.movieEnName = movieEnName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getCommentList() {
        return commentList;
    }

    public void setCommentList(String commentList) {
        this.commentList = commentList;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }
}
