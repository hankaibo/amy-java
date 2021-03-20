-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.13-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping data for table my_database.sys_department: ~3 rows (approximately)
DELETE FROM `sys_department`;
/*!40000 ALTER TABLE `sys_department` DISABLE KEYS */;
INSERT INTO `sys_department` (`id`, `parent_id`, `name`, `lft`, `rgt`, `level`, `status`, `is_update`, `description`, `create_time`, `update_time`) VALUES
	(1, NULL, '根', 1, 6, 1, 'ENABLED', 1, NULL, '2021-03-20 17:52:15', '2021-03-20 22:19:17'),
	(95, 1, '研发部', 2, 3, 2, 'ENABLED', 1, NULL, '2021-03-20 17:55:37', '2021-03-20 19:15:11'),
	(96, 1, '测试部', 4, 5, 2, 'ENABLED', 1, NULL, '2021-03-20 17:57:08', '2021-03-21 01:26:24');
/*!40000 ALTER TABLE `sys_department` ENABLE KEYS */;

-- Dumping data for table my_database.sys_department_user: ~3 rows (approximately)
DELETE FROM `sys_department_user`;
/*!40000 ALTER TABLE `sys_department_user` DISABLE KEYS */;
INSERT INTO `sys_department_user` (`id`, `department_id`, `user_id`, `create_time`, `update_time`) VALUES
	(1, 1, 1, '2019-10-06 08:43:06', '2019-10-18 09:07:39'),
	(30, 95, 32, '2020-09-07 14:16:37', NULL),
	(37, 95, 39, '2020-12-12 14:39:37', NULL);
/*!40000 ALTER TABLE `sys_department_user` ENABLE KEYS */;

-- Dumping data for table my_database.sys_dictionary: ~3 rows (approximately)
DELETE FROM `sys_dictionary`;
/*!40000 ALTER TABLE `sys_dictionary` DISABLE KEYS */;
INSERT INTO `sys_dictionary` (`id`, `parent_id`, `name`, `code`, `value`, `status`, `sort`, `description`, `create_time`, `update_time`) VALUES
	(10, NULL, '性别', 'sex', '7', 'ENABLED', NULL, NULL, '2019-10-18 13:09:54', '2021-03-21 01:37:07'),
	(11, 10, '男', 'male', '1', 'ENABLED', NULL, NULL, '2019-10-18 13:12:56', '2019-10-18 13:14:01'),
	(12, 10, '女', 'female', '2', 'ENABLED', NULL, NULL, '2019-10-18 13:13:10', '2019-10-18 13:14:01');
/*!40000 ALTER TABLE `sys_dictionary` ENABLE KEYS */;

-- Dumping data for table my_database.sys_message_content: ~0 rows (approximately)
DELETE FROM `sys_message_content`;
/*!40000 ALTER TABLE `sys_message_content` DISABLE KEYS */;
INSERT INTO `sys_message_content` (`id`, `title`, `content`, `type`, `create_time`, `update_time`) VALUES
	(27, 'test1', NULL, 'NOTIFICATION', '2020-09-08 15:52:52', NULL);
/*!40000 ALTER TABLE `sys_message_content` ENABLE KEYS */;

-- Dumping data for table my_database.sys_message_receiver: ~4 rows (approximately)
DELETE FROM `sys_message_receiver`;
/*!40000 ALTER TABLE `sys_message_receiver` DISABLE KEYS */;
INSERT INTO `sys_message_receiver` (`id`, `send_id`, `receive_id`, `content_id`, `status`, `is_read`, `create_time`, `update_time`) VALUES
	(1, 1, 1, 11, 'ENABLED', 0, '2020-06-10 16:08:53', NULL),
	(11, 1, 28, 26, 'ENABLED', 0, '2020-09-08 00:15:47', NULL),
	(12, 1, 32, 26, 'ENABLED', 0, '2020-09-08 00:15:47', NULL),
	(13, 1, 32, 27, 'ENABLED', 0, '2020-09-08 15:52:52', NULL);
