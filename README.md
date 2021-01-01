<h1 align="center">后端脚手架</h1>

## 项目特性

- :gem: **优雅美观**：基于 Spring Boot 体系精心设计
- :rocket: **最新技术栈**：使用 Spring Boot,Shiro,MyBatis 等前沿和成熟的技术开发
- :closed_lock_with_key: **安全认证**：基于 token 认证，实现按钮级别的权限认证
- :gear: **最佳实践**：良好的工程实践助您持续产出高质量代码
- :v: **快速高效**：前后端分离，专注后端，快速实现 restful 接口定义

## 项目目录
```
myspringboot  
├── logs -- 日志目录 
├── src/main/java/cn.mypandora.springboot 
|   ├── config                  -- 通用配置  
|   |    ├── converter              -- 转换器配置目录 
|   |    ├── exception              -- restful 接口异常信息配置目录 
|   |    ├── filter                 -- filter配置目录
|   |    ├── redis                  -- redis配置目录
|   |    ├── shiro                  -- shiro配置目录 
|   |    ├── swagger                -- swagger配置目录 
|   |    └── websocket              -- websocket配置目录   
|   ├── core                    -- 通用核心代码  
|   |    ├── annotation             -- 自定义注解 
|   |    ├── base                   -- 通用Mapper和分页类 
|   |    ├── enums                  -- 自定义枚举类 
|   |    ├── exception              -- 异常信息封装类 
|   |    ├── listener               -- websocket监听类 
|   |    ├── shiro                  -- shiro扩展配置 
|   |    ├── support                -- XSS 过滤 
|   |    └── utils                  -- 工具类  
|   |    └── validate               -- 自定义校验组  
|   ├── modular                 -- 业务模块  
|   |    ├── system                 -- 通用后台管理代码目录 
|   |    └── your                   -- 放置你自己的业务代码
└── src/main/resources  
    ├── mybatis                 -- Mybatis的Mapper.xml目录 
    |   ├── mapper                  -- MyBatis sql语句配置目录
    |   └── mybatis-config          -- MyBatis配置文件
    ├── sql -- sql脚本目录 
    |   └── init.sql            -- 初始化建表文件    
    ├── application.yml         -- 项目默认配置文件  
    ├── application-dev.yml     -- 开发环境配置文件  
    ├── application-docker.yml  -- docker环境配置文件  
    ├── application-prod.yml    -- 生产环境配置文件  
    ├── application-test.yml    -- 测试环境配置文件  
    └── logback-spring.xml      -- 日志配置文件  
```

## 技术选型

[x] | 技术名 | 版本  
 :---: | :--- | :---  
[x] | SpringBoot | 2.3.3 
[x] | Redis(spring-boot-starter-data-redis) | 2.3.3
[x] | Mybatis(mybatis-spring-boot-starter) | 2.1.1  
[x] | Mapper(mapper-spring-boot-starter) | 2.1.5
[x] | PageHelper(pagehelper-spring-boot-starter) | 1.2.13 
[x] | Shiro | 1.5.3 
[x] | jjwt | 0.10.7
[x] | jBCrypt | 0.4.1
[x] | jasypt-spring-boot-starter | 3.0.3
[x] | Swagger2(springfox.swagger2) | 2.9.2  
[x] | lombok | 1.18.10    

## 使用说明

1. 项目使用了Lombok简化代码，请安装对应的IDE插件。

## 开发环境

1. openjdk 11
2. MariaDB 10.4.13
3. Redis 5.0

## 快速开始
1. 下载项目
    ```
   git clone https://github.com/hankaibo/myspringboot.git
   ```
   
2. 导入项目
    
    使用自己的 IDE 导入, Intellij IDEA 社区版本即可。

3. 导入数据库

    安装 MySQL(MariaDB) 数据库，执行 init.sql 文件建表初始化数据；
    
    安装 redis。

4. 配置数据库

    打开 application-dev.yml 修改 MySQL 和 Redis 连接信息。

5. 启动项目

    找到 SpringbootApplication 启动类, 启动即可。

6. 启动前端项目

    请参考前端项目[myantdpro](https://github.com/hankaibo/myantdpro)配置。

## 逻辑
  1. POST请求【用户名/密码】到 /api/v1/login 接口进行登入，如果成功返回一个加密 token,role及resources。
  
     token: 之后用户访问每一个需要权限的网址请求必须在 header 中添加 Authorization 字段，例如 Authorization: Bearer token。
  
     role：方便前端处理的角色信息。
  
     resources：用户对应的所有资源数据集合，对每个人的页面进行按钮等组件的动态显示与隐藏。
  2. 登录成功之后，自动发起GET请求（携带token）到 /api/v1/users/info，获取当前登录用户的信息。
     
     user: 当前登录用户的个人信息。
     
  3. 之后单击页面相关按钮发送的请求，都会自动将 token 加入到 header 中，以保证有权限认证，可以成功请求到后台数据。
  
  ![Image text](./image/jwt.png)
## 参考
本文参考了以下项目，特此感谢。
1. [https://github.com/tomsun28/bootshiro](https://github.com/tomsun28/bootshiro)
2. [https://jinnianshilongnian.iteye.com/blog/2049092](https://jinnianshilongnian.iteye.com/blog/2049092)
3. [https://github.com/zhaojun1998/Shiro-Action](https://github.com/zhaojun1998/Shiro-Action)
4. [https://github.com/Smith-Cruise/Spring-Boot-Shiro](https://github.com/Smith-Cruise/Spring-Boot-Shiro)
5. [https://github.com/zzycreate/spring-boot-seed](https://github.com/zzycreate/spring-boot-seed)
6. [https://github.com/stylefeng/Guns](https://github.com/stylefeng/Guns)


## 赞助商

非常感谢 Jetbrains 提供的免费授权。

<a href="https://www.jetbrains.com/" target="_blank"><img src="https://www.jetbrains.com/company/brand/img/logo1.svg" width="100"></a>

