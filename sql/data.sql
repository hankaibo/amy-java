-- --------------------------------------------------------
-- 主机:                           58.87.121.239
-- 服务器版本:                        10.4.7-MariaDB-1:10.4.7+maria~bionic - mariadb.org binary distribution
-- 服务器OS:                        debian-linux-gnu
-- HeidiSQL 版本:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping data for table my_database.sys_department: ~1 rows (大约)
DELETE FROM `sys_department`;
/*!40000 ALTER TABLE `sys_department` DISABLE KEYS */;
INSERT INTO `sys_department` (`id`, `parent_id`, `name`, `lft`, `rgt`, `level`, `status`, `description`, `create_time`, `update_time`) VALUES
	(1, NULL, 'XX公司', 1, 2, 1, 1, NULL, '2019-10-06 08:42:35', NULL);
/*!40000 ALTER TABLE `sys_department` ENABLE KEYS */;

-- Dumping data for table my_database.sys_department_user: ~1 rows (大约)
DELETE FROM `sys_department_user`;
/*!40000 ALTER TABLE `sys_department_user` DISABLE KEYS */;
INSERT INTO `sys_department_user` (`id`, `department_id`, `user_id`, `create_time`, `update_time`) VALUES
	(1, 1, 1, '2019-10-06 08:43:06', NULL),
	(2, 1, 2, '2019-10-07 05:17:35', NULL);
/*!40000 ALTER TABLE `sys_department_user` ENABLE KEYS */;

-- Dumping data for table my_database.sys_dictionary: ~0 rows (大约)
DELETE FROM `sys_dictionary`;
/*!40000 ALTER TABLE `sys_dictionary` DISABLE KEYS */;
INSERT INTO `sys_dictionary` (`id`, `parent_id`, `name`, `code`, `value`, `status`, `sort`, `description`, `create_time`, `update_time`) VALUES
	(1, NULL, '性别', 'sex', '101', 1, NULL, '系统码表，性别。', '2019-10-07 04:18:41', NULL),
	(2, 1, '男', 'male', '1', 1, NULL, NULL, '2019-10-07 04:44:21', NULL),
	(3, 1, '女', 'female', '2', 1, NULL, NULL, '2019-10-07 04:44:43', NULL);
/*!40000 ALTER TABLE `sys_dictionary` ENABLE KEYS */;

