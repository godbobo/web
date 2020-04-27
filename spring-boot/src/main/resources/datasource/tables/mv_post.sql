CREATE TABLE IF NOT EXISTS mv_post (
    id            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    mtime_id      varchar(10)  DEFAULT NULL COMMENT '时光网电影标识',
    movie_cn_name varchar(255) NOT NULL COMMENT '电影中文名',
    movie_en_name varchar(255) DEFAULT NULL COMMENT '电影英文名',
    genre         varchar(255) DEFAULT NULL COMMENT '分类',
    intro         varchar(255) DEFAULT NULL COMMENT '介绍',
    cover         varchar(255) DEFAULT NULL COMMENT '封面图片',
    movie_time    varchar(20)  DEFAULT NULL COMMENT '电影时长',
    date          datetime     NOT NULL COMMENT '上映时间',
    director      varchar(64)  NOT NULL COMMENT '导演',
    star          varchar(255) NOT NULL COMMENT '主演',
    point         varchar(20)  DEFAULT NULL COMMENT '评分',
    total_num     varchar(64)  DEFAULT NULL COMMENT '票房',
    comment_list  text COMMENT '评论列表',
    platforms     text COMMENT '可观看影视平台列表',
    PRIMARY KEY (id)
);$$