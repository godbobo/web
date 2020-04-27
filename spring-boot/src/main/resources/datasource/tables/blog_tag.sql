CREATE TABLE IF NOT EXISTS blog_tag (
    id    bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    title varchar(12) NOT NULL COMMENT '标签名',
    color varchar(64) NOT NULL COMMENT '字体颜色',
    bg    varchar(64) NOT NULL COMMENT '背景颜色',
    PRIMARY KEY (id) USING BTREE
);$$