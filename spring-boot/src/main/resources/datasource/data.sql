# 添加微信相关配置
insert ignore into g_param(label, zh, val) values ('wechat_appid', '微信公众号appid', '');
insert ignore into g_param(label, zh, val) values ('wechat_appsecret', '微信公众号appsecret', '');
insert ignore into g_param(label, zh, val) values ('wechat_token', '微信公众号token', '');
# 添加角色信息
insert ignore into g_role(id, name, remark) VALUES (1, 'ADMIN', '管理员');
insert ignore into g_role(id, name, remark) VALUES (2, 'USER', '用户');
# 添加用户信息
insert ignore into g_user(id, real_name, password, username, gender, email, locked, enabled, reg_time, sign) VALUES (1, '管理员', '$2a$10$dIGW7i3Lz7vIWb/mymPtKu0TgDtvd7jJ1a9NZSlanj/ARlc6LzmHe', 'admin', 0, 'admin@aqzscn.com', 0, 1, now(), '我是管理员，我法力无边');
insert ignore into g_user(id, real_name, password, username, gender, email, locked, enabled, reg_time, sign) VALUES (2, '用户', '$2a$10$dIGW7i3Lz7vIWb/mymPtKu0TgDtvd7jJ1a9NZSlanj/ARlc6LzmHe', 'user', 0, 'user@aqzscn.com', 0, 1, now(), '我是用户，我快乐无边');
insert ignore into g_roles_map(uid, rid) VALUES (1, 2);
insert ignore into g_roles_map(uid, rid) VALUES (1, 1);
insert ignore into g_roles_map(uid, rid) VALUES (2, 2);