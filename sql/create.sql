-- --------------------------------------------------------
-- 主机:                           58.87.121.239
-- 服务器版本:                        10.0.34-MariaDB-0ubuntu0.16.04.1 - Ubuntu 16.04
-- 服务器OS:                        debian-linux-gnu
-- HeidiSQL 版本:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for hankaibo
CREATE DATABASE IF NOT EXISTS `hankaibo` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `hankaibo`;

-- Dumping structure for table hankaibo.sys_dict
CREATE TABLE IF NOT EXISTS `sys_dict` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级字典id',
  `name` varchar(255) DEFAULT NULL COMMENT '字典名称',
  `code` varchar(255) DEFAULT NULL COMMENT '字典编码',
  `status` int(11) DEFAULT NULL COMMENT '字典状态',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `description` varchar(255) DEFAULT NULL COMMENT '字典描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `modify_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典表';

-- Dumping data for table hankaibo.sys_dict: ~0 rows (大约)
DELETE FROM `sys_dict`;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` (`id`, `parent_id`, `name`, `code`, `status`, `sort`, `description`, `create_time`, `modify_time`, `create_user`, `modify_user`) VALUES
	(109, -1, '性别', 'sex', 1, NULL, NULL, '2019-07-24 06:32:30', NULL, NULL, NULL),
	(110, 109, '男', 'male', 1, NULL, NULL, '2019-07-24 06:33:53', NULL, NULL, NULL),
	(111, 109, '女', 'female', 1, NULL, NULL, '2019-07-24 06:34:06', NULL, NULL, NULL);
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;

-- Dumping structure for table hankaibo.sys_resource
CREATE TABLE IF NOT EXISTS `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名',
  `lft` bigint(20) DEFAULT NULL COMMENT '节点左值',
  `rgt` bigint(20) DEFAULT NULL COMMENT '节点右值',
  `level` bigint(20) DEFAULT NULL COMMENT '节点层级',
  `code` varchar(255) DEFAULT NULL COMMENT '权限编码',
  `status` int(11) DEFAULT NULL COMMENT '启用状态',
  `uri` varchar(50) DEFAULT NULL COMMENT 'uri',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `method` varchar(50) DEFAULT NULL COMMENT '方法',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- Dumping data for table hankaibo.sys_resource: ~1 rows (大约)
