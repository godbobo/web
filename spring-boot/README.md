# 多模块集成后台

# 更新日志

## 20190928 更新记录

1. 转发模块新增CURD方法
2. 新增通用Mapper，详情可参考[https://github.com/abel533/Mapper/wiki/2.2-mapping](https://github.com/abel533/Mapper/wiki/2.2-mapping)
3. 日志记录可记录 request Body 内容
4. 日志记录可通过uuid确定请求信息对应的的响应内容

## 20191010 更新记录

1. 解决插入mysql中文乱码问题
2. 修复打包后运行时报SpringContextUtil.getBean()为null的错误
3. 修复转发接口不能转发json字符串的问题