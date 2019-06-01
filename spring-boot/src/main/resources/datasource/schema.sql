# 创建一个测试的数据表 若要创建存储过程，可以配置分隔符为$$，具体信息查看OneNote记录
# 因为个人更新的话版本变动不大，因此可以区分不同环境 在开发环境不执行数据库的初始化操作 生产环境配置总是执行
# 可以建立一个备份目录，将以往执行过的sql语句存放起来
DROP TABLE IF EXISTS `cqm_devices_heart_log`;$$
CREATE TABLE `cqm_devices_heart_log`
(
  `LOGID`   varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
  `LOGKSSJ` datetime                                                NOT NULL COMMENT '记录开始时间',
  `LOGJSSJ` datetime                                                NOT NULL COMMENT '记录更新时间',
  `SN`      varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '设备编号',
  `ONLINE`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '1表示在线,0表示离线',
  `MSG`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常信息',
  `JGSJ`    varchar(72) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '间隔时间',
  PRIMARY KEY (`LOGID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '心跳日志表'
  ROW_FORMAT = Compact;$$