DELETE FROM `sys_resource`;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` (`id`, `name`, `lft`, `rgt`, `level`, `code`, `status`, `uri`, `type`, `method`, `icon`, `description`, `parent_id`, `create_time`, `modify_time`) VALUES
	(20, '根菜单', 1, 82, 1, 'root', 1, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL),
	(80, '首页', 2, 5, 2, NULL, 1, NULL, 1, NULL, NULL, NULL, 20, NULL, NULL),
	(81, '系统配置', 6, 81, 2, NULL, 1, NULL, 1, NULL, NULL, NULL, 20, NULL, NULL),
	(82, '获取当前登录用户信息', 3, 4, 3, NULL, 1, '/api/v1/users/info', 2, 'GET', NULL, NULL, 80, NULL, '2019-07-24 08:30:08'),
	(84, '用户管理', 7, 26, 3, NULL, 1, NULL, 1, NULL, NULL, NULL, 81, NULL, NULL),
	(85, '角色管理', 27, 46, 3, NULL, 1, NULL, 1, NULL, NULL, NULL, 81, NULL, NULL),
	(86, '菜单管理', 47, 62, 3, NULL, 1, NULL, 1, NULL, NULL, NULL, 81, NULL, NULL),
	(87, '接口保护', 63, 64, 3, NULL, 1, NULL, 1, NULL, NULL, NULL, 81, NULL, NULL),
	(88, '字典管理', 65, 80, 3, NULL, 1, NULL, 1, NULL, NULL, NULL, 81, NULL, NULL),
	(89, '用户列表', 8, 9, 4, NULL, 1, '/api/v1/users', 2, 'GET', NULL, NULL, 84, NULL, NULL),
	(90, '新建用户', 10, 11, 4, NULL, 1, '/api/v1/users', 2, 'POST', NULL, NULL, 84, NULL, NULL),
	(91, '删除用户（批量）', 12, 13, 4, NULL, 1, '/api/v1/users', 2, 'DELETE', NULL, NULL, 84, NULL, NULL),
	(92, '用户详情', 14, 15, 4, NULL, 1, '/api/v1/users/*', 2, 'GET', NULL, NULL, 84, NULL, NULL),
	(93, '更新用户', 16, 17, 4, NULL, 1, '/api/v1/users/*', 2, 'PUT', NULL, NULL, 84, NULL, NULL),
	(94, '删除用户', 18, 19, 4, NULL, 1, '/api/v1/users/*', 2, 'DELETE', NULL, NULL, 84, NULL, NULL),
	(95, '查询用户的所有角色', 22, 23, 4, NULL, 1, '/api/v1/users/*/roles', 2, 'GET', NULL, NULL, 84, NULL, NULL),
	(96, '赋予用户一些角色', 20, 21, 4, NULL, 1, '/api/v1/users/*/roles', 2, 'POST', NULL, NULL, 84, NULL, NULL),
	(97, '启用|禁用用户', 24, 25, 4, NULL, 1, '/api/v1/users/*/status', 2, 'PUT', NULL, NULL, 84, NULL, NULL),
	(98, '角色列表', 28, 29, 4, NULL, 1, '/api/v1/roles', 2, 'GET', NULL, NULL, 85, NULL, NULL),
	(99, '新建角色', 30, 31, 4, NULL, 1, '/api/v1/roles', 2, 'POST', NULL, NULL, 85, NULL, NULL),
	(100, '删除角色(批量)', 32, 33, 4, NULL, 1, '/api/v1/roles', 2, 'DELETE', NULL, NULL, 85, NULL, NULL),
	(101, '角色详情', 34, 35, 4, NULL, 1, '/api/v1/roles/*', 2, 'GET', NULL, NULL, 85, NULL, NULL),
	(102, '更新角色', 36, 37, 4, NULL, 1, '/api/v1/roles/*', 2, 'PUT', NULL, NULL, 85, NULL, NULL),
	(103, '删除角色', 38, 39, 4, NULL, 1, '/api/v1/roles/*', 2, 'DELETE', NULL, NULL, 85, NULL, NULL),
	(104, '查询角色的所有资源', 44, 45, 4, NULL, 1, '/api/v1/roles/*/resources', 2, 'GET', NULL, NULL, 85, NULL, NULL),
	(105, '赋予角色一些资源', 40, 41, 4, NULL, 1, '/api/v1/roles/*/resources', 2, 'POST', NULL, NULL, 85, NULL, NULL),
	(106, '启用|禁用角色', 42, 43, 4, NULL, 1, '/api/v1/roles/*/status', 2, 'PUT', NULL, NULL, 85, NULL, NULL),
	(107, '查询资源', 48, 49, 4, NULL, 1, '/api/v1/resources', 2, 'GET', NULL, NULL, 86, NULL, NULL),
	(108, '新建资源', 50, 51, 4, NULL, 1, '/api/v1/resources', 2, 'POST', NULL, NULL, 86, NULL, NULL),
	(109, '资源详情', 52, 53, 4, NULL, 1, '/api/v1/resources/*', 2, 'GET', NULL, NULL, 86, NULL, NULL),
	(110, '更新资源', 54, 55, 4, NULL, 1, '/api/v1/resources/*', 2, 'PUT', NULL, NULL, 86, NULL, NULL),
	(111, '删除资源', 56, 57, 4, NULL, 1, '/api/v1/resources/*', 2, 'DELETE', NULL, NULL, 86, NULL, NULL),
	(112, '查询子资源', 58, 59, 4, NULL, 1, '/api/v1/resources/*/children', 2, 'GET', NULL, NULL, 86, NULL, NULL),
	(113, '移动资源', 60, 61, 4, NULL, 1, '/api/v1/resources/*/location', 2, 'PUT', NULL, NULL, 86, NULL, NULL),
	(114, '字典列表', 66, 67, 4, NULL, 1, '/api/v1/dicts', 2, 'GET', NULL, NULL, 88, NULL, NULL),
	(115, '新建字典', 68, 69, 4, NULL, 1, '/api/v1/dicts', 2, 'POST', NULL, NULL, 88, NULL, NULL),
	(116, '删除字典（批量）', 70, 71, 4, NULL, 1, '/api/v1/dicts', 2, 'DELETE', NULL, NULL, 88, NULL, NULL),
	(117, '字典详情', 72, 73, 4, NULL, 1, '/api/v1/dicts/*', 2, 'GET', NULL, NULL, 88, NULL, NULL),
	(118, '更新字典', 74, 75, 4, NULL, 1, '/api/v1/dicts/*', 2, 'PUT', NULL, NULL, 88, NULL, NULL),
	(119, '删除字典', 76, 77, 4, NULL, 1, '/api/v1/dicts/*', 2, 'DELETE', NULL, NULL, 88, NULL, NULL),
	(120, '启用|禁用字典', 78, 79, 4, NULL, 1, '/api/v1/dicts/*/status', 2, 'PUT', NULL, NULL, 88, NULL, NULL);
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;

-- Dumping structure for table hankaibo.sys_role
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `code` varchar(255) DEFAULT NULL COMMENT '角色编码',
  `status` int(11) DEFAULT NULL COMMENT '启用状态',
  `description` varchar(500) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- Dumping data for table hankaibo.sys_role: ~1 rows (大约)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `name`, `code`, `status`, `description`, `create_time`, `modify_time`) VALUES
	(1, '管理员', 'admin', 1, '管理员', '2019-06-21 19:20:40', '2019-06-21 19:20:42'),
	(10, '普通用户', 'user', 1, NULL, '2019-07-24 06:34:35', NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- Dumping structure for table hankaibo.sys_role_resource
CREATE TABLE IF NOT EXISTS `sys_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限关系ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `resource_id` bigint(20) NOT NULL COMMENT '权限ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=180 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- Dumping data for table hankaibo.sys_role_resource: ~0 rows (大约)
DELETE FROM `sys_role_resource`;
/*!40000 ALTER TABLE `sys_role_resource` DISABLE KEYS */;
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `create_time`, `modify_time`) VALUES
	(126, 1, 20, NULL, NULL),
	(127, 1, 80, NULL, NULL),
	(128, 1, 81, NULL, NULL),
	(129, 1, 82, NULL, NULL),
	(130, 1, 83, NULL, NULL),
	(131, 1, 84, NULL, NULL),
	(132, 1, 85, NULL, NULL),
	(133, 1, 86, NULL, NULL),
	(134, 1, 87, NULL, NULL),
	(135, 1, 88, NULL, NULL),
	(136, 1, 89, NULL, NULL),
	(137, 1, 90, NULL, NULL),
	(138, 1, 91, NULL, NULL),
	(139, 1, 92, NULL, NULL),
	(140, 1, 93, NULL, NULL),
	(141, 1, 94, NULL, NULL),
	(142, 1, 95, NULL, NULL),
	(143, 1, 96, NULL, NULL),
	(144, 1, 97, NULL, NULL),
	(145, 1, 98, NULL, NULL),
	(146, 1, 99, NULL, NULL),
	(147, 1, 100, NULL, NULL),
	(148, 1, 101, NULL, NULL),
	(149, 1, 102, NULL, NULL),
	(150, 1, 103, NULL, NULL),
	(151, 1, 104, NULL, NULL),
	(152, 1, 105, NULL, NULL),
	(153, 1, 106, NULL, NULL),
	(154, 1, 107, NULL, NULL),
	(155, 1, 108, NULL, NULL),
	(156, 1, 109, NULL, NULL),
	(157, 1, 110, NULL, NULL),
	(158, 1, 111, NULL, NULL),
	(159, 1, 112, NULL, NULL),
	(160, 1, 113, NULL, NULL),
	(161, 1, 114, NULL, NULL),
	(162, 1, 115, NULL, NULL),
	(163, 1, 116, NULL, NULL),
	(164, 1, 117, NULL, NULL),
	(165, 1, 118, NULL, NULL),
	(166, 1, 119, NULL, NULL),
	(167, 1, 120, NULL, NULL),
	(170, 10, 80, NULL, NULL),
	(171, 10, 82, NULL, NULL),
	(172, 10, 88, NULL, NULL),
	(173, 10, 114, NULL, NULL),
	(174, 10, 115, NULL, NULL),
	(175, 10, 116, NULL, NULL),
	(176, 10, 117, NULL, NULL),
	(177, 10, 118, NULL, NULL),
	(178, 10, 119, NULL, NULL),
	(179, 10, 120, NULL, NULL);
