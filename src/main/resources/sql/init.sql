-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.4.13-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 my_database 的数据库结构
CREATE DATABASE IF NOT EXISTS `my_database` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `my_database`;

-- 导出  表 my_database.sys_department 结构
CREATE TABLE IF NOT EXISTS `sys_department`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id`   bigint(20) unsigned DEFAULT NULL COMMENT '父ID',
    `name`        varchar(255)        NOT NULL COMMENT '部门名称',
    `lft`         int(11) unsigned    NOT NULL COMMENT '部门左值',
    `rgt`         int(11) unsigned    NOT NULL COMMENT '部门右值',
    `level`       int(11) unsigned    NOT NULL COMMENT '部门层级',
    `status`      tinyint(3)          NOT NULL COMMENT '启用状态',
    `is_update`   tinyint(3)          NOT NULL COMMENT '是否可更新',
    `description` varchar(255)        DEFAULT NULL COMMENT '部门描述',
    `create_time` datetime            DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime            DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 90
  DEFAULT CHARSET = utf8 COMMENT ='系统部门表。';

-- 正在导出表  my_database.sys_department 的数据：~2 rows (大约)
DELETE
FROM `sys_department`;
/*!40000 ALTER TABLE `sys_department`
    DISABLE KEYS */;
INSERT INTO `sys_department` (`id`, `parent_id`, `name`, `lft`, `rgt`, `level`, `status`, `is_update`, `description`,
                              `create_time`, `update_time`)
VALUES (1, NULL, 'XX公司', 1, 6, 1, 1, 1, NULL, '2019-10-06 08:42:35', '2020-04-01 11:01:42'),
       (23, 1, '研发部', 2, 3, 2, 1, 1, NULL, '2019-10-16 17:03:00', '2020-04-01 11:01:42');
/*!40000 ALTER TABLE `sys_department`
    ENABLE KEYS */;

-- 导出  表 my_database.sys_department_user 结构
CREATE TABLE IF NOT EXISTS `sys_department_user`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `department_id` bigint(20) unsigned NOT NULL COMMENT '部门ID',
    `user_id`       bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  DEFAULT CHARSET = utf8 COMMENT ='系统部门用户表。';

-- 正在导出表  my_database.sys_department_user 的数据：~3 rows (大约)
DELETE
FROM `sys_department_user`;
/*!40000 ALTER TABLE `sys_department_user`
    DISABLE KEYS */;
INSERT INTO `sys_department_user` (`id`, `department_id`, `user_id`, `create_time`, `update_time`)
VALUES (1, 1, 1, '2019-10-06 08:43:06', '2019-10-18 09:07:39'),
       (25, 23, 27, '2020-03-30 03:36:43', NULL),
       (26, 23, 28, '2020-03-31 11:18:39', '2020-04-01 05:58:46'),
       (30, 23, 32, '2020-09-07 14:16:37', NULL);
/*!40000 ALTER TABLE `sys_department_user`
    ENABLE KEYS */;

-- 导出  表 my_database.sys_dictionary 结构
CREATE TABLE IF NOT EXISTS `sys_dictionary`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id`   bigint(20) unsigned DEFAULT NULL COMMENT '父ID',
    `name`        varchar(255)        NOT NULL COMMENT '字典名称',
    `code`        varchar(255)        NOT NULL COMMENT '字典编码',
    `value`       varchar(255)        NOT NULL COMMENT '字典值',
    `status`      tinyint(3)          NOT NULL COMMENT '字典状态',
    `sort`        int(11)             DEFAULT NULL COMMENT '排序',
    `description` varchar(255)        DEFAULT NULL COMMENT '字典描述',
    `create_time` datetime            DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime            DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='系统字典表。';

-- 正在导出表  my_database.sys_dictionary 的数据：~4 rows (大约)
DELETE
FROM `sys_dictionary`;
/*!40000 ALTER TABLE `sys_dictionary`
    DISABLE KEYS */;
INSERT INTO `sys_dictionary` (`id`, `parent_id`, `name`, `code`, `value`, `status`, `sort`, `description`,
                              `create_time`, `update_time`)
VALUES (10, NULL, '性别', 'sex', '7', 1, NULL, NULL, '2019-10-18 13:09:54', '2019-10-18 13:13:58'),
       (11, 10, '男', 'male', '1', 1, NULL, NULL, '2019-10-18 13:12:56', '2019-10-18 13:14:01'),
       (12, 10, '女', 'female', '2', 1, NULL, NULL, '2019-10-18 13:13:10', '2019-10-18 13:14:01');
/*!40000 ALTER TABLE `sys_dictionary`
    ENABLE KEYS */;

-- 导出  表 my_database.sys_message_content 结构
CREATE TABLE IF NOT EXISTS `sys_message_content`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `title`       varchar(128) DEFAULT NULL COMMENT '站内信标题',
    `content`     varchar(255) DEFAULT NULL COMMENT '站内信内容',
    `type`        tinyint(3)   DEFAULT NULL COMMENT '站内信类型',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 28
  DEFAULT CHARSET = utf8 COMMENT ='系统站内信内容表。';

-- 正在导出表  my_database.sys_message_content 的数据：~0 rows (大约)
DELETE
FROM `sys_message_content`;
/*!40000 ALTER TABLE `sys_message_content`
    DISABLE KEYS */;
INSERT INTO `sys_message_content` (`id`, `title`, `content`, `type`, `create_time`, `update_time`)
VALUES (27, 'test1', NULL, 1, '2020-09-08 15:52:52', NULL);
/*!40000 ALTER TABLE `sys_message_content`
    ENABLE KEYS */;

-- 导出  表 my_database.sys_message_receiver 结构
CREATE TABLE IF NOT EXISTS `sys_message_receiver`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `send_id`     bigint(20) unsigned NOT NULL COMMENT '发信人ID',
    `receive_id`  bigint(20) unsigned NOT NULL COMMENT '收信人ID',
    `content_id`  bigint(20) unsigned NOT NULL COMMENT '站内信ID',
    `status`      int(9)   DEFAULT NULL COMMENT '站内信状态',
    `is_read`     tinyint(1)          NOT NULL COMMENT '是否已读',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='系统站内信收件人表。';

