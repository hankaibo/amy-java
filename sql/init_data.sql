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

-- Dumping data for table my_database.sys_department: ~7 rows (大约)
DELETE FROM `sys_department`;
/*!40000 ALTER TABLE `sys_department` DISABLE KEYS */;
INSERT INTO `sys_department` (`id`, `parent_id`, `name`, `lft`, `rgt`, `level`, `status`, `description`, `create_time`, `update_time`) VALUES
	(1, NULL, 'XX公司', 1, 14, 1, 1, NULL, '2019-10-06 08:42:35', '2019-10-18 09:06:35'),
	(23, 1, '研发部', 4, 13, 2, 1, NULL, '2019-10-16 17:03:00', '2019-10-18 09:06:35'),
	(27, 23, '研发一部', 5, 6, 3, 0, '后台。', '2019-10-16 17:04:55', '2019-10-18 11:02:27'),
	(28, 23, '研发二部', 7, 8, 3, 1, '前台。', '2019-10-16 17:05:36', '2019-10-18 09:06:17'),
	(30, 1, '财务部', 2, 3, 2, 0, NULL, '2019-10-17 05:10:28', '2019-10-18 09:05:29'),
	(31, 23, '研发三部', 9, 10, 3, 0, '设计。', '2019-10-18 02:24:16', '2019-10-18 11:02:28'),
	(32, 23, '研发四部', 11, 12, 3, 1, '测试。\n', '2019-10-18 09:06:35', '2019-10-18 09:06:56');
/*!40000 ALTER TABLE `sys_department` ENABLE KEYS */;

-- Dumping data for table my_database.sys_department_user: ~5 rows (大约)
DELETE FROM `sys_department_user`;
/*!40000 ALTER TABLE `sys_department_user` DISABLE KEYS */;
INSERT INTO `sys_department_user` (`id`, `department_id`, `user_id`, `create_time`, `update_time`) VALUES
	(1, 23, 1, '2019-10-06 08:43:06', '2019-10-18 09:07:39'),
	(2, 32, 2, '2019-10-07 05:17:35', '2019-10-18 09:07:15'),
	(3, 28, 3, '2019-10-07 06:02:20', NULL),
	(4, 28, 4, '2019-10-07 06:02:37', NULL),
	(5, 28, 5, '2019-10-07 06:03:00', NULL);
/*!40000 ALTER TABLE `sys_department_user` ENABLE KEYS */;

-- Dumping data for table my_database.sys_dictionary: ~3 rows (大约)
DELETE FROM `sys_dictionary`;
/*!40000 ALTER TABLE `sys_dictionary` DISABLE KEYS */;
INSERT INTO `sys_dictionary` (`id`, `parent_id`, `name`, `code`, `value`, `status`, `sort`, `description`, `create_time`, `update_time`) VALUES
	(10, NULL, '性别', 'sex', '7', 1, NULL, NULL, '2019-10-18 13:09:54', '2019-10-18 13:13:58'),
	(11, 10, '男', 'male', '1', 1, NULL, NULL, '2019-10-18 13:12:56', '2019-10-18 13:14:01'),
	(12, 10, '女', 'female', '2', 1, NULL, NULL, '2019-10-18 13:13:10', '2019-10-18 13:14:01');
/*!40000 ALTER TABLE `sys_dictionary` ENABLE KEYS */;

