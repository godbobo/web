CREATE TABLE IF NOT EXISTS g_resource (
    id          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    file_name   varchar(255) NOT NULL COMMENT '文件名',
    path        varchar(255) NOT NULL COMMENT '文件系统路径',
    uri         varchar(255) NOT NULL COMMENT '文件访问路径',
    size        int(11)      NOT NULL COMMENT '文件大小',
    create_time datetime     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (id) USING BTREE,
    KEY resource_uri (uri(191)) USING BTREE COMMENT '其他用到资源的地方都将用这个作为资源访问地址，在需要查询这张表时加索引速度会更快一点'
);$$