-- 正在导出表  my_database.sys_message_receiver 的数据：~3 rows (大约)
DELETE
FROM `sys_message_receiver`;
/*!40000 ALTER TABLE `sys_message_receiver`
    DISABLE KEYS */;
INSERT INTO `sys_message_receiver` (`id`, `send_id`, `receive_id`, `content_id`, `status`, `is_read`, `create_time`,
                                    `update_time`)
VALUES (1, 1, 1, 11, 1, 0, '2020-06-10 16:08:53', NULL),
       (11, 1, 28, 26, 1, 0, '2020-09-08 00:15:47', NULL),
       (12, 1, 32, 26, 1, 0, '2020-09-08 00:15:47', NULL),
       (13, 1, 32, 27, 1, 0, '2020-09-08 15:52:52', NULL);
/*!40000 ALTER TABLE `sys_message_receiver`
    ENABLE KEYS */;

-- 导出  表 my_database.sys_message_sender 结构
CREATE TABLE IF NOT EXISTS `sys_message_sender`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `send_id`      bigint(20) unsigned NOT NULL COMMENT '发信人ID',
    `receive_id`   bigint(20) unsigned NOT NULL COMMENT '收信人ID',
    `content_id`   bigint(20) unsigned NOT NULL COMMENT '站内信ID',
    `status`       int(9)     DEFAULT NULL COMMENT '站内信状态',
    `is_publish`   tinyint(3) DEFAULT NULL COMMENT '是否发布',
    `publish_time` datetime   DEFAULT NULL COMMENT '发布时间',
    `create_time`  datetime   DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime   DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8 COMMENT ='系统站内信发件人表。';

-- 正在导出表  my_database.sys_message_sender 的数据：~2 rows (大约)
DELETE
FROM `sys_message_sender`;
/*!40000 ALTER TABLE `sys_message_sender`
    DISABLE KEYS */;
INSERT INTO `sys_message_sender` (`id`, `send_id`, `receive_id`, `content_id`, `status`, `is_publish`, `publish_time`,
                                  `create_time`, `update_time`)
VALUES (1, 1, 1, 11, 1, 1, NULL, '2020-06-10 16:08:53', NULL),
       (10, 1, 1, 25, 1, 1, NULL, '2020-09-01 22:43:37', '2020-09-01 22:43:47'),
       (13, 1, 32, 27, 1, 1, '2020-09-08 15:52:52', '2020-09-08 15:52:52', NULL);
/*!40000 ALTER TABLE `sys_message_sender`
    ENABLE KEYS */;