-- Dumping data for table my_database.sys_resource: ~59 rows (大约)
DELETE FROM `sys_resource`;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` (`id`, `parent_id`, `name`, `lft`, `rgt`, `level`, `code`, `status`, `uri`, `type`, `method`, `icon`, `description`, `create_time`, `update_time`) VALUES
	(1, NULL, '菜单', 1, 118, 1, 'menuTree', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:33:12'),
	(2, 1, '首页', 2, 5, 2, 'home', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-14 09:08:43'),
	(3, 1, '系统配置', 6, 117, 2, 'system', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:33:12'),
	(4, 3, '部门管理', 7, 24, 3, 'system.department', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(5, 3, '用户管理', 25, 44, 3, 'system.user', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(6, 3, '角色管理', 45, 64, 3, 'system.role', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(7, 3, '菜单管理', 65, 82, 3, 'system.menu', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:30:58'),
	(8, 3, '接口保护', 83, 100, 3, 'system.api', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:40:04'),
	(9, 3, '字典管理', 101, 116, 3, 'system.dictionary', 1, NULL, 1, NULL, NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:33:12'),
	(10, 2, '获取当前登录用户信息', 3, 4, 3, 'system.user.info', 1, '/api/v1/users/info', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(11, 4, '部门列表', 8, 9, 4, 'system.department.list', 1, '/api/v1/departments', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', NULL),
	(12, 4, '部门新建', 10, 11, 4, 'system.department.add', 1, '/api/v1/departments', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', '2019-10-07 04:10:31'),
	(13, 4, '部门详情', 14, 15, 4, 'system.department.detail', 1, '/api/v1/departments/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:20:06'),
	(14, 4, '部门更新', 16, 17, 4, 'system.department.update', 1, '/api/v1/departments/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:20:04'),
	(15, 4, '部门删除', 18, 19, 4, 'system.department.delete', 1, '/api/v1/departments/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:20:03'),
	(16, 4, '子部门列表', 22, 23, 4, 'system.department.children', 1, '/api/v1/departments/*/children', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:13'),
	(17, 4, '部门移动', 12, 13, 4, 'system.department.move', 1, '/api/v1/departments', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:20:06'),
	(18, 5, '用户列表', 26, 27, 4, 'system.user.list', 1, '/api/v1/users', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:24:27'),
	(19, 5, '用户新建', 28, 29, 4, 'system.user.add', 1, '/api/v1/users', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:24:27'),
	(20, 5, '用户删除（批量）', 30, 31, 4, 'system.user.batchDelete', 1, '/api/v1/users', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:24:26'),
	(21, 5, '用户详情', 32, 33, 4, 'system.user.detail', 1, '/api/v1/users/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(22, 5, '用户更新', 34, 35, 4, 'system.user.update', 1, '/api/v1/users/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(23, 5, '用户删除', 36, 37, 4, 'system.user.delete', 1, '/api/v1/users/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(24, 5, '用户所有角色', 40, 41, 4, 'system.user.roles', 1, '/api/v1/users/*/roles', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:24:54'),
	(25, 5, '赋予用户角色', 42, 43, 4, 'system.user.grant', 1, '/api/v1/users/*/roles', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:24:52'),
	(26, 5, '用户状态', 38, 39, 4, 'system.user.status', 1, '/api/v1/users/*', 2, 'PATCH', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:25:09'),
	(27, 6, '角色列表', 46, 47, 4, 'system.role.list', 1, '/api/v1/roles', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(28, 6, '角色新建', 48, 49, 4, 'system.role.add', 1, '/api/v1/roles', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(29, 6, '角色删除（批量）', 50, 51, 4, 'system.role.batchDelete', 1, '/api/v1/roles', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(30, 6, '角色详情', 52, 53, 4, 'system.role.detail', 1, '/api/v1/roles/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(31, 6, '角色更新', 54, 55, 4, 'system.role.update', 1, '/api/v1/roles/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(32, 6, '角色删除', 56, 57, 4, 'system.role.delete', 1, '/api/v1/roles/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(33, 6, '角色所有资源', 60, 61, 4, 'system.role.resources', 1, '/api/v1/roles/*/resources', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:26:53'),
	(34, 6, '赋予角色资源', 62, 63, 4, 'system.role.grant', 1, '/api/v1/roles/*/resources', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:26:51'),
	(35, 6, '角色状态', 58, 59, 4, 'system.role.status', 1, '/api/v1/roles/*', 2, 'PATCH', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:27:05'),
	(36, 7, '菜单列表', 66, 67, 4, 'system.menu.list', 1, '/api/v1/resources', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(37, 7, '菜单新建', 68, 69, 4, 'system.menu.add', 1, '/api/v1/resources', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:23:10'),
	(38, 7, '菜单详情', 72, 73, 4, 'system.menu.detail', 1, '/api/v1/resources/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:30:06'),
	(39, 7, '菜单更新', 74, 75, 4, 'system.menu.update', 1, '/api/v1/resources/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:27:50'),
	(40, 7, '菜单删除', 76, 77, 4, 'system.menu.delete', 1, '/api/v1/resources/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:27:49'),
	(41, 7, '子菜单列表', 80, 81, 4, 'system.menu.children', 1, '/api/v1/resources/*/children', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:31:16'),
	(42, 7, '菜单移动', 70, 71, 4, 'system.menu.move', 1, '/api/v1/resources/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:31:06'),
	(43, 8, '资源列表', 84, 85, 4, 'system.api.list', 1, '/api/v1/resources', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:40:38'),
	(44, 8, '资源新建', 86, 87, 4, 'system.api.add', 1, '/api/v1/resources', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:40:55'),
	(45, 8, '资源详情', 90, 91, 4, 'system.api.detail', 1, '/api/v1/resources/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:41:09'),
	(46, 8, '资源更新', 92, 93, 4, 'system.api.update', 1, '/api/v1/resources/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:41:20'),
	(47, 8, '资源删除', 94, 95, 4, 'system.api.delete', 1, '/api/v1/resources/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:41:27'),
	(48, 8, '子资源列表', 98, 99, 4, 'system.api.children', 1, '/api/v1/resources/*/children', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:41:42'),
	(49, 8, '资源移动', 88, 89, 4, 'system.api.move', 1, '/api/v1/resources/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:41:03'),
	(50, 9, '字典列表', 102, 103, 4, 'system.dictionary.list', 1, '/api/v1/dictionaries', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:33:12'),
	(51, 9, '字典新建', 104, 105, 4, 'system.dictionary.add', 1, '/api/v1/dictionaries', 2, 'POST', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:33:12'),
	(52, 9, '字典删除（批量）', 106, 107, 4, 'system.dictionary.batchDelete', 1, '/api/v1/dictionaries', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:33:12'),
	(53, 9, '字典详情', 108, 109, 4, 'system.dictionary.detail', 1, '/api/v1/dictionaries/*', 2, 'GET', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:33:12'),
	(54, 9, '字典更新', 110, 111, 4, 'system.dictionary.update', 1, '/api/v1/dictionaries/*', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:33:12'),
	(55, 9, '字典删除', 112, 113, 4, 'system.dictionary.delete', 1, '/api/v1/dictionaries/*', 2, 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:33:12'),
	(56, 9, '字典状态', 114, 115, 4, 'system.dictionary.status', 1, '/api/v1/dictionaries/*/status', 2, 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2019-10-18 10:33:12'),
	(63, 4, '部门状态', 20, 21, 4, 'system.department.status', 1, '/api/v1/departments/*', 2, 'PATCH', NULL, NULL, '2019-10-18 10:23:10', '2019-10-18 10:23:13'),
	(64, 7, '资源状态', 78, 79, 4, 'system.menu.status', 1, '/api/v1/resources/*', 2, 'PATCH', NULL, NULL, '2019-10-18 10:30:58', '2019-10-18 10:31:16'),
	(65, 8, '资源状态', 96, 97, 4, 'system.api.status', 1, '/api/v1/resources/*', 2, 'PATCH', NULL, NULL, '2019-10-18 10:33:12', '2019-10-18 10:41:34');
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;

-- Dumping data for table my_database.sys_role: ~5 rows (大约)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `name`, `code`, `status`, `description`, `create_time`, `update_time`) VALUES
	(1, '管理员', 'Administration', 1, '系统管理员，拥有系统所有资源的管理权限。', '2019-10-07 01:16:01', '2019-10-18 11:03:17'),
	(2, '普通角色', 'user', 1, '只有首页的操作权限。', '2019-10-07 03:02:08', '2019-10-18 11:06:05'),
	(3, '添加角色', 'test_add', 1, '该角色只拥有添加的相关权限。', '2019-10-07 06:03:42', '2019-10-18 11:06:06'),
	(4, '删除角色', 'test_delete', 1, '该角色只拥有删除的相关权限。', '2019-10-07 06:04:17', '2019-10-18 05:13:11'),
	(5, '修改角色', 'test_update', 1, '该角色只拥有修改的相关权限。', '2019-10-07 06:04:49', '2019-10-18 09:12:52');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- Dumping data for table my_database.sys_role_resource: ~147 rows (大约)
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
	(59, 2, 1, '2019-10-07 04:16:21', NULL),
	(60, 3, 2, '2019-10-07 06:06:21', NULL),
	(61, 3, 10, '2019-10-07 06:06:21', NULL),
	(62, 3, 11, '2019-10-07 06:06:21', NULL),
	(63, 3, 12, '2019-10-07 06:06:21', NULL),
	(64, 3, 16, '2019-10-07 06:06:21', NULL),
	(66, 3, 18, '2019-10-07 06:06:21', NULL),
	(67, 3, 19, '2019-10-07 06:06:21', NULL),
	(69, 3, 27, '2019-10-07 06:06:21', NULL),
	(70, 3, 28, '2019-10-07 06:06:21', NULL),
	(72, 3, 36, '2019-10-07 06:06:21', NULL),
	(73, 3, 37, '2019-10-07 06:06:21', NULL),
	(74, 3, 41, '2019-10-07 06:06:21', NULL),
	(76, 3, 43, '2019-10-07 06:06:21', NULL),
	(77, 3, 44, '2019-10-07 06:06:21', NULL),
	(78, 3, 48, '2019-10-07 06:06:21', NULL),
	(80, 3, 50, '2019-10-07 06:06:21', NULL),
	(81, 3, 51, '2019-10-07 06:06:21', NULL),
	(83, 3, 1, '2019-10-07 06:06:21', NULL),
	(84, 3, 3, '2019-10-07 06:06:21', NULL),
	(85, 3, 4, '2019-10-07 06:06:21', NULL),
	(86, 3, 5, '2019-10-07 06:06:21', NULL),
	(87, 3, 6, '2019-10-07 06:06:21', NULL),
	(88, 3, 7, '2019-10-07 06:06:21', NULL),
	(89, 3, 8, '2019-10-07 06:06:21', NULL),
	(90, 3, 9, '2019-10-07 06:06:21', NULL),
	(91, 4, 2, '2019-10-07 06:07:41', NULL),
	(92, 4, 10, '2019-10-07 06:07:41', NULL),
	(93, 4, 11, '2019-10-07 06:07:41', NULL),
	(94, 4, 15, '2019-10-07 06:07:41', NULL),
	(95, 4, 16, '2019-10-07 06:07:41', NULL),
	(97, 4, 18, '2019-10-07 06:07:41', NULL),
	(98, 4, 20, '2019-10-07 06:07:41', NULL),
	(99, 4, 23, '2019-10-07 06:07:41', NULL),
	(101, 4, 27, '2019-10-07 06:07:41', NULL),
	(102, 4, 29, '2019-10-07 06:07:41', NULL),
	(103, 4, 32, '2019-10-07 06:07:41', NULL),
	(105, 4, 36, '2019-10-07 06:07:41', NULL),
	(106, 4, 40, '2019-10-07 06:07:41', NULL),
	(107, 4, 41, '2019-10-07 06:07:41', NULL),
	(108, 4, 42, '2019-10-07 06:07:41', NULL),
	(109, 4, 43, '2019-10-07 06:07:41', NULL),
	(110, 4, 47, '2019-10-07 06:07:41', NULL),
	(111, 4, 48, '2019-10-07 06:07:41', NULL),
	(113, 4, 50, '2019-10-07 06:07:41', NULL),
	(114, 4, 52, '2019-10-07 06:07:41', NULL),
	(115, 4, 55, '2019-10-07 06:07:41', NULL),
	(117, 4, 1, '2019-10-07 06:07:41', NULL),
	(118, 4, 3, '2019-10-07 06:07:41', NULL),
	(119, 4, 4, '2019-10-07 06:07:41', NULL),
	(120, 4, 5, '2019-10-07 06:07:41', NULL),
	(121, 4, 6, '2019-10-07 06:07:41', NULL),
	(122, 4, 7, '2019-10-07 06:07:41', NULL),
	(123, 4, 8, '2019-10-07 06:07:41', NULL),
	(124, 4, 9, '2019-10-07 06:07:41', NULL),
	(125, 5, 2, '2019-10-07 06:08:44', NULL),
	(126, 5, 10, '2019-10-07 06:08:44', NULL),
	(127, 5, 11, '2019-10-07 06:08:44', NULL),
	(128, 5, 13, '2019-10-07 06:08:44', NULL),
	(129, 5, 14, '2019-10-07 06:08:44', NULL),
	(130, 5, 16, '2019-10-07 06:08:44', NULL),
	(132, 5, 18, '2019-10-07 06:08:44', NULL),
	(133, 5, 21, '2019-10-07 06:08:44', NULL),
	(134, 5, 22, '2019-10-07 06:08:44', NULL),
	(136, 5, 27, '2019-10-07 06:08:44', NULL),
	(137, 5, 30, '2019-10-07 06:08:44', NULL),
	(138, 5, 31, '2019-10-07 06:08:44', NULL),
	(140, 5, 36, '2019-10-07 06:08:44', NULL),
	(141, 5, 38, '2019-10-07 06:08:44', NULL),
	(142, 5, 39, '2019-10-07 06:08:44', NULL),
	(143, 5, 41, '2019-10-07 06:08:44', NULL),
	(145, 5, 43, '2019-10-07 06:08:44', NULL),
	(146, 5, 45, '2019-10-07 06:08:44', NULL),
	(147, 5, 46, '2019-10-07 06:08:44', NULL),
	(148, 5, 48, '2019-10-07 06:08:44', NULL),
	(150, 5, 50, '2019-10-07 06:08:44', NULL),
	(151, 5, 53, '2019-10-07 06:08:44', NULL),
	(152, 5, 54, '2019-10-07 06:08:44', NULL),
	(154, 5, 1, '2019-10-07 06:08:44', NULL),
	(155, 5, 3, '2019-10-07 06:08:44', NULL),
	(156, 5, 4, '2019-10-07 06:08:44', NULL),
	(157, 5, 5, '2019-10-07 06:08:44', NULL),
	(158, 5, 6, '2019-10-07 06:08:44', NULL),
	(159, 5, 7, '2019-10-07 06:08:44', NULL),
	(160, 5, 8, '2019-10-07 06:08:44', NULL),
	(161, 5, 9, '2019-10-07 06:08:44', NULL),
	(183, 1, 63, '2019-10-18 10:57:30', NULL),
	(184, 1, 64, '2019-10-18 10:57:30', NULL),
	(185, 1, 65, '2019-10-18 10:57:30', NULL);
/*!40000 ALTER TABLE `sys_role_resource` ENABLE KEYS */;

-- Dumping data for table my_database.sys_user: ~5 rows (大约)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `nickname`, `real_name`, `password`, `salt`, `status`, `avatar`, `email`, `phone`, `mobile`, `sex`, `last_login_time`, `create_time`, `update_time`) VALUES
	(1, 'admin', '管理员', NULL, '$2a$10$BQNgm4HovoDft9kj93gSgOnV0PNjDwSEEqTTt81Ghd5sgH1zAs5kO', '$2a$10$BQNgm4HovoDft9kj93gSgO', 1, 'http://lorempixel.com/200/200/', NULL, NULL, NULL, NULL, NULL, '2019-10-06 08:43:06', '2019-10-18 09:07:40'),
	(2, 'user', '测试用户', NULL, '$2a$10$RUrZV0lxdBkratQfQw7wLe3SgPhx9AoZrGnVGcqp/iDbtAOMhDOvS', '$2a$10$RUrZV0lxdBkratQfQw7wLe', 1, NULL, NULL, NULL, NULL, NULL, NULL, '2019-10-07 05:17:35', '2019-10-18 09:07:15'),
	(3, 'add', '测试添加', NULL, '$2a$10$GhIlaJ4Fb83PjbIbSfXNGuAxTT2cczv5D2J.sbEoxh0lTXlMnbmXy', '$2a$10$GhIlaJ4Fb83PjbIbSfXNGu', 1, NULL, NULL, NULL, NULL, NULL, NULL, '2019-10-07 06:02:20', '2019-10-18 05:16:04'),
	(4, 'delete', '测试删除', NULL, '$2a$10$rj9cfiANmp6BApeZ5n3miuS2NLHdSHJfWatB1QpNmHWYiaOCxYuUe', '$2a$10$rj9cfiANmp6BApeZ5n3miu', 1, NULL, NULL, NULL, NULL, NULL, NULL, '2019-10-07 06:02:37', '2019-10-18 05:16:05'),
	(5, 'update', '测试修改', NULL, '$2a$10$ibNjVTpWCg0JL6nn66hoaeIpooFUrNO9XW.hRnFzEHyoLAJx3aeSm', '$2a$10$ibNjVTpWCg0JL6nn66hoae', 1, NULL, NULL, NULL, NULL, b'011', NULL, '2019-10-07 06:03:00', '2019-10-18 09:11:27');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- Dumping data for table my_database.sys_user_role: ~5 rows (大约)
DELETE FROM `sys_user_role`;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`) VALUES
	(1, 1, 1, '2019-10-07 08:19:20', NULL),
	(2, 2, 2, '2019-10-07 05:18:07', NULL),
	(3, 3, 3, '2019-10-07 06:08:58', NULL),
	(4, 4, 4, '2019-10-07 06:09:03', NULL),
	(5, 5, 5, '2019-10-07 06:09:08', NULL);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
