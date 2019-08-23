# myspringboot

#### 项目介绍
SpringBoot的简洁脚手架，前后端分离，Restful接口定义，Token 权限认证。

#### 项目目录
```
myspringboot  
├── src/main/java/cn.mypandora.springboot 
|   ├── config -- 通用配置  
|   |    ├── druid -- 连接池配置文件  
|   |    ├── shiro -- 权限框架配置文件 
|   |    └── swagger -- 接口文档配置文件   
|   ├── core -- 通用核心代码  
|   |    ├── base -- 通用Mapper及前端返回数据格式封装 
|   |    ├── enums -- 枚举类 
|   |    ├── exception -- 异常类 
|   |    ├── shiro -- shiro扩展配置 
|   |    ├── support -- XSS 过滤 
|   |    ├── exception -- 异常类 
|   |    └── utils -- 工具类  
|   ├── modular -- 业务模块  
|   |    ├── system -- 通用后台管理代码目录 
|   |    └── your -- 放置你自己的业务代码
|   └── SpringbootApplication.java -- 项目启动类  
└── src/main/resources  
    ├── mapper -- Mybatis的Mapper.xml目录  
    ├── sql -- sql脚本目录  
    ├── application.yml -- 项目默认配置文件  
    ├── application-dev.yml -- 测试环境配置文件  
    ├── application-prod.yml -- 生产环境配置文件  
    └── logback-spring.xml -- 日志配置文件  
```

#### 技术选型

[x] | 技术名 | 版本  
 :---: | :---: | :---:  
[x] | SpringBoot | 2.1.5.RELEASE  
[x] | Web(spring-boot-starter-web) | 2.1.5.RELEASE  
[x] | Redis(spring-boot-starter-data-redis) | 2.1.5.RELEASE  
[x] | Mybatis(mybatis-spring-boot-starter) | 2.0.1  
[x] | Mapper(mapper-spring-boot-starter) | 2.1.5
[x] | PageHelper(pagehelper-spring-boot-starter) | 1.2.12 
[x] | Druid(druid-spring-boot-starter) | 1.1.16  
[x] | Shiro | 1.4.0 
[x] | Swagger2(springfox.swagger2) | 2.9.2  
[x] | lombok | 1.18.8    

#### 使用说明

1. 项目使用了Lombok简化代码，请安装对应的IDE插件。

#### 开发环境

1. openjdk 11.0.4 2019-07-16
2. 10.0.34-MariaDB-0ubuntu0.16.04.1
3. Redis server v=3.0.6

#### 快速开始
1. 下载项目
    ```
   git clone https://github.com/hankaibo/myspringboot.git
   ```
   
2. 导入项目
    
    使用自己的 IDE 导入, Eclipse 和 Intellij IDEA 均可。

3. 导入数据库（可使用作者默认）

    执行 create.sql 文件；创建自己的 redis 数据库。

4. 配置redis（可使用作者默认）

    打开 application-dev.yml 修改 MySQL 和 Redis 连接信息。

5. 启动项目

    找到 SpringbootApplication 启动类, 启动即可。

6. 启动前端项目

    请参考前端项目配置。

#### 程序逻辑
  1. POST请求【用户名/密码】到 /api/v1/login 进行登入，如果成功返回一个加密 token,role及resources。
  
     token: 之后用户访问每一个需要权限的网址请求必须在 header 中添加 Authorization 字段，例如 Authorization: Bearer token。
  
     role：方便前端处理的角色信息。
  
     resources：用户对应的所有资源数据集合，对每个人的页面进行按钮等组件的动态显示与隐藏。
  2. GET请求（携带token）到 /api/v1/users/info，获取当前登录用户的信息 user和menuList。
     
     user: 当前登录用户的个人信息。
     
     menuList: 当前登录用户有权限要显示的菜单数据，与resources是相对应的。（菜单打开的页面包含资源，资源属于某个菜单对应的页面。）
     
# 参考
本文大量参考了bootshiro，特此感谢。
1. [https://jinnianshilongnian.iteye.com/blog/2049092](https://jinnianshilongnian.iteye.com/blog/2049092)
2. [https://github.com/tomsun28/bootshiro](https://github.com/tomsun28/bootshiro)
3. [https://github.com/zhaojun1998/Shiro-Action](https://github.com/zhaojun1998/Shiro-Action)
4. [https://github.com/Smith-Cruise/Spring-Boot-Shiro](https://github.com/Smith-Cruise/Spring-Boot-Shiro)
5. [https://github.com/zzycreate/spring-boot-seed](https://github.com/zzycreate/spring-boot-seed)
6. [https://github.com/stylefeng/Guns](https://github.com/stylefeng/Guns)

# 注意
本项目仅供学习使用，从未在生产环境得到检验，请小心使用。

本项目仅供学习使用，从未在生产环境得到检验，请小心使用。

本项目仅供学习使用，从未在生产环境得到检验，请小心使用。