-- 导出  表 my_database.sys_resource 结构
CREATE TABLE IF NOT EXISTS `sys_resource`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id`   bigint(20) unsigned DEFAULT NULL COMMENT '父ID',
    `name`        varchar(255)        NOT NULL COMMENT '资源名称',
    `lft`         int(11) unsigned    NOT NULL COMMENT '资源左值',
    `rgt`         int(11) unsigned    NOT NULL COMMENT '资源右值',
    `level`       int(11) unsigned    NOT NULL COMMENT '资源层级',
    `code`        varchar(255)        NOT NULL COMMENT '资源编码',
    `status`      tinyint(3)          NOT NULL COMMENT '启用状态',
    `is_update`   tinyint(3)          NOT NULL COMMENT '是否可更新',
    `uri`         varchar(50)         DEFAULT NULL COMMENT 'URL',
    `type`        tinyint(3)          NOT NULL COMMENT '类型',
    `method`      varchar(50)         DEFAULT NULL COMMENT '方法',
    `icon`        varchar(50)         DEFAULT NULL COMMENT '图标',
    `description` varchar(255)        DEFAULT NULL COMMENT '权限描述',
    `create_time` datetime            DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime            DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 78
  DEFAULT CHARSET = utf8 COMMENT ='系统资源表。';

-- 正在导出表  my_database.sys_resource 的数据：~74 rows (大约)
DELETE
FROM `sys_resource`;
/*!40000 ALTER TABLE `sys_resource`
    DISABLE KEYS */;
INSERT INTO `sys_resource` (`id`, `parent_id`, `name`, `lft`, `rgt`, `level`, `code`, `status`, `is_update`, `uri`,
                            `type`, `method`, `icon`, `description`, `create_time`, `update_time`)
VALUES (1, NULL, '菜单', 1, 148, 1, 'menuTree', 1, 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52',
        '2020-08-28 00:10:59'),
       (2, 1, '首页', 2, 5, 2, 'home', 1, 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2020-08-28 00:06:10'),
       (3, 2, '获取当前登录用户信息', 3, 4, 3, 'system:user:info', 1, 1, '/api/v1/users/info', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-04-01 15:33:07'),
       (4, 1, '系统配置', 32, 147, 2, 'system', 1, 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52',
        '2020-08-28 00:10:59'),
       (5, 4, '部门管理', 33, 50, 3, 'system:department', 1, 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52',
        '2020-08-28 00:10:50'),
       (6, 5, '获取部门树', 34, 35, 4, 'system:department:tree', 1, 1, '/api/v1/departments', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (7, 5, '新建部门', 36, 37, 4, 'system:department:add', 1, 1, '/api/v1/departments', 2, 'POST', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (8, 5, '移动部门', 38, 39, 4, 'system:department:move', 1, 1, '/api/v1/departments', 2, 'PUT', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (9, 5, '获取部门详情', 40, 41, 4, 'system:department:detail', 1, 1, '/api/v1/departments/*', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (10, 5, '更新部门', 42, 43, 4, 'system:department:update', 1, 1, '/api/v1/departments/*', 2, 'PUT', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (11, 5, '删除部门', 44, 45, 4, 'system:department:delete', 1, 1, '/api/v1/departments/*', 2, 'DELETE', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (12, 5, '获取子部门列表', 46, 47, 4, 'system:department:children', 1, 1, '/api/v1/departments/*/children', 2, 'GET',
        NULL, NULL, '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (13, 5, '启用禁用部门', 48, 49, 4, 'system:department:status', 1, 1, '/api/v1/departments/*/status', 2, 'PATCH', NULL,
        NULL, '2019-10-18 10:23:10', '2020-08-28 00:10:50'),
       (14, 4, '用户管理', 51, 74, 3, 'system:user', 1, 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52',
        '2020-08-28 00:10:50'),
       (15, 14, '获取用户列表', 52, 53, 4, 'system:user:list', 1, 1, '/api/v1/users', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (16, 14, '新建用户', 54, 55, 4, 'system:user:add', 1, 1, '/api/v1/users', 2, 'POST', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (17, 14, '批量删除用户', 56, 57, 4, 'system:user:batchDelete', 1, 1, '/api/v1/users', 2, 'DELETE', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (18, 14, '获取用户详情', 58, 59, 4, 'system:user:detail', 1, 1, '/api/v1/users/*', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (19, 14, '更新用户', 60, 61, 4, 'system:user:update', 1, 1, '/api/v1/users/*', 2, 'PUT', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (20, 14, '删除用户', 62, 63, 4, 'system:user:delete', 1, 1, '/api/v1/users/*', 2, 'DELETE', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (21, 14, '重置用户密码', 64, 65, 4, 'system:user:pwd:reset', 1, 1, '/api/v1/users/*/password', 2, 'PATCH', NULL, NULL,
        '2020-03-29 10:27:40', '2020-08-28 00:10:50'),
       (22, 14, '修改用户密码', 66, 67, 4, 'system:user:pwd:update', 1, 1, '/api/v1/users/*/pwd', 2, 'PATCH', NULL, NULL,
        '2020-03-29 10:28:39', '2020-08-28 00:10:50'),
       (23, 14, '获取用户角色', 68, 69, 4, 'system:user:roles', 1, 1, '/api/v1/users/*/roles', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (24, 14, '赋予用户角色', 70, 71, 4, 'system:user:grant', 1, 1, '/api/v1/users/*/roles', 2, 'POST', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (25, 14, '启用禁用用户', 72, 73, 4, 'system:user:status', 1, 1, '/api/v1/users/*/status', 2, 'PATCH', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (26, 4, '角色管理', 75, 96, 3, 'system:role', 1, 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52',
        '2020-08-28 00:10:50'),
       (27, 26, '获取角色树', 76, 77, 4, 'system:role:tree', 1, 1, '/api/v1/roles', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (28, 26, '新建角色', 78, 79, 4, 'system:role:add', 1, 1, '/api/v1/roles', 2, 'POST', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (29, 26, '移动角色', 80, 81, 4, 'system:role:move', 1, 1, '/api/v1/roles', 2, 'PUT', NULL, NULL,
        '2020-03-29 10:37:33', '2020-08-28 00:10:50'),
       (30, 26, '获取角色详情', 82, 83, 4, 'system:role:detail', 1, 1, '/api/v1/roles/*', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (31, 26, '更新角色', 84, 85, 4, 'system:role:update', 1, 1, '/api/v1/roles/*', 2, 'PUT', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (32, 26, '删除角色', 86, 87, 4, 'system:role:delete', 1, 1, '/api/v1/roles/*', 2, 'DELETE', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (33, 26, '获取子角色列表', 88, 89, 4, 'system:role:children', 1, 1, '/api/v1/roles/*/children', 2, 'GET', NULL, NULL,
        '2020-03-29 10:40:24', '2020-08-28 00:10:50'),
       (34, 26, '获取角色资源', 90, 91, 4, 'system:role:resources', 1, 1, '/api/v1/roles/*/resources', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (35, 26, '赋予角色资源', 92, 93, 4, 'system:role:grant', 1, 1, '/api/v1/roles/*/resources', 2, 'POST', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (36, 26, '启用禁用角色', 94, 95, 4, 'system:role:status', 1, 1, '/api/v1/roles/*/status', 2, 'PATCH', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (37, 4, '菜单管理', 97, 114, 3, 'system:menu', 1, 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52',
        '2020-08-28 00:10:50'),
       (38, 37, '获取菜单树', 98, 99, 4, 'system:menu:tree', 1, 1, '/api/v1/resources', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (39, 37, '新建菜单', 100, 101, 4, 'system:menu:add', 1, 1, '/api/v1/resources', 2, 'POST', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (40, 37, '菜单移动', 102, 103, 4, 'system:menu:move', 1, 1, '/api/v1/resources', 2, 'PUT', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (41, 37, '获取菜单详情', 104, 105, 4, 'system:menu:detail', 1, 1, '/api/v1/resources/*', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (42, 37, '更新菜单', 106, 107, 4, 'system:menu:update', 1, 1, '/api/v1/resources/*', 2, 'PUT', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (43, 37, '删除菜单', 108, 109, 4, 'system:menu:delete', 1, 1, '/api/v1/resources/*', 2, 'DELETE', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (44, 37, '获取子菜单列表', 110, 111, 4, 'system:menu:children', 1, 1, '/api/v1/resources/*/children', 2, 'GET', NULL,
        NULL, '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (45, 37, '启用禁用菜单', 112, 113, 4, 'system:menu:status', 1, 1, '/api/v1/resources/*/status', 2, 'PATCH', NULL, NULL,
        '2019-10-18 10:30:58', '2020-08-28 00:10:50'),
       (46, 4, '接口保护', 115, 130, 3, 'system:api', 1, 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52',
        '2020-08-28 00:10:50'),
       (47, 46, '新建接口', 116, 117, 4, 'system:api:add', 1, 1, '/api/v1/resources', 2, 'POST', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (48, 46, '移动接口', 118, 119, 4, 'system:api:move', 1, 1, '/api/v1/resources', 2, 'PUT', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (49, 46, '获取接口详情', 120, 121, 4, 'system:api:detail', 1, 1, '/api/v1/resources/*', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (50, 46, '更新接口', 122, 123, 4, 'system:api:update', 1, 1, '/api/v1/resources/*', 2, 'PUT', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (51, 46, '删除接口', 124, 125, 4, 'system:api:delete', 1, 1, '/api/v1/resources/*', 2, 'DELETE', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (52, 46, '获取子接口列表', 126, 127, 4, 'system:api:children', 1, 1, '/api/v1/resources/*/children', 2, 'GET', NULL,
        NULL, '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (53, 46, '启用禁用接口', 128, 129, 4, 'system:api:status', 1, 1, '/api/v1/resources/*/status', 2, 'PATCH', NULL, NULL,
        '2019-10-18 10:33:12', '2020-08-28 00:10:50'),
       (54, 4, '字典管理', 131, 146, 3, 'system:dictionary', 1, 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52',
        '2020-08-28 00:10:50'),
       (55, 54, '获取字典列表', 132, 133, 4, 'system:dictionary:list', 1, 1, '/api/v1/dictionaries', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (56, 54, '新建字典', 134, 135, 4, 'system:dictionary:add', 1, 1, '/api/v1/dictionaries', 2, 'POST', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (57, 54, '批量删除字典', 136, 137, 4, 'system:dictionary:batchDelete', 1, 1, '/api/v1/dictionaries', 2, 'DELETE', NULL,
        NULL, '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (58, 54, '获取字典详情', 138, 139, 4, 'system:dictionary:detail', 1, 1, '/api/v1/dictionaries/*', 2, 'GET', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (59, 54, '更新字典', 140, 141, 4, 'system:dictionary:update', 1, 1, '/api/v1/dictionaries/*', 2, 'PUT', NULL, NULL,
        '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (60, 54, '删除字典', 142, 143, 4, 'system:dictionary:delete', 1, 1, '/api/v1/dictionaries/*', 2, 'DELETE', NULL,
        NULL, '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (61, 54, '启用禁用字典', 144, 145, 4, 'system:dictionary:status', 1, 1, '/api/v1/dictionaries/*/status', 2, 'PUT',
        NULL, NULL, '2019-10-07 01:30:52', '2020-08-28 00:10:50'),
       (63, 76, '获取信息列表', 8, 9, 4, 'system:message:list', 1, 1, '/api/v1/messages', 2, 'GET', NULL, NULL,
        '2020-03-29 11:09:36', '2020-08-27 16:08:49'),
       (64, 76, '新建信息', 10, 11, 4, 'system:message:add', 1, 1, '/api/v1/messages', 2, 'POST', NULL, NULL,
        '2020-03-29 11:10:31', '2020-08-27 16:09:03'),
       (65, 76, '批量发布信息', 26, 27, 4, 'system:message:batchPublish', 1, 1, '/api/v1/messages', 2, 'PUT', NULL, NULL,
        '2020-03-29 11:12:49', '2020-08-27 16:10:50'),
       (66, 76, '批量删除信息', 14, 15, 4, 'system:message:batchDelete', 1, 1, '/api/v1/messages', 2, 'DELETE', NULL, NULL,
        '2020-03-29 11:13:27', '2020-08-27 16:09:36'),
       (67, 76, '获取信息详情', 12, 13, 4, 'system:message:detail', 1, 1, '/api/v1/messages/*', 2, 'GET', NULL, NULL,
        '2020-03-29 11:18:23', '2020-08-27 16:09:20'),
       (68, 76, '更新信息', 16, 17, 4, 'system:message:update', 1, 1, '/api/v1/messages/*', 2, 'PUT', NULL, NULL,
        '2020-03-29 11:19:04', '2020-08-27 16:09:49'),
       (69, 76, '删除信息', 18, 19, 4, 'system:message:delete', 1, 1, '/api/v1/messages/*', 2, 'DELETE', NULL, NULL,
        '2020-03-29 11:19:41', '2020-08-27 16:10:00'),
       (70, 76, '发布信息', 20, 21, 4, 'system:message:publish', 1, 1, '/api/v1/messages/*/publish', 2, 'PATCH', NULL, NULL,
        '2020-03-29 11:20:39', '2020-08-27 16:10:15'),
       (71, 76, '启用禁用信息', 22, 23, 4, 'system:message:status', 1, 1, '/api/v1/messages/*/status', 2, 'PATCH', NULL, NULL,
        '2020-03-29 11:21:37', '2020-08-27 16:10:24'),
       (72, 76, '定时发布信息', 24, 25, 4, 'system:message:timer', 1, 1, '/api/v1/messages/timer', 2, 'POST', NULL, NULL,
        '2020-03-30 03:29:19', '2020-08-27 16:10:34'),
       (73, 1, '个人页', 6, 31, 2, 'account', 1, 1, NULL, 1, NULL, NULL, NULL, '2020-08-27 15:55:07',
        '2020-08-28 00:10:50'),
       (76, 73, '个人中心', 7, 28, 3, 'account:center', 1, 1, NULL, 1, NULL, NULL, NULL, '2020-08-27 16:05:34',
        '2020-08-28 00:10:50'),
       (77, 73, '个人设置', 29, 30, 3, 'account:setting', 1, 1, NULL, 1, NULL, NULL, NULL, '2020-08-27 16:05:50',
        '2020-08-28 00:10:50');
/*!40000 ALTER TABLE `sys_resource`
    ENABLE KEYS */;

-- 导出  表 my_database.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `code`        varchar(255)        NOT NULL COMMENT '角色编码',
    `parent_id`   bigint(20)   DEFAULT NULL COMMENT '父ID',
    `name`        varchar(255)        NOT NULL COMMENT '角色名',
    `lft`         int(11)             NOT NULL COMMENT '角色左值',
    `rgt`         int(11)             NOT NULL COMMENT '角色右值',
    `level`       int(11)             NOT NULL COMMENT '角色层级',
    `status`      tinyint(3)          NOT NULL COMMENT '启用状态',
    `is_update`   tinyint(3)          NOT NULL COMMENT '是否可更新',
    `description` varchar(500) DEFAULT NULL COMMENT '角色描述',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 37
  DEFAULT CHARSET = utf8 COMMENT ='系统角色表。';

-- 正在导出表  my_database.sys_role 的数据：~14 rows (大约)
DELETE
FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `code`, `parent_id`, `name`, `lft`, `rgt`, `level`, `status`, `is_update`, `description`,
                        `create_time`, `update_time`)
VALUES (1, 'Administration', NULL, '管理员', 1, 30, 1, 1, 1, '系统管理员，拥有系统所有资源的管理权限。', '2019-10-07 01:16:01',
        '2020-04-01 14:37:25'),
       (2, 'user', 1, '普通角色', 2, 9, 2, 1, 1, '拥有管理员的部分权限。', '2019-10-07 03:02:08', '2020-04-01 14:26:05'),
       (25, 'test', 2, '访客角色', 3, 6, 3, 1, 1, NULL, '2020-03-31 11:08:45', '2020-04-01 07:11:07'),
       (26, 'test1', 1, '测试角色1', 12, 25, 2, 1, 1, NULL, '2020-04-01 06:04:34', '2020-04-01 14:37:25'),
       (27, 'test2', 1, '普通角色2', 26, 27, 2, 1, 1, NULL, '2020-04-01 06:04:43', '2020-04-01 14:37:25'),
       (28, 'test3', 1, '普通角色3', 28, 29, 2, 1, 1, NULL, '2020-04-01 06:04:54', '2020-04-01 14:37:25'),
       (29, '22', 25, '11', 4, 5, 4, 1, 1, NULL, '2020-04-01 07:11:07', NULL),
       (30, '123', 2, '123', 7, 8, 3, 1, 1, NULL, '2020-04-01 14:26:04', NULL),
       (31, '2222', 26, '123123', 13, 24, 3, 1, 1, NULL, '2020-04-01 14:26:18', '2020-04-01 14:37:25'),
       (32, '33333333', 31, '3333333', 14, 23, 4, 1, 1, NULL, '2020-04-01 14:35:57', '2020-04-01 14:37:25'),
       (33, '4444', 32, '4444444', 15, 22, 5, 1, 1, NULL, '2020-04-01 14:36:26', '2020-04-01 14:37:25'),
       (34, '555', 33, '5555', 16, 21, 6, 1, 1, NULL, '2020-04-01 14:36:50', '2020-04-01 14:37:25'),
       (35, '66', 34, '666', 17, 20, 7, 1, 1, NULL, '2020-04-01 14:37:08', '2020-04-01 14:37:25'),
       (36, '77', 35, '77', 18, 19, 8, 1, 1, NULL, '2020-04-01 14:37:24', NULL);
/*!40000 ALTER TABLE `sys_role`
    ENABLE KEYS */;

-- 导出  表 my_database.sys_role_resource 结构
CREATE TABLE IF NOT EXISTS `sys_role_resource`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`     bigint(20) unsigned NOT NULL COMMENT '角色ID',
    `resource_id` bigint(20) unsigned NOT NULL COMMENT '权限ID',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 826
  DEFAULT CHARSET = utf8 COMMENT ='系统角色权限表。';

