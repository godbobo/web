# Restful接口后台

本项目使用 Spring Boot 框架开发，(尽可能)提供 Restful 形式的接口。所有功能及特性基本采用 Spring Boot 的原生方式或 Spring Boot 提供的整合包。目前包含的特性有：

1. 启动时自动更新SQL文件
2. 定时任务Schedule的使用
3. HTTP请求的使用
4. 邮件服务
5. MyBatis
6. 微信后台
7. 博客后台(待完善)
8. 电影记录后台(待完善)

## 安装

IDE采用IDEA开发，建议使用该软件运行项目。

项目拷贝下来后，新建一个数据库，名字叫`web`，字符集建议设置为`utf8mb4`，这样可以支持存储表情符号，排序方式为`utf8mb4_unicode_ci`。

> 数据库名称若要自定义，需要在application-{environment}.yml中同步修改。

然后启动程序，运行一遍过后会在数据库生成所有的数据表及基本信息。

> 启动时在数据库自动生成了两个用户，一个管理员身份(admin/1)，一个普通用户(user/1)。若要手动修改密码，可在项目运行后通过`/api/g/user/crypt-pwd?pwd=1`获取加密后的密码并在数据库中修改。

## 使用

### 1、项目配置文件

配置文件位于`/src/main/resources/`目录下application开头的yml文件，默认提供了`application.yml`和`application-dev.yml`文件。

其中`application.yml`用于切换环境配置文件及一些不会随环境变化而改变的配置信息，而`dev`文件则存储与环境相关的配置，如数据库连接信息。

其实还有一个`application-prod.yml`文件没有包含在项目中，一般用这个文件名来表示生产环境。

要切换环境配置文件也很简单，在`application.yml`中修改以下内容即可：

```yaml
spring:
  profiles:
    active: dev
```

同时我们还有在生成jar包后切换环境的需求，可以创建一个sh文件或bat文件并配置以下内容：

```bat
@echo off
title web-admin-8520
java -jar web-0.0.1.jar --spring.profiles.active=prod
```

上面是 bat 文件的示例，在指定环境的同时还设置了窗口的标题为项目名称加端口号便于我们识别。

### 2、启动时更新SQL文件

有了这个特性，我们可以在项目部署时省去对数据库的手动操作。

具体做法是在`application.yml`中配置要执行的SQL文件地址：

```yaml
spring:
    datasource:
        schema:
          - classpath:datasource/tables/g_param.sql
          - classpath:datasource/tables/g_dict.sql
          - classpath:datasource/tables/g_user.sql
          - classpath:datasource/tables/g_role.sql
          - classpath:datasource/tables/g_roles_map.sql
          - classpath:datasource/tables/g_dispatch.sql
          - classpath:datasource/tables/g_mock.sql
          - classpath:datasource/tables/g_resource.sql
          - classpath:datasource/tables/blog_article.sql
          - classpath:datasource/tables/blog_series.sql
          - classpath:datasource/tables/blog_tag.sql
          - classpath:datasource/tables/blog_tags_map.sql
          - classpath:datasource/tables/mv_platform.sql
          - classpath:datasource/tables/mv_post.sql
        data: classpath:datasource/data.sql
        separator: $$
        continue-on-error: true
        initialization-mode: always
```

上面的`schema`和`data`字段都可以接收列表或者单个路经，schema一般用于配置DDL(数据定义语言)，这里配置的SQL文件执行顺序在data配置的SQL前面，data一般用于配置DDL（数据操纵语言）。

因为数据表很多且有一定规律，我就单独给每个数据表创建一个文件放在`/resources/datasource/tables/`下面，而要插入数据的地方其实很少且无规律，全部放在`data.sql`文件中应该就可以了。

> 因为项目复杂起来后很可能会使用到数据库存储过程，而Spring Boot 默认是以`;`来分割SQL语句的，这就会导致创建存储过程的语言出现问题，所以上面使用`separator:$$`来修改默认分隔符。

上面的`continue-on-error`的配置很容易理解，就是遇到错误时继续，我这里把它开启了。（尽管感觉关掉会更好，更容易发现错误）

最后一定要记得将`initialization-mode`设置为**always**,这样配置后每次项目启动都会执行我们配置的SQL。

都会执行SQL就会导致一个问题：已存在的数据表会创建失败，已存在的数据会重复插入。这时我们需要通过特殊的SQL语法去规避这种问题，即在创建表时加上`IF NOT EXISTS`关键字，插入数据时加上`INSERT IGNORE INTO`关键字。示例如下：

```sql
# 如果不存在表g_roles_map则创建
CREATE TABLE IF NOT EXISTS g_roles_map (
    uid bigint(20) NOT NULL COMMENT '用户主键',
    rid bigint(20) NOT NULL COMMENT '角色主键',
    PRIMARY KEY (uid, rid)
);

# 插入时如果主键冲突或 unique key 冲突则忽略此次插入
insert ignore into g_param(label, zh, val) values ('wechat_appid', '微信公众号appid', '');

```

有了上面的做法，基本上就满足需求了。但还有一种情况需要考虑，就是数据表已经创建，但表结构修改了，这种情况需要判断要删除或新增的字段是否存在，然后再做相关操作。另外这种操作仍然属于DDL语言，所以最好就直接**放在创建表语句的后面**。

### 3、Spring Security

项目采用Oauth2的password模式验证，在AuthorizationConfig中配置了客户端名称和密钥（密钥必须进行加密），如果要获取token，客户端必须在请求头携带 `Basic {clientId}:{clientSecret}`,而且**{clientId}:{clientSecret}**组合后的字符串要进行base64加密。

具体获取token方式可参考`/resources/templates/get-authorization-for-test.html`中的做法。

## 功能

### 1、日志记录

代码位于`/global/config/aspect/LogAspect.java`，使用了Spring的AOP机制拦截请求，得到请求数据及返回结果。

对于Mybatis的日志暂时还没辙。

# 更新日志

## 20200427 更新记录

1. 增加电影记录功能
2. 优化项目结构，可在启动项目时自动更新SQL文件，免除手动操作出现失误的可能。
3. 将项目中敏感配置移动到数据库中，避免密钥泄露风险

## 20190928 更新记录

1. 转发模块新增CURD方法
2. 新增通用Mapper，详情可参考[https://github.com/abel533/Mapper/wiki/2.2-mapping](https://github.com/abel533/Mapper/wiki/2.2-mapping)
3. 日志记录可记录 request Body 内容
4. 日志记录可通过uuid确定请求信息对应的的响应内容

## 20191010 更新记录

1. 解决插入mysql中文乱码问题
2. 修复打包后运行时报SpringContextUtil.getBean()为null的错误
3. 修复转发接口不能转发json字符串的问题
2. 取消Spring Security配置的角色权限验证
3. 完善JacksonUtil工具类，使之可以正常转换带有JsonFilter注解的实体类
4. 完善用户实体类字段，对可能产生空指针的地方做了处理
5. 用户基础增改查功能完成
6. 解决博客文章查询失败问题