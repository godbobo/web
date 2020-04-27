# 添加微信相关配置
insert ignore into g_param(label, zh, val) values ('wechat_appid', '微信公众号appid', '');
insert ignore into g_param(label, zh, val) values ('wechat_appsecret', '微信公众号appsecret', '');
insert ignore into g_param(label, zh, val) values ('wechat_token', '微信公众号token', '');
# 添加角色信息
insert ignore into g_role(name, remark) VALUES ('ADMIN', '管理员');
insert ignore into g_role(name, remark) VALUES ('USER', '用户');