-- 正在导出表  my_database.sys_role_resource 的数据：~234 rows (大约)
DELETE
FROM `sys_role_resource`;
/*!40000 ALTER TABLE `sys_role_resource`
    DISABLE KEYS */;
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `create_time`, `update_time`)
VALUES (1, 1, 1, '2019-10-07 03:03:38', NULL),
       (2, 1, 2, '2019-10-07 03:03:38', NULL),
       (3, 1, 3, '2019-10-07 03:03:38', NULL),
       (4, 1, 4, '2019-10-07 03:03:38', NULL),
       (5, 1, 5, '2019-10-07 03:03:38', NULL),
       (6, 1, 6, '2019-10-07 03:03:38', NULL),
       (7, 1, 7, '2019-10-07 03:03:38', NULL),
       (8, 1, 8, '2019-10-07 03:03:38', NULL),
       (9, 1, 9, '2019-10-07 03:03:38', NULL),
       (10, 1, 10, '2019-10-07 03:03:38', NULL),
       (11, 1, 11, '2019-10-07 03:03:38', NULL),
       (12, 1, 12, '2019-10-07 03:03:38', NULL),
       (13, 1, 13, '2019-10-07 03:03:38', NULL),
       (14, 1, 14, '2019-10-07 03:03:38', NULL),
       (15, 1, 15, '2019-10-07 03:03:38', NULL),
       (16, 1, 16, '2019-10-07 03:03:38', NULL),
       (17, 1, 17, '2019-10-07 03:03:38', NULL),
       (18, 1, 18, '2019-10-07 03:03:38', NULL),
       (19, 1, 19, '2019-10-07 03:03:38', NULL),
       (20, 1, 20, '2019-10-07 03:03:38', NULL),
       (21, 1, 21, '2019-10-07 03:03:38', NULL),
       (22, 1, 22, '2019-10-07 03:03:38', NULL),
       (23, 1, 23, '2019-10-07 03:03:38', NULL),
       (24, 1, 24, '2019-10-07 03:03:38', NULL),
       (25, 1, 25, '2019-10-07 03:03:38', NULL),
       (26, 1, 26, '2019-10-07 03:03:38', NULL),
       (27, 1, 27, '2019-10-07 03:03:38', NULL),
       (28, 1, 28, '2019-10-07 03:03:38', NULL),
       (30, 1, 30, '2019-10-07 03:03:38', NULL),
       (31, 1, 31, '2019-10-07 03:03:38', NULL),
       (32, 1, 32, '2019-10-07 03:03:38', NULL),
       (33, 1, 33, '2019-10-07 03:03:38', NULL),
       (34, 1, 34, '2019-10-07 03:03:38', NULL),
       (35, 1, 35, '2019-10-07 03:03:38', NULL),
       (36, 1, 36, '2019-10-07 03:03:38', NULL),
       (37, 1, 37, '2019-10-07 03:03:38', NULL),
       (38, 1, 38, '2019-10-07 03:03:38', NULL),
       (39, 1, 39, '2019-10-07 03:03:38', NULL),
       (40, 1, 40, '2019-10-07 03:03:38', NULL),
       (41, 1, 41, '2019-10-07 03:03:38', NULL),
       (42, 1, 42, '2019-10-07 03:03:38', NULL),
       (44, 1, 44, '2019-10-07 03:03:38', NULL),
       (45, 1, 45, '2019-10-07 03:03:38', NULL),
       (46, 1, 46, '2019-10-07 03:03:38', NULL),
       (47, 1, 47, '2019-10-07 03:03:38', NULL),
       (48, 1, 48, '2019-10-07 03:03:38', NULL),
       (49, 1, 49, '2019-10-07 03:03:38', NULL),
       (50, 1, 50, '2019-10-07 03:03:38', NULL),
       (51, 1, 51, '2019-10-07 03:03:38', NULL),
       (52, 1, 52, '2019-10-07 03:03:38', NULL),
       (53, 1, 53, '2019-10-07 03:03:38', NULL),
       (54, 1, 54, '2019-10-07 03:03:38', NULL),
       (55, 1, 55, '2019-10-07 03:03:38', NULL),
       (56, 1, 56, '2019-10-07 03:03:38', NULL),
       (57, 1, 29, '2019-10-07 04:16:21', NULL),
       (58, 1, 43, '2019-10-07 04:16:21', NULL),
       (59, 1, 57, '2019-10-07 04:16:21', NULL),
       (60, 1, 58, '2019-10-07 06:06:21', NULL),
       (61, 1, 59, '2019-10-07 06:06:21', NULL),
       (62, 1, 60, '2019-10-07 06:06:21', NULL),
       (63, 1, 61, '2019-10-07 06:06:21', NULL),
       (66, 1, 63, '2019-10-07 06:06:21', NULL),
       (67, 1, 64, '2019-10-07 06:06:21', NULL),
       (69, 1, 65, '2019-10-07 06:06:21', NULL),
       (70, 1, 66, '2019-10-07 06:06:21', NULL),
       (72, 1, 67, '2019-10-07 06:06:21', NULL),
       (73, 1, 68, '2019-10-07 06:06:21', NULL),
       (74, 1, 69, '2019-10-07 06:06:21', NULL),
       (77, 1, 70, '2019-10-07 06:06:21', NULL),
       (78, 1, 71, '2019-10-07 06:06:21', NULL),
       (559, 1, 72, '2020-03-30 03:29:20', NULL),
       (635, 2, 2, '2020-03-31 08:48:30', NULL),
       (636, 2, 3, '2020-03-31 08:48:30', NULL),
       (637, 2, 5, '2020-03-31 08:48:30', NULL),
       (638, 2, 6, '2020-03-31 08:48:30', NULL),
       (639, 2, 7, '2020-03-31 08:48:30', NULL),
       (640, 2, 8, '2020-03-31 08:48:30', NULL),
       (641, 2, 9, '2020-03-31 08:48:30', NULL),
       (642, 2, 10, '2020-03-31 08:48:30', NULL),
       (643, 2, 11, '2020-03-31 08:48:30', NULL),
       (644, 2, 12, '2020-03-31 08:48:30', NULL),
       (645, 2, 13, '2020-03-31 08:48:30', NULL),
       (646, 2, 14, '2020-03-31 08:48:30', NULL),
       (647, 2, 15, '2020-03-31 08:48:30', NULL),
       (648, 2, 16, '2020-03-31 08:48:30', NULL),
       (649, 2, 17, '2020-03-31 08:48:30', NULL),
       (650, 2, 18, '2020-03-31 08:48:30', NULL),
       (651, 2, 19, '2020-03-31 08:48:30', NULL),
       (652, 2, 20, '2020-03-31 08:48:30', NULL),
       (653, 2, 21, '2020-03-31 08:48:30', NULL),
       (654, 2, 22, '2020-03-31 08:48:30', NULL),
       (655, 2, 23, '2020-03-31 08:48:30', NULL),
       (656, 2, 24, '2020-03-31 08:48:30', NULL),
       (657, 2, 25, '2020-03-31 08:48:30', NULL),
       (658, 25, 2, '2020-03-31 11:18:13', NULL),
       (659, 25, 3, '2020-03-31 11:18:13', NULL),
       (660, 25, 5, '2020-03-31 11:18:13', NULL),
       (661, 25, 6, '2020-03-31 11:18:13', NULL),
       (662, 25, 7, '2020-03-31 11:18:13', NULL),
       (663, 25, 8, '2020-03-31 11:18:13', NULL),
       (664, 25, 9, '2020-03-31 11:18:13', NULL),
       (665, 25, 10, '2020-03-31 11:18:13', NULL),
       (666, 25, 11, '2020-03-31 11:18:13', NULL),
       (667, 25, 12, '2020-03-31 11:18:13', NULL),
       (668, 25, 13, '2020-03-31 11:18:13', NULL),
       (669, 28, 2, '2020-04-01 06:05:04', NULL),
       (670, 28, 3, '2020-04-01 06:05:04', NULL),
       (672, 28, 63, '2020-04-01 06:05:04', NULL),
       (673, 28, 64, '2020-04-01 06:05:04', NULL),
       (674, 28, 65, '2020-04-01 06:05:04', NULL),
       (675, 28, 66, '2020-04-01 06:05:04', NULL),
       (676, 28, 67, '2020-04-01 06:05:04', NULL),
       (677, 28, 68, '2020-04-01 06:05:04', NULL),
       (678, 28, 69, '2020-04-01 06:05:04', NULL),
       (679, 28, 70, '2020-04-01 06:05:04', NULL),
       (680, 28, 71, '2020-04-01 06:05:04', NULL),
       (681, 28, 72, '2020-04-01 06:05:04', NULL),
       (682, 28, 54, '2020-04-01 06:05:04', NULL),
       (683, 28, 55, '2020-04-01 06:05:04', NULL),
       (684, 28, 56, '2020-04-01 06:05:04', NULL),
       (685, 28, 57, '2020-04-01 06:05:04', NULL),
       (686, 28, 58, '2020-04-01 06:05:04', NULL),
       (687, 28, 59, '2020-04-01 06:05:04', NULL),
       (688, 28, 60, '2020-04-01 06:05:04', NULL),
       (689, 28, 61, '2020-04-01 06:05:04', NULL),
       (690, 27, 2, '2020-04-01 06:05:18', NULL),
       (691, 27, 3, '2020-04-01 06:05:18', NULL),
       (692, 27, 46, '2020-04-01 06:05:18', NULL),
       (693, 27, 47, '2020-04-01 06:05:18', NULL),
       (694, 27, 48, '2020-04-01 06:05:18', NULL),
       (695, 27, 49, '2020-04-01 06:05:18', NULL),
       (696, 27, 50, '2020-04-01 06:05:18', NULL),
       (697, 27, 51, '2020-04-01 06:05:18', NULL),
       (698, 27, 52, '2020-04-01 06:05:18', NULL),
       (699, 27, 53, '2020-04-01 06:05:18', NULL),
       (700, 27, 37, '2020-04-01 06:05:18', NULL),
       (701, 27, 38, '2020-04-01 06:05:18', NULL),
       (702, 27, 39, '2020-04-01 06:05:18', NULL),
       (703, 27, 40, '2020-04-01 06:05:18', NULL),
       (704, 27, 41, '2020-04-01 06:05:18', NULL),
       (705, 27, 42, '2020-04-01 06:05:18', NULL),
       (706, 27, 44, '2020-04-01 06:05:18', NULL),
       (707, 27, 45, '2020-04-01 06:05:18', NULL),
       (708, 27, 43, '2020-04-01 06:05:18', NULL),
       (709, 27, 26, '2020-04-01 06:05:18', NULL),
       (710, 27, 27, '2020-04-01 06:05:18', NULL),
       (711, 27, 28, '2020-04-01 06:05:18', NULL),
       (712, 27, 30, '2020-04-01 06:05:18', NULL),
       (713, 27, 31, '2020-04-01 06:05:18', NULL),
       (714, 27, 32, '2020-04-01 06:05:18', NULL),
       (715, 27, 33, '2020-04-01 06:05:18', NULL),
       (716, 27, 34, '2020-04-01 06:05:18', NULL),
       (717, 27, 35, '2020-04-01 06:05:18', NULL),
       (718, 27, 36, '2020-04-01 06:05:18', NULL),
       (719, 27, 29, '2020-04-01 06:05:18', NULL),
       (741, 26, 2, '2020-04-01 06:05:27', NULL),
       (742, 26, 3, '2020-04-01 06:05:27', NULL),
       (743, 2, 26, '2020-04-01 14:24:20', NULL),
       (744, 2, 27, '2020-04-01 14:24:20', NULL),
       (745, 2, 28, '2020-04-01 14:24:20', NULL),
       (746, 2, 30, '2020-04-01 14:24:20', NULL),
       (747, 2, 31, '2020-04-01 14:24:20', NULL),
       (748, 2, 32, '2020-04-01 14:24:20', NULL),
       (749, 2, 33, '2020-04-01 14:24:20', NULL),
       (750, 2, 34, '2020-04-01 14:24:20', NULL),
       (751, 2, 35, '2020-04-01 14:24:20', NULL),
       (752, 2, 36, '2020-04-01 14:24:20', NULL),
       (753, 2, 29, '2020-04-01 14:24:20', NULL),
       (754, 2, 37, '2020-04-01 14:24:20', NULL),
       (755, 2, 38, '2020-04-01 14:24:20', NULL),
       (756, 2, 39, '2020-04-01 14:24:20', NULL),
       (757, 2, 40, '2020-04-01 14:24:20', NULL),
       (758, 2, 41, '2020-04-01 14:24:20', NULL),
       (759, 2, 42, '2020-04-01 14:24:20', NULL),
       (760, 2, 44, '2020-04-01 14:24:20', NULL),
       (761, 2, 45, '2020-04-01 14:24:20', NULL),
       (762, 2, 43, '2020-04-01 14:24:20', NULL),
       (763, 2, 46, '2020-04-01 14:24:20', NULL),
       (764, 2, 47, '2020-04-01 14:24:20', NULL),
       (765, 2, 48, '2020-04-01 14:24:20', NULL),
       (766, 2, 49, '2020-04-01 14:24:20', NULL),
       (767, 2, 50, '2020-04-01 14:24:20', NULL),
       (768, 2, 51, '2020-04-01 14:24:20', NULL),
       (769, 2, 52, '2020-04-01 14:24:20', NULL),
       (770, 2, 53, '2020-04-01 14:24:20', NULL),
       (771, 2, 54, '2020-04-01 14:24:20', NULL),
       (772, 2, 55, '2020-04-01 14:24:20', NULL),
       (773, 2, 56, '2020-04-01 14:24:20', NULL),
       (774, 2, 57, '2020-04-01 14:24:20', NULL),
       (775, 2, 58, '2020-04-01 14:24:20', NULL),
       (776, 2, 59, '2020-04-01 14:24:20', NULL),
       (777, 2, 60, '2020-04-01 14:24:20', NULL),
       (778, 2, 61, '2020-04-01 14:24:20', NULL),
       (780, 2, 63, '2020-04-01 14:24:20', NULL),
       (781, 2, 64, '2020-04-01 14:24:20', NULL),
       (782, 2, 65, '2020-04-01 14:24:20', NULL),
       (783, 2, 66, '2020-04-01 14:24:20', NULL),
       (784, 2, 67, '2020-04-01 14:24:20', NULL),
       (785, 2, 68, '2020-04-01 14:24:20', NULL),
       (786, 2, 69, '2020-04-01 14:24:20', NULL),
       (787, 2, 70, '2020-04-01 14:24:20', NULL),
       (788, 2, 71, '2020-04-01 14:24:20', NULL),
       (789, 2, 72, '2020-04-01 14:24:20', NULL),
       (790, 2, 4, '2020-04-01 14:24:20', NULL),
       (791, 2, 1, '2020-04-01 14:24:20', NULL),
       (792, 26, 5, '2020-04-01 14:35:35', NULL),
       (793, 26, 6, '2020-04-01 14:35:35', NULL),
       (794, 26, 7, '2020-04-01 14:35:35', NULL),
       (795, 26, 8, '2020-04-01 14:35:35', NULL),
       (796, 26, 9, '2020-04-01 14:35:35', NULL),
       (797, 26, 10, '2020-04-01 14:35:35', NULL),
       (798, 26, 11, '2020-04-01 14:35:35', NULL),
       (799, 26, 12, '2020-04-01 14:35:35', NULL),
       (800, 26, 13, '2020-04-01 14:35:35', NULL),
       (801, 31, 2, '2020-04-01 14:35:50', NULL),
       (802, 31, 3, '2020-04-01 14:35:50', NULL),
       (803, 31, 6, '2020-04-01 14:35:50', NULL),
       (804, 31, 8, '2020-04-01 14:35:50', NULL),
       (805, 31, 10, '2020-04-01 14:35:50', NULL),
       (806, 31, 12, '2020-04-01 14:35:50', NULL),
       (807, 31, 13, '2020-04-01 14:35:50', NULL),
       (808, 32, 2, '2020-04-01 14:36:19', NULL),
       (809, 32, 3, '2020-04-01 14:36:19', NULL),
       (810, 32, 6, '2020-04-01 14:36:19', NULL),
       (811, 32, 12, '2020-04-01 14:36:19', NULL),
       (812, 33, 2, '2020-04-01 14:36:35', NULL),
       (813, 33, 3, '2020-04-01 14:36:35', NULL),
       (814, 34, 3, '2020-04-01 14:37:01', NULL),
       (815, 34, 2, '2020-04-01 14:37:01', NULL),
       (816, 35, 3, '2020-04-01 14:37:18', NULL),
       (817, 35, 2, '2020-04-01 14:37:18', NULL),
       (818, 1, 73, '2020-08-27 15:55:07', NULL),
       (821, 1, 76, '2020-08-27 16:05:34', NULL),
       (822, 1, 77, '2020-08-27 16:05:50', NULL),
       (823, 2, 76, '2020-08-27 16:14:28', NULL),
       (824, 2, 77, '2020-08-27 16:14:28', NULL),
       (825, 2, 73, '2020-08-27 16:14:28', NULL);
