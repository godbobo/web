CREATE TABLE IF NOT EXISTS mv_platform (
    id       bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    name     varchar(255) NOT NULL COMMENT '平台名称',
    movie_id bigint(20)   NOT NULL COMMENT '关联电影',
    m_url    varchar(255) DEFAULT NULL COMMENT '手机观看地址',
    pc_url   varchar(255) NOT NULL COMMENT '电脑观看地址',
    PRIMARY KEY (id),
    KEY mv_post_id (movie_id)
);$$