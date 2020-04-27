CREATE TABLE IF NOT EXISTS blog_article (
    id           bigint(20)          NOT NULL AUTO_INCREMENT COMMENT '主键',
    title        varchar(255)        NOT NULL COMMENT '博文标题',
    abstract_txt varchar(255)                 DEFAULT NULL COMMENT '博文简介',
    abstract_img varchar(255)                 DEFAULT NULL COMMENT '博文配图',
    content      longtext            NOT NULL COMMENT '正文',
    update_time  datetime            NOT NULL COMMENT '更新时间',
    view_num     bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '浏览量',
    series       bigint(20)                   DEFAULT NULL COMMENT '所属连载系列',
    series_order int(11)                      DEFAULT NULL COMMENT '在连载中的排序',
    PRIMARY KEY (id) USING BTREE,
    KEY blog_series (series) USING BTREE
);$$