/*!40000 ALTER TABLE `sys_role_resource`
    ENABLE KEYS */;

-- 导出  表 my_database.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`        varchar(255)        NOT NULL COMMENT '用户名',
    `nickname`        varchar(255) DEFAULT NULL COMMENT '昵称',
    `real_name`       varchar(255) DEFAULT NULL COMMENT '真实姓名',
    `password`        varchar(255)        NOT NULL COMMENT '密码',
    `salt`            varchar(255)        NOT NULL COMMENT '盐',
    `status`          tinyint(3)          NOT NULL COMMENT '启用状态',
    `avatar`          varchar(255) DEFAULT NULL COMMENT '头像',
    `email`           varchar(255) DEFAULT NULL COMMENT '邮箱',
    `phone`           varchar(255) DEFAULT NULL COMMENT '座机',
    `mobile`          varchar(255) DEFAULT NULL COMMENT '电话',
    `sex`             bit(3)       DEFAULT NULL COMMENT '性别',
    `profile`         varchar(255) DEFAULT NULL COMMENT '用户简介',
    `signature`       varchar(255) DEFAULT NULL COMMENT '用户个性签名',
    `address`         varchar(255) DEFAULT NULL COMMENT '用户街道地址',
    `last_login_time` datetime     DEFAULT NULL COMMENT '最后登录时间',
    `create_time`     datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 33
  DEFAULT CHARSET = utf8 COMMENT ='系统用户表。';

-- 正在导出表  my_database.sys_user 的数据：~3 rows (大约)
DELETE
FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user`
    DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `nickname`, `real_name`, `password`, `salt`, `status`, `avatar`, `email`,
                        `phone`, `mobile`, `sex`, `profile`, `signature`, `address`, `last_login_time`, `create_time`,
                        `update_time`)
