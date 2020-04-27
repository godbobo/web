CREATE TABLE IF NOT EXISTS g_user (
    id        bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    real_name varchar(32)     NOT NULL COMMENT '姓名',
    password  varchar(64)     NOT NULL COMMENT '密码',
    username  varchar(64)     NOT NULL COMMENT '用户名',
    gender    int(1) unsigned NOT NULL DEFAULT '0' COMMENT '性别',
    tel       varchar(16)              DEFAULT NULL COMMENT '手机号',
    email     varchar(255)    NOT NULL COMMENT '邮箱',
    head_img  varchar(255)             DEFAULT NULL COMMENT '头像',
    locked    int(1)          NOT NULL DEFAULT '0' COMMENT '是否被锁定，用于禁止违规用户登录',
    enabled   int(1)          NOT NULL DEFAULT '0' COMMENT '是否可用，用于注册后激活用户',
    reg_time  datetime        NOT NULL COMMENT '注册时间',
    sign      varchar(255)             DEFAULT NULL COMMENT '个性签名',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE KEY username (username) USING BTREE,
    UNIQUE KEY tel (tel) USING BTREE
);$$