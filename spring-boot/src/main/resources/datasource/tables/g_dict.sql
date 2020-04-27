CREATE TABLE IF NOT EXISTS g_dict(
    id        bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    code      varchar(255) NOT NULL COMMENT '字典编码',
    type      varchar(255) DEFAULT NULL COMMENT '字典类型（为空表示字典名）',
    type_name varchar(255) NOT NULL COMMENT '类型名称',
    o1        varchar(255) DEFAULT NULL COMMENT '补充属性1',
    o2        varchar(255) DEFAULT NULL COMMENT '补充属性2',
    o3        varchar(255) DEFAULT NULL COMMENT '补充属性3',
    PRIMARY KEY (id)
);$$