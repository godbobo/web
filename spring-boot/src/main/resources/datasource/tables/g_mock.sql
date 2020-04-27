CREATE TABLE IF NOT EXISTS g_mock (
    id          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    path        varchar(255) NOT NULL COMMENT '访问路径（相对地址，前面不需要加/）',
    resbody     text         NOT NULL COMMENT '返回信息',
    remark      varchar(255) DEFAULT NULL COMMENT '描述信息',
    dispatch_id bigint(20)   DEFAULT NULL COMMENT '针对具体的某个转发服务',
    method      varchar(255) DEFAULT NULL COMMENT '请求方法，为空表示全部',
    PRIMARY KEY (id) USING BTREE,
    KEY g_mock_target (dispatch_id) USING BTREE
);$$