# 多模块集成后台

# 更新日志

## 20190928 更新记录

1. 转发模块新增CURD方法
2. 新增通用Mapper，详情可参考[https://github.com/abel533/Mapper/wiki/2.2-mapping](https://github.com/abel533/Mapper/wiki/2.2-mapping)
3. 日志记录可记录 request Body 内容
4. 日志记录可通过uuid确定请求信息对应的的响应内容

## 20191010 更新记录

1. 解决插入mysql中文乱码问题
2. 取消Spring Security配置的角色权限验证
3. 完善JacksonUtil工具类，使之可以正常转换带有JsonFilter注解的实体类
4. 完善用户实体类字段，对可能产生空指针的地方做了处理