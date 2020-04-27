CREATE TABLE IF NOT EXISTS blog_series (
    id           bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    name         varchar(255)    NOT NULL COMMENT '系列名',
    auto_index   int(1) unsigned NOT NULL DEFAULT '0' COMMENT '自动标号',
    abs          varchar(255)    NOT NULL COMMENT '介绍',
    img          varchar(255)    NOT NULL COMMENT '配图',
    ignore_order int(1) unsigned NOT NULL DEFAULT '0' COMMENT '忽略手动排序',
    PRIMARY KEY (id) USING BTREE
);$$