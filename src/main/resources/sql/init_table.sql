-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.4.13-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 my_database 的数据库结构
CREATE DATABASE IF NOT EXISTS `my_database` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `my_database`;

-- 导出  表 my_database.sys_department 结构
CREATE TABLE IF NOT EXISTS `sys_department` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '父ID',
  `name` varchar(255) NOT NULL COMMENT '部门名称',
  `lft` int(11) unsigned NOT NULL COMMENT '部门左值',
  `rgt` int(11) unsigned NOT NULL COMMENT '部门右值',
  `level` int(11) unsigned NOT NULL COMMENT '部门层级',
  `status` tinyint(3) NOT NULL COMMENT '启用状态',
  `is_update` tinyint(3) NOT NULL COMMENT '是否可更新',
  `description` varchar(255) DEFAULT NULL COMMENT '部门描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='系统部门表。';

-- 数据导出被取消选择。

-- 导出  表 my_database.sys_department_user 结构
CREATE TABLE IF NOT EXISTS `sys_department_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `department_id` bigint(20) unsigned NOT NULL COMMENT '部门ID',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='系统部门用户表。';

-- 数据导出被取消选择。

-- 导出  表 my_database.sys_dictionary 结构
CREATE TABLE IF NOT EXISTS `sys_dictionary` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '父ID',
  `name` varchar(255) NOT NULL COMMENT '字典名称',
  `code` varchar(255) NOT NULL COMMENT '字典编码',
  `value` varchar(255) NOT NULL COMMENT '字典值',
  `status` tinyint(3) NOT NULL COMMENT '字典状态',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `description` varchar(255) DEFAULT NULL COMMENT '字典描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统字典表。';

-- 数据导出被取消选择。

-- 导出  表 my_database.sys_message 结构
CREATE TABLE IF NOT EXISTS `sys_message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `send_id` bigint(20) unsigned NOT NULL COMMENT ' 发信人ID',
  `receive_id` bigint(20) unsigned NOT NULL COMMENT '收信人ID',
  `content_id` bigint(20) unsigned NOT NULL COMMENT '站内信ID',
  `status` int(9) DEFAULT NULL COMMENT '站内信状态',
  `is_publish` tinyint(3) DEFAULT NULL COMMENT '是否发布',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `is_read` tinyint(1) NOT NULL COMMENT '是否已读',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统站内信表。';

-- 数据导出被取消选择。

-- 导出  表 my_database.sys_message_content 结构
CREATE TABLE IF NOT EXISTS `sys_message_content` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(128) DEFAULT NULL COMMENT '站内信标题',
  `content` varchar(255) DEFAULT NULL COMMENT '站内信内容',
  `type` tinyint(3) DEFAULT NULL COMMENT '站内信类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='系统站内信内容表。';

-- 数据导出被取消选择。

-- 导出  表 my_database.sys_resource 结构
CREATE TABLE IF NOT EXISTS `sys_resource` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '父ID',
  `name` varchar(255) NOT NULL COMMENT '资源名称',
  `lft` int(11) unsigned NOT NULL COMMENT '资源左值',
  `rgt` int(11) unsigned NOT NULL COMMENT '资源右值',
  `level` int(11) unsigned NOT NULL COMMENT '资源层级',
  `code` varchar(255) NOT NULL COMMENT '资源编码',
  `status` tinyint(3) NOT NULL COMMENT '启用状态',
  `is_update` tinyint(3) NOT NULL COMMENT '是否可更新',
  `uri` varchar(50) DEFAULT NULL COMMENT 'URL',
  `type` tinyint(3) NOT NULL COMMENT '类型',
  `method` varchar(50) DEFAULT NULL COMMENT '方法',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COMMENT='系统资源表。';

-- 数据导出被取消选择。

-- 导出  表 my_database.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(255) NOT NULL COMMENT '角色编码',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父ID',
  `name` varchar(255) NOT NULL COMMENT '角色名',
  `lft` int(11) NOT NULL COMMENT '角色左值',
  `rgt` int(11) NOT NULL COMMENT '角色右值',
  `level` int(11) NOT NULL COMMENT '角色层级',
  `status` tinyint(3) NOT NULL COMMENT '启用状态',
  `is_update` tinyint(3) NOT NULL COMMENT '是否可更新',
  `description` varchar(500) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='系统角色表。';

-- 数据导出被取消选择。

-- 导出  表 my_database.sys_role_resource 结构
CREATE TABLE IF NOT EXISTS `sys_role_resource` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `resource_id` bigint(20) unsigned NOT NULL COMMENT '权限ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=818 DEFAULT CHARSET=utf8 COMMENT='系统角色权限表。';

-- 数据导出被取消选择。

-- 导出  表 my_database.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(255) NOT NULL COMMENT '盐',
  `status` tinyint(3) NOT NULL COMMENT '启用状态',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) DEFAULT NULL COMMENT '座机',
  `mobile` varchar(255) DEFAULT NULL COMMENT '电话',
  `sex` bit(3) DEFAULT NULL COMMENT '性别',
  `profile` varchar(255) DEFAULT NULL COMMENT '用户简介',
  `signature` varchar(255) DEFAULT NULL COMMENT '用户个性签名',
  `address` varchar(255) DEFAULT NULL COMMENT '用户街道地址',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='系统用户表。';

-- 数据导出被取消选择。

-- 导出  表 my_database.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='系统用户角色表。';

-- 数据导出被取消选择。

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
