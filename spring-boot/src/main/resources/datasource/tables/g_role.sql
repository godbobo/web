CREATE TABLE IF NOT EXISTS g_role (
    id     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    name   varchar(16) NOT NULL COMMENT '角色名',
    remark varchar(64) NOT NULL COMMENT '注释',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE KEY name (name) USING BTREE
);$$