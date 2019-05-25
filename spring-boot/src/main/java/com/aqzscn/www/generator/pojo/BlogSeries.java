package com.aqzscn.www.generator.pojo;

public class BlogSeries {
    private Long id;

    private String name;

    private Integer autoIndex;

    private String abs;

    private String img;

    private Integer ignoreOrder;

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
        this.name = name == null ? null : name.trim();
    }

    public Integer getAutoIndex() {
        return autoIndex;
    }

    public void setAutoIndex(Integer autoIndex) {
        this.autoIndex = autoIndex;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs == null ? null : abs.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Integer getIgnoreOrder() {
        return ignoreOrder;
    }

    public void setIgnoreOrder(Integer ignoreOrder) {
        this.ignoreOrder = ignoreOrder;
    }
}