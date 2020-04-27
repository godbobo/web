CREATE TABLE IF NOT EXISTS g_dispatch (
    id               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    service_name     varchar(255) NOT NULL COMMENT '服务名称',
    service_url      varchar(255) NOT NULL COMMENT '服务地址（根路径地址）',
    req_target_param varchar(255)          DEFAULT NULL COMMENT '将json请求的某个参数作为新的请求体',
    req_prefix       varchar(255)          DEFAULT NULL COMMENT '请求体前添加自定义内容',
    res_body         text COMMENT '自定义响应体，为空则直接返回中转的响应体',
    res_data_key     varchar(255)          DEFAULT NULL COMMENT '自定义响应体存放响应内容的键名',
    enable           int(1)       NOT NULL DEFAULT '0' COMMENT '是否启用',
    use_path         int(1)       NOT NULL COMMENT '是否拼接路径',
    PRIMARY KEY (id)
);$$