/*!40000 ALTER TABLE `sys_role_resource` ENABLE KEYS */;

-- Dumping structure for table hankaibo.sys_user
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(255) NOT NULL COMMENT '盐',
  `status` int(11) DEFAULT NULL COMMENT '启用状态',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(255) DEFAULT NULL COMMENT 'Email',
  `phone` varchar(255) DEFAULT NULL COMMENT '座机',
  `mobile` varchar(255) DEFAULT NULL COMMENT '电话',
  `sex` bit(3) DEFAULT NULL COMMENT '性别',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- Dumping data for table hankaibo.sys_user: ~1 rows (大约)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `nickname`, `real_name`, `password`, `salt`, `status`, `avatar`, `email`, `phone`, `mobile`, `sex`, `last_login_time`, `create_time`, `modify_time`) VALUES
	(16, 'admin', '管理员', '管理员', '$2a$10$zeeu/HxJCUZY1xFgHNI1wOUhkrOKc353YOZozlYjcIAPYQZz/VYN.', '$2a$10$zeeu/HxJCUZY1xFgHNI1wO', 1, NULL, NULL, NULL, NULL, NULL, NULL, '2019-07-22 08:57:06', '2019-07-24 06:38:44'),
	(27, 'hh', NULL, NULL, '$2a$10$dJP6VeSiuJ0ZD8ZR4wxFH.UnY/uP9rVl0y2OZHpdlrSXhEciKQ.RK', '$2a$10$dJP6VeSiuJ0ZD8ZR4wxFH.', 1, NULL, NULL, NULL, NULL, NULL, NULL, '2019-07-24 06:39:08', NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- Dumping structure for table hankaibo.sys_user_role
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色关系ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- Dumping data for table hankaibo.sys_user_role: ~1 rows (大约)
DELETE FROM `sys_user_role`;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `modify_time`) VALUES
	(20, 16, 1, NULL, NULL),
	(23, 27, 10, NULL, NULL);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
