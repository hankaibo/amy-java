-- --------------------------------------------------------
-- 主机:                           58.87.121.239
-- 服务器版本:                        10.0.34-MariaDB-0ubuntu0.16.04.1 - Ubuntu 16.04
-- 服务器操作系统:                      debian-linux-gnu
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 test.sys_permission 结构
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `state_code` int(11) DEFAULT NULL COMMENT '启用状态',
  `permission_name` varchar(255) DEFAULT NULL COMMENT '权限名',
  `permission_code` varchar(255) DEFAULT NULL COMMENT '权限编码',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- 数据导出被取消选择。
-- 导出  表 test.sys_role 结构
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `state_code` int(11) DEFAULT NULL COMMENT '启用状态',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `role_code` varchar(255) DEFAULT NULL COMMENT '角色编码',
  `description` varchar(500) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 数据导出被取消选择。
-- 导出  表 test.sys_role_permission 结构
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限关系ID',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- 数据导出被取消选择。
-- 导出  表 test.sys_user 结构
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `state_code` int(11) DEFAULT NULL COMMENT '启用状态',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(255) NOT NULL COMMENT '盐',
  `email` varchar(255) DEFAULT NULL COMMENT 'Email',
  `phone` varchar(255) DEFAULT NULL COMMENT '座机',
  `mobile` varchar(255) DEFAULT NULL COMMENT '电话',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 数据导出被取消选择。
-- 导出  表 test.sys_user_role 结构
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` bigint(20) NOT NULL COMMENT '用户角色关系ID',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
