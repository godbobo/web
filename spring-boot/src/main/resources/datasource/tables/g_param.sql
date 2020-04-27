CREATE TABLE IF NOT EXISTS g_param (
    id    int(11)      NOT NULL AUTO_INCREMENT COMMENT '参数表主键',
    label varchar(64)  NOT NULL COMMENT '参数名',
    zh    varchar(64)  NOT NULL COMMENT '参数中文名',
    val   varchar(255) NOT NULL COMMENT '参数值',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE KEY label (label) USING BTREE
);$$