-- Dumping data for table my_database.sys_resource: ~56 rows (大约)
DELETE FROM `sys_resource`;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` (`id`, `parent_id`, `name`, `lft`, `rgt`, `level`, `code`, `status`, `uri`, `type`, `method`, `icon`, `description`, `create_time`, `update_time`) VALUES
	(1, NULL, '菜单', 1, 112, 1, 'menuTree', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', NULL),
	(2, 1, '首页', 2, 5, 2, 'home', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', NULL),
	(3, 1, '系统配置', 6, 111, 2, 'system', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', NULL),
	(4, 3, '部门管理', 7, 22, 3, 'system.department', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-06 15:21:32'),
	(5, 3, '用户管理', 23, 42, 3, 'system.user', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-06 15:21:42'),
	(6, 3, '角色管理', 43, 62, 3, 'system.role', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-06 15:21:50'),
	(7, 3, '菜单管理', 63, 78, 3, 'system.menu', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-06 15:21:58'),
	(8, 3, '资源保护', 79, 94, 3, 'system.resource', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-06 15:22:05'),
	(9, 3, '字典管理', 95, 110, 3, 'system.dictionary', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-06 15:22:13'),
	(10, 2, '获取当前登录用户信息', 3, 4, 3, 'system.user.info', 1, '/api/v1/users/info', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(11, 4, '部门列表', 8, 9, 4, 'system.department.list', 1, '/api/v1/departments', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(12, 4, '部门新建', 10, 11, 4, 'system.department.add', 1, '/api/v1/departments', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', '2019-10-07 04:10:31'),
	(13, 4, '部门详情', 12, 13, 4, 'system.department.detail', 1, '/api/v1/departments/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(14, 4, '部门更新', 14, 15, 4, 'system.department.update', 1, '/api/v1/departments/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(15, 4, '部门删除', 16, 17, 4, 'system.department.delete', 1, '/api/v1/departments/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(16, 4, '子部门列表', 18, 19, 4, 'system.department.children', 1, '/api/v1/departments/*/children', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(17, 4, '部门移动', 20, 21, 4, 'system.department.move', 1, '/api/v1/departments/*/location', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(18, 5, '用户列表', 24, 25, 4, 'system.user.list', 1, '/api/v1/users', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(19, 5, '用户新建', 26, 27, 4, 'system.user.add', 1, '/api/v1/users', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(20, 5, '用户删除（批量）', 28, 29, 4, 'system.user.batchDelete', 1, '/api/v1/users', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(21, 5, '用户详情', 30, 31, 4, 'system.user.detail', 1, '/api/v1/users/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(22, 5, '用户更新', 32, 33, 4, 'system.user.update', 1, '/api/v1/users/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(23, 5, '用户删除', 34, 35, 4, 'system.user.delete', 1, '/api/v1/users/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(24, 5, '用户所有角色', 36, 37, 4, 'system.user.roles', 1, '/api/v1/users/*/roles', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(25, 5, '赋予用户角色', 38, 39, 4, 'system.user.grant', 1, '/api/v1/users/*/roles', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(26, 5, '用户状态', 40, 41, 4, 'system.user.status', 1, '/api/v1/users/*/status', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(27, 6, '角色列表', 44, 45, 4, 'system.role.list', 1, '/api/v1/roles', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(28, 6, '角色新建', 46, 47, 4, 'system.role.add', 1, '/api/v1/roles', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(29, 6, '角色删除（批量）', 48, 49, 4, 'system.role.batchDelete', 1, '/api/v1/roles', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(30, 6, '角色详情', 50, 51, 4, 'system.role.detail', 1, '/api/v1/roles/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(31, 6, '角色更新', 52, 53, 4, 'system.role.update', 1, '/api/v1/roles/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(32, 6, '角色删除', 54, 55, 4, 'system.role.detele', 1, '/api/v1/roles/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(33, 6, '角色所有资源', 56, 57, 4, 'system.role.resources', 1, '/api/v1/roles/*/resources', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(34, 6, '赋予角色资源', 58, 59, 4, 'system.role.grant', 1, '/api/v1/roles/*/resources', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(35, 6, '角色状态', 60, 61, 4, 'system.role.status', 1, '/api/v1/roles/*/status', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(36, 7, '菜单列表', 64, 65, 4, 'system.menu.list', 1, '/api/v1/resources', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(37, 7, '菜单新建', 66, 67, 4, 'system.menu.add', 1, '/api/v1/resources', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(38, 7, '菜单详情', 68, 69, 4, 'system.menu.detail', 1, '/api/v1/resources/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(39, 7, '菜单更新', 70, 71, 4, 'system.menu.update', 1, '/api/v1/resources/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(40, 7, '菜单删除', 72, 73, 4, 'system.menu.delete', 1, '/api/v1/resources/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(41, 7, '子菜单列表', 74, 75, 4, 'system.menu.children', 1, '/api/v1/resources/*/children', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(42, 7, '菜单移动', 76, 77, 4, 'system.menu.move', 1, '/api/v1/resources/*/location', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(43, 8, '资源列表', 80, 81, 4, 'system.resource.list', 1, '/api/v1/resources', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(44, 8, '资源新建', 82, 83, 4, 'system.resource.add', 1, '/api/v1/resources', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(45, 8, '资源详情', 84, 85, 4, 'system.resource.detail', 1, '/api/v1/resources/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(46, 8, '资源更新', 86, 87, 4, 'system.resource.update', 1, '/api/v1/resources/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(47, 8, '资源删除', 88, 89, 4, 'system.resource.delete', 1, '/api/v1/resources/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(48, 8, '子资源列表', 90, 91, 4, 'system.resource.children', 1, '/api/v1/resources/*/children', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(49, 8, '资源移动', 92, 93, 4, 'system.resource.move', 1, '/api/v1/resources/*/location', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(50, 9, '字典列表', 96, 97, 4, 'system.dictionary.list', 1, '/api/v1/dictionaries', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(51, 9, '字典新建', 98, 99, 4, 'system.dictionary.add', 1, '/api/v1/dictionaries', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(52, 9, '字典删除（批量）', 100, 101, 4, 'system.dictionary.batchDelete', 1, '/api/v1/dictionaries', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(53, 9, '字典详情', 102, 103, 4, 'system.dictionary.detail', 1, '/api/v1/dictionaries/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(54, 9, '字典更新', 104, 105, 4, 'system.dictionary.update', 1, '/api/v1/dictionaries/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(55, 9, '字典删除', 106, 107, 4, 'system.dictionary.detele', 1, '/api/v1/dictionaries/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(56, 9, '字典状态', 108, 109, 4, 'system.dictionary.status', 1, '/api/v1/dictionaries/*/status', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', NULL);
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;

-- Dumping data for table my_database.sys_role: ~2 rows (大约)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `name`, `code`, `status`, `description`, `create_time`, `update_time`) VALUES
	(1, '管理员', 'Administration', 1, '系统管理员，拥有系统所有资源的管理权限。', '2019-10-07 01:16:01', NULL),
	(2, '普通角色', 'user', 1, '只有首页的操作权限。', '2019-10-07 03:02:08', '2019-10-07 04:16:11');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- Dumping data for table my_database.sys_role_resource: ~59 rows (大约)
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
	(29, 1, 29, '2019-10-07 03:03:38', NULL),
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
	(43, 1, 43, '2019-10-07 03:03:38', NULL),
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
	(57, 2, 2, '2019-10-07 04:16:21', NULL),
	(58, 2, 10, '2019-10-07 04:16:21', NULL),
	(59, 2, 1, '2019-10-07 04:16:21', NULL);
/*!40000 ALTER TABLE `sys_role_resource` ENABLE KEYS */;

-- Dumping data for table my_database.sys_user: ~1 rows (大约)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `nickname`, `real_name`, `password`, `salt`, `status`, `avatar`, `email`, `phone`, `mobile`, `sex`, `last_login_time`, `create_time`, `update_time`) VALUES
	(1, 'admin', '管理员', NULL, '$2a$10$BQNgm4HovoDft9kj93gSgOnV0PNjDwSEEqTTt81Ghd5sgH1zAs5kO', '$2a$10$BQNgm4HovoDft9kj93gSgO', 1, NULL, NULL, NULL, NULL, NULL, NULL, '2019-10-06 08:43:06', '2019-10-07 05:18:35'),
	(2, 'test', '测试用户', NULL, '$2a$10$RUrZV0lxdBkratQfQw7wLe3SgPhx9AoZrGnVGcqp/iDbtAOMhDOvS', '$2a$10$RUrZV0lxdBkratQfQw7wLe', 1, NULL, NULL, NULL, NULL, NULL, NULL, '2019-10-07 05:17:35', '2019-10-07 05:17:58');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- Dumping data for table my_database.sys_user_role: ~1 rows (大约)
DELETE FROM `sys_user_role`;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`) VALUES
	(1, 1, 1, '2019-10-07 08:19:20', NULL),
	(2, 2, 2, '2019-10-07 05:18:07', NULL);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