/*!40000 ALTER TABLE `sys_message_receiver` ENABLE KEYS */;

-- Dumping data for table my_database.sys_message_sender: ~3 rows (approximately)
DELETE FROM `sys_message_sender`;
/*!40000 ALTER TABLE `sys_message_sender` DISABLE KEYS */;
INSERT INTO `sys_message_sender` (`id`, `send_id`, `receive_id`, `content_id`, `status`, `is_publish`, `publish_time`, `create_time`, `update_time`) VALUES
	(1, 1, 1, 11, 'ENABLED', 1, NULL, '2020-06-10 16:08:53', NULL),
	(10, 1, 1, 25, 'ENABLED', 1, NULL, '2020-09-01 22:43:37', '2020-09-01 22:43:47'),
	(13, 1, 32, 27, 'ENABLED', 1, '2020-09-08 15:52:52', '2020-09-08 15:52:52', NULL);
/*!40000 ALTER TABLE `sys_message_sender` ENABLE KEYS */;

-- Dumping data for table my_database.sys_resource: ~74 rows (approximately)
DELETE FROM `sys_resource`;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` (`id`, `parent_id`, `name`, `lft`, `rgt`, `level`, `code`, `status`, `is_update`, `uri`, `type`, `method`, `icon`, `description`, `create_time`, `update_time`) VALUES
	(1, NULL, '菜单', 1, 148, 1, 'menuTree', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(2, 1, '首页', 2, 5, 2, 'home', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2021-03-21 01:36:31'),
	(3, 2, '获取当前登录用户信息', 3, 4, 3, 'system:user:info', 'DISABLED', 1, '/api/v1/users/info', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-03-21 01:36:31'),
	(4, 1, '系统配置', 32, 147, 2, 'system', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(5, 4, '部门管理', 33, 50, 3, 'system:department', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(6, 5, '获取部门树', 34, 35, 4, 'system:department:tree', 'ENABLED', 1, '/api/v1/departments', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(7, 5, '新建部门', 36, 37, 4, 'system:department:add', 'ENABLED', 1, '/api/v1/departments', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(8, 5, '移动部门', 38, 39, 4, 'system:department:move', 'ENABLED', 1, '/api/v1/departments', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(9, 5, '获取部门详情', 40, 41, 4, 'system:department:detail', 'ENABLED', 1, '/api/v1/departments/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(10, 5, '更新部门', 42, 43, 4, 'system:department:update', 'ENABLED', 1, '/api/v1/departments/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(11, 5, '删除部门', 44, 45, 4, 'system:department:delete', 'ENABLED', 1, '/api/v1/departments/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(12, 5, '获取子部门列表', 46, 47, 4, 'system:department:children', 'ENABLED', 1, '/api/v1/departments/*/children', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(13, 5, '启用禁用部门', 48, 49, 4, 'system:department:status', 'ENABLED', 1, '/api/v1/departments/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-18 10:23:10', '2020-12-06 22:51:16'),
	(14, 4, '用户管理', 51, 74, 3, 'system:user', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(15, 14, '获取用户列表', 52, 53, 4, 'system:user:list', 'ENABLED', 1, '/api/v1/users', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(16, 14, '新建用户', 54, 55, 4, 'system:user:add', 'ENABLED', 1, '/api/v1/users', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(17, 14, '批量删除用户', 56, 57, 4, 'system:user:batchDelete', 'ENABLED', 1, '/api/v1/users', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(18, 14, '获取用户详情', 58, 59, 4, 'system:user:detail', 'ENABLED', 1, '/api/v1/users/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(19, 14, '更新用户', 60, 61, 4, 'system:user:update', 'ENABLED', 1, '/api/v1/users/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(20, 14, '删除用户', 62, 63, 4, 'system:user:delete', 'ENABLED', 1, '/api/v1/users/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(21, 14, '重置用户密码', 64, 65, 4, 'system:user:pwd:reset', 'ENABLED', 1, '/api/v1/users/*/password', 'API', 'PATCH', NULL, NULL, '2020-03-29 10:27:40', '2020-12-06 22:51:16'),
	(22, 14, '修改用户密码', 66, 67, 4, 'system:user:pwd:update', 'ENABLED', 1, '/api/v1/users/*/pwd', 'API', 'PATCH', NULL, NULL, '2020-03-29 10:28:39', '2020-12-06 22:51:16'),
	(23, 14, '获取用户角色', 68, 69, 4, 'system:user:roles', 'ENABLED', 1, '/api/v1/users/*/roles', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(24, 14, '赋予用户角色', 70, 71, 4, 'system:user:grant', 'ENABLED', 1, '/api/v1/users/*/roles', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(25, 14, '启用禁用用户', 72, 73, 4, 'system:user:status', 'ENABLED', 1, '/api/v1/users/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(26, 4, '角色管理', 75, 96, 3, 'system:role', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(27, 26, '获取角色树', 76, 77, 4, 'system:role:tree', 'ENABLED', 1, '/api/v1/roles', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(28, 26, '新建角色', 78, 79, 4, 'system:role:add', 'ENABLED', 1, '/api/v1/roles', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(29, 26, '移动角色', 80, 81, 4, 'system:role:move', 'ENABLED', 1, '/api/v1/roles', 'API', 'PUT', NULL, NULL, '2020-03-29 10:37:33', '2020-12-06 22:51:16'),
	(30, 26, '获取角色详情', 82, 83, 4, 'system:role:detail', 'ENABLED', 1, '/api/v1/roles/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(31, 26, '更新角色', 84, 85, 4, 'system:role:update', 'ENABLED', 1, '/api/v1/roles/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(32, 26, '删除角色', 86, 87, 4, 'system:role:delete', 'ENABLED', 1, '/api/v1/roles/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(33, 26, '获取子角色列表', 88, 89, 4, 'system:role:children', 'ENABLED', 1, '/api/v1/roles/*/children', 'API', 'GET', NULL, NULL, '2020-03-29 10:40:24', '2020-12-06 22:51:16'),
	(34, 26, '获取角色资源', 90, 91, 4, 'system:role:resources', 'ENABLED', 1, '/api/v1/roles/*/resources', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(35, 26, '赋予角色资源', 92, 93, 4, 'system:role:grant', 'ENABLED', 1, '/api/v1/roles/*/resources', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(36, 26, '启用禁用角色', 94, 95, 4, 'system:role:status', 'ENABLED', 1, '/api/v1/roles/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(37, 4, '菜单管理', 97, 114, 3, 'system:menu', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(38, 37, '获取菜单树', 98, 99, 4, 'system:menu:tree', 'ENABLED', 1, '/api/v1/resources', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(39, 37, '新建菜单', 100, 101, 4, 'system:menu:add', 'ENABLED', 1, '/api/v1/resources', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(40, 37, '菜单移动', 102, 103, 4, 'system:menu:move', 'ENABLED', 1, '/api/v1/resources', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(41, 37, '获取菜单详情', 104, 105, 4, 'system:menu:detail', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(42, 37, '更新菜单', 106, 107, 4, 'system:menu:update', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(43, 37, '删除菜单', 108, 109, 4, 'system:menu:delete', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(44, 37, '获取子菜单列表', 110, 111, 4, 'system:menu:children', 'ENABLED', 1, '/api/v1/resources/*/children', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(45, 37, '启用禁用菜单', 112, 113, 4, 'system:menu:status', 'ENABLED', 1, '/api/v1/resources/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-18 10:30:58', '2020-12-06 22:51:16'),
	(46, 4, '接口保护', 115, 130, 3, 'system:api', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(47, 46, '新建接口', 116, 117, 4, 'system:api:add', 'ENABLED', 1, '/api/v1/resources', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(48, 46, '移动接口', 118, 119, 4, 'system:api:move', 'ENABLED', 1, '/api/v1/resources', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(49, 46, '获取接口详情', 120, 121, 4, 'system:api:detail', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(50, 46, '更新接口', 122, 123, 4, 'system:api:update', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(51, 46, '删除接口', 124, 125, 4, 'system:api:delete', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(52, 46, '获取子接口列表', 126, 127, 4, 'system:api:children', 'ENABLED', 1, '/api/v1/resources/*/children', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(53, 46, '启用禁用接口', 128, 129, 4, 'system:api:status', 'ENABLED', 1, '/api/v1/resources/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-18 10:33:12', '2020-12-06 22:51:16'),
	(54, 4, '字典管理', 131, 146, 3, 'system:dictionary', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(55, 54, '获取字典列表', 132, 133, 4, 'system:dictionary:list', 'ENABLED', 1, '/api/v1/dictionaries', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(56, 54, '新建字典', 134, 135, 4, 'system:dictionary:add', 'ENABLED', 1, '/api/v1/dictionaries', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(57, 54, '批量删除字典', 136, 137, 4, 'system:dictionary:batchDelete', 'ENABLED', 1, '/api/v1/dictionaries', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(58, 54, '获取字典详情', 138, 139, 4, 'system:dictionary:detail', 'ENABLED', 1, '/api/v1/dictionaries/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(59, 54, '更新字典', 140, 141, 4, 'system:dictionary:update', 'ENABLED', 1, '/api/v1/dictionaries/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(60, 54, '删除字典', 142, 143, 4, 'system:dictionary:delete', 'ENABLED', 1, '/api/v1/dictionaries/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(61, 54, '启用禁用字典', 144, 145, 4, 'system:dictionary:status', 'ENABLED', 1, '/api/v1/dictionaries/*/status', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2020-12-06 22:51:16'),
	(63, 76, '获取信息列表', 8, 9, 4, 'system:message:list', 'DISABLED', 1, '/api/v1/messages', 'API', 'GET', NULL, NULL, '2020-03-29 11:09:36', '2021-03-21 01:36:42'),
	(64, 76, '新建信息', 10, 11, 4, 'system:message:add', 'DISABLED', 1, '/api/v1/messages', 'API', 'POST', NULL, NULL, '2020-03-29 11:10:31', '2021-03-21 01:36:42'),
	(65, 76, '批量发布信息', 26, 27, 4, 'system:message:batchPublish', 'DISABLED', 1, '/api/v1/messages', 'API', 'PUT', NULL, NULL, '2020-03-29 11:12:49', '2021-03-21 01:36:42'),
	(66, 76, '批量删除信息', 14, 15, 4, 'system:message:batchDelete', 'DISABLED', 1, '/api/v1/messages', 'API', 'DELETE', NULL, NULL, '2020-03-29 11:13:27', '2021-03-21 01:36:42'),
	(67, 76, '获取信息详情', 12, 13, 4, 'system:message:detail', 'DISABLED', 1, '/api/v1/messages/*', 'API', 'GET', NULL, NULL, '2020-03-29 11:18:23', '2021-03-21 01:36:42'),
	(68, 76, '更新信息', 16, 17, 4, 'system:message:update', 'DISABLED', 1, '/api/v1/messages/*', 'API', 'PUT', NULL, NULL, '2020-03-29 11:19:04', '2021-03-21 01:36:42'),
	(69, 76, '删除信息', 18, 19, 4, 'system:message:delete', 'DISABLED', 1, '/api/v1/messages/*', 'API', 'DELETE', NULL, NULL, '2020-03-29 11:19:41', '2021-03-21 01:36:42'),
	(70, 76, '发布信息', 20, 21, 4, 'system:message:publish', 'DISABLED', 1, '/api/v1/messages/*/publish', 'API', 'PATCH', NULL, NULL, '2020-03-29 11:20:39', '2021-03-21 01:36:42'),
	(71, 76, '启用禁用信息', 22, 23, 4, 'system:message:status', 'DISABLED', 1, '/api/v1/messages/*/status', 'API', 'PATCH', NULL, NULL, '2020-03-29 11:21:37', '2021-03-21 01:36:42'),
	(72, 76, '定时发布信息', 24, 25, 4, 'system:message:timer', 'DISABLED', 1, '/api/v1/messages/timer', 'API', 'POST', NULL, NULL, '2020-03-30 03:29:19', '2021-03-21 01:36:42'),
	(73, 1, '个人页', 6, 31, 2, 'account', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2020-08-27 15:55:07', '2021-03-21 01:36:42'),
	(76, 73, '个人中心', 7, 28, 3, 'account:center', 'DISABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2020-08-27 16:05:34', '2021-03-21 01:36:42'),
	(77, 73, '个人设置', 29, 30, 3, 'account:setting', 'DISABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2020-08-27 16:05:50', '2021-03-21 01:36:42');
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;

-- Dumping data for table my_database.sys_role: ~5 rows (approximately)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `code`, `parent_id`, `name`, `lft`, `rgt`, `level`, `status`, `is_update`, `description`, `create_time`, `update_time`) VALUES
	(1, 'Administration', NULL, '管理员', 1, 10, 1, 'ENABLED', 1, '系统管理员，拥有系统所有资源的管理权限。', '2019-10-07 01:16:01', '2020-12-06 22:30:31'),
	(37, 'user1', 1, '普通角色', 2, 3, 2, 'ENABLED', 1, '只拥有个人相关的权限。', '2020-11-03 00:35:34', '2021-03-21 01:26:54'),
	(38, 'user2', 1, '部门管理员', 4, 5, 2, 'ENABLED', 1, '拥有部门与用户管理相关的所有权限。', '2020-11-03 00:35:41', '2020-12-12 14:36:30'),
	(39, 'user3', 1, '审查角色', 6, 7, 2, 'ENABLED', 1, '只拥有查看权限。', '2020-11-03 00:35:48', '2020-12-12 14:38:51'),
	(40, 'user4', 1, '测试角色', 8, 9, 2, 'ENABLED', 1, '测试只拥有用户权限没有部门权限的情况。', '2020-11-03 00:35:56', '2020-12-12 14:50:49');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- Dumping data for table my_database.sys_role_resource: ~196 rows (approximately)
DELETE FROM `sys_role_resource`;
/*!40000 ALTER TABLE `sys_role_resource` DISABLE KEYS */;
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `create_time`, `update_time`) VALUES
	(1, 1, 1, '2019-10-07 03:03:38', NULL),
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
	(843, 37, 2, '2020-12-12 14:33:36', NULL),
	(844, 37, 3, '2020-12-12 14:33:36', NULL),
	(845, 37, 73, '2020-12-12 14:33:36', NULL),
	(846, 37, 76, '2020-12-12 14:33:36', NULL),
	(847, 37, 77, '2020-12-12 14:33:36', NULL),
	(848, 37, 63, '2020-12-12 14:33:36', NULL),
	(849, 37, 64, '2020-12-12 14:33:36', NULL),
	(850, 37, 65, '2020-12-12 14:33:36', NULL),
	(851, 37, 66, '2020-12-12 14:33:36', NULL),
	(852, 37, 67, '2020-12-12 14:33:36', NULL),
	(853, 37, 68, '2020-12-12 14:33:36', NULL),
	(854, 37, 69, '2020-12-12 14:33:36', NULL),
	(855, 37, 70, '2020-12-12 14:33:36', NULL),
	(856, 37, 71, '2020-12-12 14:33:36', NULL),
	(857, 37, 72, '2020-12-12 14:33:36', NULL),
	(858, 38, 2, '2020-12-12 14:35:53', NULL),
	(859, 38, 3, '2020-12-12 14:35:53', NULL),
	(860, 38, 73, '2020-12-12 14:35:53', NULL),
	(861, 38, 76, '2020-12-12 14:35:53', NULL),
	(862, 38, 77, '2020-12-12 14:35:53', NULL),
	(863, 38, 63, '2020-12-12 14:35:53', NULL),
	(864, 38, 64, '2020-12-12 14:35:53', NULL),
	(865, 38, 65, '2020-12-12 14:35:53', NULL),
	(866, 38, 66, '2020-12-12 14:35:53', NULL),
	(867, 38, 67, '2020-12-12 14:35:53', NULL),
	(868, 38, 68, '2020-12-12 14:35:53', NULL),
	(869, 38, 69, '2020-12-12 14:35:53', NULL),
	(870, 38, 70, '2020-12-12 14:35:53', NULL),
	(871, 38, 71, '2020-12-12 14:35:53', NULL),
	(872, 38, 72, '2020-12-12 14:35:53', NULL),
	(873, 38, 5, '2020-12-12 14:35:53', NULL),
	(874, 38, 6, '2020-12-12 14:35:53', NULL),
	(875, 38, 7, '2020-12-12 14:35:53', NULL),
	(876, 38, 8, '2020-12-12 14:35:53', NULL),
	(877, 38, 9, '2020-12-12 14:35:53', NULL),
	(878, 38, 10, '2020-12-12 14:35:53', NULL),
	(879, 38, 11, '2020-12-12 14:35:53', NULL),
	(880, 38, 12, '2020-12-12 14:35:53', NULL),
	(881, 38, 13, '2020-12-12 14:35:53', NULL),
	(882, 38, 14, '2020-12-12 14:35:53', NULL),
	(883, 38, 15, '2020-12-12 14:35:53', NULL),
	(884, 38, 16, '2020-12-12 14:35:53', NULL),
	(885, 38, 17, '2020-12-12 14:35:53', NULL),
	(886, 38, 18, '2020-12-12 14:35:53', NULL),
	(887, 38, 19, '2020-12-12 14:35:53', NULL),
	(888, 38, 20, '2020-12-12 14:35:53', NULL),
	(889, 38, 21, '2020-12-12 14:35:53', NULL),
	(890, 38, 22, '2020-12-12 14:35:53', NULL),
	(891, 38, 23, '2020-12-12 14:35:53', NULL),
	(892, 38, 24, '2020-12-12 14:35:53', NULL),
	(893, 38, 25, '2020-12-12 14:35:53', NULL),
	(894, 39, 2, '2020-12-12 14:38:11', NULL),
	(895, 39, 3, '2020-12-12 14:38:11', NULL),
	(896, 39, 73, '2020-12-12 14:38:11', NULL),
	(897, 39, 76, '2020-12-12 14:38:11', NULL),
	(898, 39, 77, '2020-12-12 14:38:11', NULL),
	(899, 39, 63, '2020-12-12 14:38:11', NULL),
	(900, 39, 64, '2020-12-12 14:38:11', NULL),
	(901, 39, 65, '2020-12-12 14:38:11', NULL),
	(902, 39, 66, '2020-12-12 14:38:11', NULL),
	(903, 39, 67, '2020-12-12 14:38:11', NULL),
	(904, 39, 68, '2020-12-12 14:38:11', NULL),
	(905, 39, 69, '2020-12-12 14:38:11', NULL),
	(906, 39, 70, '2020-12-12 14:38:11', NULL),
	(907, 39, 71, '2020-12-12 14:38:11', NULL),
	(908, 39, 72, '2020-12-12 14:38:11', NULL),
	(909, 39, 6, '2020-12-12 14:38:11', NULL),
	(910, 39, 12, '2020-12-12 14:38:11', NULL),
	(911, 39, 58, '2020-12-12 14:38:11', NULL),
	(912, 39, 55, '2020-12-12 14:38:11', NULL),
	(913, 39, 52, '2020-12-12 14:38:11', NULL),
	(914, 39, 44, '2020-12-12 14:38:11', NULL),
	(915, 39, 38, '2020-12-12 14:38:11', NULL),
	(916, 39, 27, '2020-12-12 14:38:11', NULL),
	(917, 39, 33, '2020-12-12 14:38:11', NULL),
	(918, 39, 15, '2020-12-12 14:38:11', NULL),
	(919, 39, 23, '2020-12-12 14:38:11', NULL),
	(920, 40, 2, '2020-12-12 14:51:00', NULL),
	(921, 40, 3, '2020-12-12 14:51:00', NULL),
	(922, 40, 73, '2020-12-12 14:51:00', NULL),
	(923, 40, 76, '2020-12-12 14:51:00', NULL),
	(924, 40, 77, '2020-12-12 14:51:00', NULL),
	(925, 40, 63, '2020-12-12 14:51:00', NULL),
	(926, 40, 64, '2020-12-12 14:51:00', NULL),
	(927, 40, 65, '2020-12-12 14:51:00', NULL),
	(928, 40, 66, '2020-12-12 14:51:00', NULL),
	(929, 40, 67, '2020-12-12 14:51:00', NULL),
	(930, 40, 68, '2020-12-12 14:51:00', NULL),
	(931, 40, 69, '2020-12-12 14:51:00', NULL),
	(932, 40, 70, '2020-12-12 14:51:00', NULL),
	(933, 40, 71, '2020-12-12 14:51:00', NULL),
	(934, 40, 72, '2020-12-12 14:51:00', NULL),
	(935, 40, 14, '2020-12-12 14:51:00', NULL),
	(936, 40, 15, '2020-12-12 14:51:00', NULL),
	(937, 40, 16, '2020-12-12 14:51:00', NULL),
	(938, 40, 17, '2020-12-12 14:51:00', NULL),
	(939, 40, 18, '2020-12-12 14:51:00', NULL),
	(940, 40, 19, '2020-12-12 14:51:00', NULL),
	(941, 40, 20, '2020-12-12 14:51:00', NULL),
	(942, 40, 21, '2020-12-12 14:51:00', NULL),
	(943, 40, 22, '2020-12-12 14:51:00', NULL),
	(944, 40, 23, '2020-12-12 14:51:00', NULL),
	(945, 40, 24, '2020-12-12 14:51:00', NULL),
	(946, 40, 25, '2020-12-12 14:51:00', NULL);
/*!40000 ALTER TABLE `sys_role_resource` ENABLE KEYS */;

-- Dumping data for table my_database.sys_user: ~3 rows (approximately)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `nickname`, `real_name`, `password`, `salt`, `status`, `avatar`, `email`, `phone`, `mobile`, `sex`, `profile`, `signature`, `address`, `last_login_time`, `create_time`, `update_time`) VALUES
	(1, 'admin', '管理员', 'hankaibo', '$2a$10$BQNgm4HovoDft9kj93gSgOnV0PNjDwSEEqTTt81Ghd5sgH1zAs5kO', '$2a$10$BQNgm4HovoDft9kj93gSgO', 'ENABLED', 'E:/tmp/upload/images/b74f87f7-f1dc-4b59-959f-315bdbd8dc58.jpeg', 'hankaibo@mail.com', '+86-12345678', '+86-18612345678', NULL, '我是一条狗。', '胸无点墨，满腹牢骚', '东城区1号', NULL, '2019-10-03 00:43:06', '2021-03-21 01:26:38'),
	(32, 'hankaibo', NULL, NULL, '$2a$10$BCLF4kcbYfxKftlnWO.0KuVLCMtrnajV4nCPqD05hJWaxKqbf/37i', '$2a$10$BCLF4kcbYfxKftlnWO.0Ku', 'ENABLED', 'E:/tmp/upload/images/e5f122e5-c0a5-4fd1-a2de-fcca5b7b4ad7.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-09-07 14:16:37', '2020-09-08 00:24:48'),
	(39, 'hp', NULL, NULL, '$2a$10$lKjqhVHXx/E/ayrnHjd2m.IzcL10xA4RHGb2Do.89FTpcqt3Ti39e', '$2a$10$lKjqhVHXx/E/ayrnHjd2m.', 'ENABLED', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-12-12 14:39:37', '2021-03-20 23:52:06');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- Dumping data for table my_database.sys_user_role: ~4 rows (approximately)
DELETE FROM `sys_user_role`;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`) VALUES
	(1, 1, 1, '2019-10-07 08:19:20', NULL),
	(39, 32, 40, '2020-12-12 14:51:37', NULL),
	(40, 32, 38, '2020-12-12 16:33:12', NULL),
	(41, 39, 40, '2021-03-20 23:52:18', NULL);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