VALUES (1, 'admin', '管理员', 'hankaibo', '$2a$10$BQNgm4HovoDft9kj93gSgOnV0PNjDwSEEqTTt81Ghd5sgH1zAs5kO',
        '$2a$10$BQNgm4HovoDft9kj93gSgO', 1, 'E:/tmp/upload/images/b74f87f7-f1dc-4b59-959f-315bdbd8dc58.jpeg',
        'hankaibo@mail.com', '+86-12345678', '+86-18612345678', NULL, '我是一条狗。', '胸无点墨，满腹牢骚', '东城区1号', NULL,
        '2019-10-03 00:43:06', '2020-09-01 22:39:06'),
       (27, 'test', NULL, NULL, '$2a$10$XScz.FTdh22uSGMnBrjiLutcKA7tlnuH2RhM9SE9lzUfauv8HC/k6',
        '$2a$10$XScz.FTdh22uSGMnBrjiLu', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-03-30 03:36:43',
        '2020-04-01 12:16:03'),
       (28, 'user', NULL, NULL, '$2a$10$4hC69dCJgAIlZIPYVgSyj.PqMa8qOJ7EgJYlpRsq2GjhS/KHq8si.',
        '$2a$10$4hC69dCJgAIlZIPYVgSyj.', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-03-31 11:18:39',
        '2020-04-01 13:29:52'),
       (32, 'hankaibo', NULL, NULL, '$2a$10$BCLF4kcbYfxKftlnWO.0KuVLCMtrnajV4nCPqD05hJWaxKqbf/37i',
        '$2a$10$BCLF4kcbYfxKftlnWO.0Ku', 1, 'E:/tmp/upload/images/e5f122e5-c0a5-4fd1-a2de-fcca5b7b4ad7.jpg', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, '2020-09-07 14:16:37', '2020-09-08 00:24:48');
/*!40000 ALTER TABLE `sys_user`
    ENABLE KEYS */;

-- 导出  表 my_database.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `role_id`     bigint(20) unsigned NOT NULL COMMENT '角色ID',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  DEFAULT CHARSET = utf8 COMMENT ='系统用户角色表。';

-- 正在导出表  my_database.sys_user_role 的数据：~4 rows (大约)
DELETE
FROM `sys_user_role`;
/*!40000 ALTER TABLE `sys_user_role`
    DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`)
VALUES (1, 1, 1, '2019-10-07 08:19:20', NULL),
       (27, 28, 2, '2020-04-01 03:57:06', NULL),
       (28, 27, 25, '2020-04-01 06:00:55', NULL),
       (29, 28, 26, '2020-04-01 06:07:48', NULL),
       (30, 32, 1, '2020-09-07 14:16:49', NULL);
/*!40000 ALTER TABLE `sys_user_role`
    ENABLE KEYS */;

/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
