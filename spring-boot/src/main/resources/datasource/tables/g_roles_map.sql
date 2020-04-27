CREATE TABLE IF NOT EXISTS g_roles_map (
    uid bigint(20) NOT NULL COMMENT '用户主键',
    rid bigint(20) NOT NULL COMMENT '角色主键',
    PRIMARY KEY (uid, rid)
);$$