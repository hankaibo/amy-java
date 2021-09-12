-- --------------------------------------------------------
-- Host:                         rm-2zezxd1097ucps7t8bo.mysql.rds.aliyuncs.com
-- Server version:               8.0.18 - Source distribution
-- Server OS:                    Linux
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping data for table amy-db.sys_department: ~4 rows (approximately)
DELETE FROM `sys_department`;
/*!40000 ALTER TABLE `sys_department` DISABLE KEYS */;
INSERT INTO `sys_department` (`id`, `parent_id`, `name`, `lft`, `rgt`, `level`, `status`, `is_update`, `description`, `create_time`, `update_time`) VALUES
	(1, NULL, '根', 1, 8, 1, 'ENABLED', 1, NULL, '2021-03-20 17:52:15', '2021-07-24 22:32:14'),
	(95, 1, '研发部', 6, 7, 2, 'ENABLED', 1, NULL, '2021-03-20 17:55:37', '2021-07-24 22:32:14'),
	(96, 1, '测试部', 2, 5, 2, 'ENABLED', 1, NULL, '2021-03-20 17:57:08', '2021-07-24 22:32:14'),
	(108, 96, '前端', 3, 4, 3, 'ENABLED', 1, NULL, '2021-07-24 14:32:14', NULL);
/*!40000 ALTER TABLE `sys_department` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_department_user: ~5 rows (approximately)
DELETE FROM `sys_department_user`;
/*!40000 ALTER TABLE `sys_department_user` DISABLE KEYS */;
INSERT INTO `sys_department_user` (`id`, `department_id`, `user_id`, `create_time`, `update_time`) VALUES
	(1, 1, 1, '2019-10-06 08:43:06', '2019-10-18 09:07:39'),
	(30, 95, 32, '2020-09-07 14:16:37', NULL),
	(38, 95, 1, '2021-07-09 03:09:45', NULL),
	(42, 96, 42, '2021-07-26 10:08:42', NULL),
	(43, 108, 42, '2021-07-26 10:08:42', NULL);
/*!40000 ALTER TABLE `sys_department_user` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_dictionary: ~2 rows (approximately)
DELETE FROM `sys_dictionary`;
/*!40000 ALTER TABLE `sys_dictionary` DISABLE KEYS */;
INSERT INTO `sys_dictionary` (`id`, `name`, `code`, `status`, `description`, `create_time`, `update_time`) VALUES
	(1, '性别', 'SYSTEM_SEX', 'ENABLED', '系统性别码表。', '2021-09-12 18:32:09', '2021-09-12 21:29:53'),
	(2, '国家', 'SYSTEM_COUNTRY', 'ENABLED', NULL, '2021-09-12 22:31:31', '2021-09-12 22:31:42');
/*!40000 ALTER TABLE `sys_dictionary` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_dictionary_item: ~6 rows (approximately)
DELETE FROM `sys_dictionary_item`;
/*!40000 ALTER TABLE `sys_dictionary_item` DISABLE KEYS */;
INSERT INTO `sys_dictionary_item` (`id`, `dictionary_id`, `name`, `value`, `sort`, `status`, `description`, `create_time`, `update_time`) VALUES
	(1, 1, '男', '1', 1, 'ENABLED', NULL, '2021-09-12 21:48:39', '2021-09-12 21:53:30'),
	(2, 1, '女', '2', 2, 'ENABLED', NULL, '2021-09-12 21:53:42', NULL),
	(3, 1, '保密', '3', 3, 'ENABLED', NULL, '2021-09-12 21:54:03', NULL),
	(4, 1, '未知', '4', 4, 'ENABLED', NULL, '2021-09-12 22:20:29', NULL),
	(5, 2, '中国', 'China', 1, 'ENABLED', NULL, '2021-09-12 22:35:11', '2021-09-12 22:35:34'),
	(6, 2, '日本', 'Japan', 2, 'ENABLED', NULL, '2021-09-12 22:35:26', NULL);
/*!40000 ALTER TABLE `sys_dictionary_item` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_message_content: ~1 rows (approximately)
DELETE FROM `sys_message_content`;
/*!40000 ALTER TABLE `sys_message_content` DISABLE KEYS */;
INSERT INTO `sys_message_content` (`id`, `title`, `content`, `type`, `create_time`, `update_time`) VALUES
	(27, 'test1', NULL, 'NOTIFICATION', '2020-09-08 15:52:52', NULL);
/*!40000 ALTER TABLE `sys_message_content` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_message_receiver: ~4 rows (approximately)
DELETE FROM `sys_message_receiver`;
/*!40000 ALTER TABLE `sys_message_receiver` DISABLE KEYS */;
INSERT INTO `sys_message_receiver` (`id`, `send_id`, `receive_id`, `content_id`, `status`, `is_read`, `create_time`, `update_time`) VALUES
	(1, 1, 1, 11, 'ENABLED', 0, '2020-06-10 16:08:53', NULL),
	(11, 1, 28, 26, 'ENABLED', 0, '2020-09-08 00:15:47', NULL),
	(12, 1, 32, 26, 'ENABLED', 0, '2020-09-08 00:15:47', NULL),
	(13, 1, 32, 27, 'ENABLED', 0, '2020-09-08 15:52:52', NULL);
/*!40000 ALTER TABLE `sys_message_receiver` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_message_sender: ~3 rows (approximately)
DELETE FROM `sys_message_sender`;
/*!40000 ALTER TABLE `sys_message_sender` DISABLE KEYS */;
INSERT INTO `sys_message_sender` (`id`, `send_id`, `receive_id`, `content_id`, `status`, `is_publish`, `publish_time`, `create_time`, `update_time`) VALUES
	(1, 1, 1, 11, 'ENABLED', 1, NULL, '2020-06-10 16:08:53', NULL),
	(10, 1, 1, 25, 'ENABLED', 1, NULL, '2020-09-01 22:43:37', '2020-09-01 22:43:47'),
	(13, 1, 32, 27, 'ENABLED', 1, '2020-09-08 15:52:52', '2020-09-08 15:52:52', NULL);
/*!40000 ALTER TABLE `sys_message_sender` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_region: ~10 rows (approximately)
DELETE FROM `sys_region`;
/*!40000 ALTER TABLE `sys_region` DISABLE KEYS */;
INSERT INTO `sys_region` (`id`, `parent_id`, `name`, `value`, `code`, `lft`, `rgt`, `level`, `status`, `is_update`, `description`, `create_time`, `update_time`) VALUES
	(1, NULL, '根', '1', 'root', 1, 20, 1, 'ENABLED', 1, '根', '2021-06-26 22:49:40', '2021-09-12 12:39:56'),
	(15, 1, '中国', '+86', 'china', 2, 19, 2, 'ENABLED', 1, NULL, '2021-09-12 01:36:59', '2021-09-12 12:40:13'),
	(16, 15, '北京市', '110000000000', '110000000000', 3, 10, 3, 'ENABLED', 1, NULL, '2021-09-12 10:43:08', '2021-09-12 12:29:29'),
	(17, 16, '丰台区', '110106000000', '110106000000', 4, 9, 4, 'ENABLED', 1, NULL, '2021-09-12 10:44:18', '2021-09-12 10:45:25'),
	(18, 17, '右安门街道', '110106001000', '110106001000', 5, 8, 5, 'ENABLED', 1, NULL, '2021-09-12 10:44:49', '2021-09-12 10:45:25'),
	(19, 18, '翠林三里社区', '110106001003', '110106001003', 6, 7, 6, 'ENABLED', 1, NULL, '2021-09-12 10:45:27', NULL),
	(20, 15, '河北省', '130000000000', '130000000000', 11, 18, 3, 'ENABLED', 1, NULL, '2021-09-12 12:30:10', '2021-09-12 12:40:13'),
	(21, 20, '石家庄市', '130100000000', '130100000000', 12, 17, 4, 'ENABLED', 1, NULL, '2021-09-12 12:30:48', '2021-09-12 12:40:13'),
	(22, 21, '裕华区', '130108000000', '130108000000', 13, 16, 5, 'ENABLED', 1, NULL, '2021-09-12 12:31:32', '2021-09-12 12:40:13'),
	(23, 22, '裕东街道', '130108007000', '130108007000', 14, 15, 6, 'ENABLED', 1, NULL, '2021-09-12 12:39:57', '2021-09-12 12:40:13');
/*!40000 ALTER TABLE `sys_region` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_resource: ~92 rows (approximately)
DELETE FROM `sys_resource`;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` (`id`, `parent_id`, `name`, `lft`, `rgt`, `level`, `code`, `status`, `is_update`, `uri`, `type`, `method`, `icon`, `description`, `create_time`, `update_time`) VALUES
	(1, NULL, '菜单', 1, 184, 1, 'menuTree', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2021-09-12 18:00:20'),
	(2, 1, '首页', 2, 9, 2, 'home', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(3, 2, '获取当前登录用户信息', 3, 4, 3, 'system:user:info', 'ENABLED', 1, '/api/v1/users/info', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-07-29 09:49:11'),
	(4, 1, '系统配置', 36, 183, 2, 'system', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2021-09-12 18:00:20'),
	(5, 4, '部门管理', 37, 54, 3, 'system:department', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(6, 5, '获取部门树', 38, 39, 4, 'system:department:tree', 'ENABLED', 1, '/api/v1/departments', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(7, 5, '新建部门', 40, 41, 4, 'system:department:add', 'ENABLED', 1, '/api/v1/departments', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(8, 5, '移动部门', 42, 43, 4, 'system:department:move', 'ENABLED', 1, '/api/v1/departments', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(9, 5, '获取部门详情', 44, 45, 4, 'system:department:detail', 'ENABLED', 1, '/api/v1/departments/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(10, 5, '更新部门', 46, 47, 4, 'system:department:update', 'ENABLED', 1, '/api/v1/departments/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(11, 5, '删除部门', 48, 49, 4, 'system:department:delete', 'ENABLED', 1, '/api/v1/departments/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(12, 5, '获取子部门列表', 50, 51, 4, 'system:department:children', 'ENABLED', 1, '/api/v1/departments/*/children', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(13, 5, '启用禁用部门', 52, 53, 4, 'system:department:status', 'ENABLED', 1, '/api/v1/departments/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-18 10:23:10', '2021-08-25 16:43:33'),
	(14, 4, '用户管理', 55, 78, 3, 'system:user', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(15, 14, '获取用户列表', 56, 57, 4, 'system:user:list', 'ENABLED', 1, '/api/v1/users', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(16, 14, '新建用户', 58, 59, 4, 'system:user:add', 'ENABLED', 1, '/api/v1/users', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(17, 14, '批量删除用户', 60, 61, 4, 'system:user:batchDelete', 'ENABLED', 1, '/api/v1/users', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(18, 14, '获取用户详情', 62, 63, 4, 'system:user:detail', 'ENABLED', 1, '/api/v1/users/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(19, 14, '更新用户', 64, 65, 4, 'system:user:update', 'ENABLED', 1, '/api/v1/users/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(20, 14, '删除用户', 66, 67, 4, 'system:user:delete', 'ENABLED', 1, '/api/v1/users/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(21, 14, '重置用户密码', 68, 69, 4, 'system:user:pwd:reset', 'ENABLED', 1, '/api/v1/users/*/password', 'API', 'PATCH', NULL, NULL, '2020-03-29 10:27:40', '2021-08-25 16:43:33'),
	(22, 14, '修改用户密码', 70, 71, 4, 'system:user:pwd:update', 'ENABLED', 1, '/api/v1/users/*/pwd', 'API', 'PATCH', NULL, NULL, '2020-03-29 10:28:39', '2021-08-25 16:43:33'),
	(23, 14, '获取用户角色', 72, 73, 4, 'system:user:roles', 'ENABLED', 1, '/api/v1/users/*/roles', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(24, 14, '赋予用户角色', 74, 75, 4, 'system:user:grant', 'ENABLED', 1, '/api/v1/users/*/roles', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(25, 14, '启用禁用用户', 76, 77, 4, 'system:user:status', 'ENABLED', 1, '/api/v1/users/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(26, 4, '角色管理', 79, 100, 3, 'system:role', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(27, 26, '获取角色树', 80, 81, 4, 'system:role:tree', 'ENABLED', 1, '/api/v1/roles', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(28, 26, '新建角色', 82, 83, 4, 'system:role:add', 'ENABLED', 1, '/api/v1/roles', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(29, 26, '移动角色', 84, 85, 4, 'system:role:move', 'ENABLED', 1, '/api/v1/roles', 'API', 'PUT', NULL, NULL, '2020-03-29 10:37:33', '2021-08-25 16:43:33'),
	(30, 26, '获取角色详情', 86, 87, 4, 'system:role:detail', 'ENABLED', 1, '/api/v1/roles/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(31, 26, '更新角色', 88, 89, 4, 'system:role:update', 'ENABLED', 1, '/api/v1/roles/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(32, 26, '删除角色', 90, 91, 4, 'system:role:delete', 'ENABLED', 1, '/api/v1/roles/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(33, 26, '获取子角色列表', 92, 93, 4, 'system:role:children', 'ENABLED', 1, '/api/v1/roles/*/children', 'API', 'GET', NULL, NULL, '2020-03-29 10:40:24', '2021-08-25 16:43:33'),
	(34, 26, '获取角色资源', 94, 95, 4, 'system:role:resources', 'ENABLED', 1, '/api/v1/roles/*/resources', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(35, 26, '赋予角色资源', 96, 97, 4, 'system:role:grant', 'ENABLED', 1, '/api/v1/roles/*/resources', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(36, 26, '启用禁用角色', 98, 99, 4, 'system:role:status', 'ENABLED', 1, '/api/v1/roles/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(37, 4, '菜单管理', 101, 118, 3, 'system:menu', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(38, 37, '获取菜单树', 102, 103, 4, 'system:menu:tree', 'ENABLED', 1, '/api/v1/resources', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(39, 37, '新建菜单', 104, 105, 4, 'system:menu:add', 'ENABLED', 1, '/api/v1/resources', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(40, 37, '菜单移动', 106, 107, 4, 'system:menu:move', 'ENABLED', 1, '/api/v1/resources', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(41, 37, '获取菜单详情', 108, 109, 4, 'system:menu:detail', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(42, 37, '更新菜单', 110, 111, 4, 'system:menu:update', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(43, 37, '删除菜单', 112, 113, 4, 'system:menu:delete', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(44, 37, '获取子菜单列表', 114, 115, 4, 'system:menu:children', 'ENABLED', 1, '/api/v1/resources/*/children', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(45, 37, '启用禁用菜单', 116, 117, 4, 'system:menu:status', 'ENABLED', 1, '/api/v1/resources/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-18 10:30:58', '2021-08-25 16:43:33'),
	(46, 4, '接口保护', 119, 134, 3, 'system:api', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(47, 46, '新建接口', 120, 121, 4, 'system:api:add', 'ENABLED', 1, '/api/v1/resources', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(48, 46, '移动接口', 122, 123, 4, 'system:api:move', 'ENABLED', 1, '/api/v1/resources', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(49, 46, '获取接口详情', 124, 125, 4, 'system:api:detail', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(50, 46, '更新接口', 126, 127, 4, 'system:api:update', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(51, 46, '删除接口', 128, 129, 4, 'system:api:delete', 'ENABLED', 1, '/api/v1/resources/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(52, 46, '获取子接口列表', 130, 131, 4, 'system:api:children', 'ENABLED', 1, '/api/v1/resources/*/children', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(53, 46, '启用禁用接口', 132, 133, 4, 'system:api:status', 'ENABLED', 1, '/api/v1/resources/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-18 10:33:12', '2021-08-25 16:43:33'),
	(54, 4, '区域管理', 135, 152, 3, 'system:region', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(55, 54, '获取区域树', 136, 137, 4, 'system:region:tree', 'ENABLED', 1, '/api/v1/regions', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(56, 54, '新建区域', 140, 141, 4, 'system:region:add', 'ENABLED', 1, '/api/v1/regions', 'API', 'POST', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(57, 54, '获取子区域列表', 148, 149, 4, 'system:region:chirdren', 'ENABLED', 1, '/api/v1/regions/*/children', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(58, 54, '获取区域详情', 142, 143, 4, 'system:region:detail', 'ENABLED', 1, '/api/v1/regions/*', 'API', 'GET', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(59, 54, '更新区域', 144, 145, 4, 'system:region:update', 'ENABLED', 1, '/api/v1/regions/*', 'API', 'PUT', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(60, 54, '删除区域', 146, 147, 4, 'system:region:delete', 'ENABLED', 1, '/api/v1/regions/*', 'API', 'DELETE', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(61, 54, '启用禁用区域', 150, 151, 4, 'system:region:status', 'ENABLED', 1, '/api/v1/regions/*/status', 'API', 'PATCH', NULL, NULL, '2019-10-07 01:30:52', '2021-08-25 16:43:33'),
	(63, 76, '获取信息列表', 12, 13, 4, 'system:message:list', 'DISABLED', 1, '/api/v1/messages', 'API', 'GET', NULL, NULL, '2020-03-29 11:09:36', '2021-08-25 16:43:33'),
	(64, 76, '新建信息', 14, 15, 4, 'system:message:add', 'DISABLED', 1, '/api/v1/messages', 'API', 'POST', NULL, NULL, '2020-03-29 11:10:31', '2021-08-25 16:43:33'),
	(65, 76, '批量发布信息', 30, 31, 4, 'system:message:batchPublish', 'DISABLED', 1, '/api/v1/messages', 'API', 'PUT', NULL, NULL, '2020-03-29 11:12:49', '2021-08-25 16:43:33'),
	(66, 76, '批量删除信息', 18, 19, 4, 'system:message:batchDelete', 'DISABLED', 1, '/api/v1/messages', 'API', 'DELETE', NULL, NULL, '2020-03-29 11:13:27', '2021-08-25 16:43:33'),
	(67, 76, '获取信息详情', 16, 17, 4, 'system:message:detail', 'DISABLED', 1, '/api/v1/messages/*', 'API', 'GET', NULL, NULL, '2020-03-29 11:18:23', '2021-08-25 16:43:33'),
	(68, 76, '更新信息', 20, 21, 4, 'system:message:update', 'DISABLED', 1, '/api/v1/messages/*', 'API', 'PUT', NULL, NULL, '2020-03-29 11:19:04', '2021-08-25 16:43:33'),
	(69, 76, '删除信息', 22, 23, 4, 'system:message:delete', 'DISABLED', 1, '/api/v1/messages/*', 'API', 'DELETE', NULL, NULL, '2020-03-29 11:19:41', '2021-08-25 16:43:33'),
	(70, 76, '发布信息', 24, 25, 4, 'system:message:publish', 'DISABLED', 1, '/api/v1/messages/*/publish', 'API', 'PATCH', NULL, NULL, '2020-03-29 11:20:39', '2021-08-25 16:43:33'),
	(71, 76, '启用禁用信息', 26, 27, 4, 'system:message:status', 'DISABLED', 1, '/api/v1/messages/*/status', 'API', 'PATCH', NULL, NULL, '2020-03-29 11:21:37', '2021-08-25 16:43:33'),
	(72, 76, '定时发布信息', 28, 29, 4, 'system:message:timer', 'DISABLED', 1, '/api/v1/messages/timer', 'API', 'POST', NULL, NULL, '2020-03-30 03:29:19', '2021-08-25 16:43:33'),
	(73, 1, '个人页', 10, 35, 2, 'account', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2020-08-27 15:55:07', '2021-08-25 16:43:33'),
	(76, 73, '个人中心', 11, 32, 3, 'account:center', 'DISABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2020-08-27 16:05:34', '2021-08-25 16:43:33'),
	(77, 73, '个人设置', 33, 34, 3, 'account:setting', 'DISABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2020-08-27 16:05:50', '2021-08-25 16:43:33'),
	(80, 54, '移动区域', 138, 139, 4, 'system:region:move', 'ENABLED', 1, '/api/v1/regions', 'API', 'PUT', NULL, NULL, '2021-06-26 23:37:52', '2021-08-25 16:43:33'),
	(84, 2, '测试', 5, 6, 3, 'account:test', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2021-08-19 01:58:58', NULL),
	(85, 2, '测试', 7, 8, 3, 'account:test', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2021-08-25 08:43:33', NULL),
	(86, 4, '字典管理', 153, 182, 3, 'system:dictionary', 'ENABLED', 1, NULL, 'MENU', NULL, NULL, NULL, '2021-09-12 17:47:18', '2021-09-12 18:00:20'),
	(87, 86, '获取字典列表', 154, 155, 4, 'system:dictionary:list', 'ENABLED', 1, '/api/v1/dictionaries', 'API', 'GET', NULL, NULL, '2021-09-12 17:50:21', NULL),
	(88, 86, '新建字典', 156, 157, 4, 'system:dictionary:add', 'ENABLED', 1, '/api/v1/dictionaries', 'API', 'POST', NULL, NULL, '2021-09-12 17:50:57', NULL),
	(89, 86, '批量删除字典', 158, 159, 4, 'system:dictionary:batchDelete', 'ENABLED', 1, '/api/v1/dictionaries', 'API', 'DELETE', NULL, NULL, '2021-09-12 17:51:51', NULL),
	(90, 86, '获取字典详情', 160, 161, 4, 'system:dictionary:detail', 'ENABLED', 1, '/api/v1/dictionaries/*', 'API', 'GET', NULL, NULL, '2021-09-12 17:52:29', NULL),
	(91, 86, '更新字典', 162, 163, 4, 'system:dictionary:update', 'ENABLED', 1, '/api/v1/dictionaries/*', 'API', 'PUT', NULL, NULL, '2021-09-12 17:53:10', NULL),
	(92, 86, '删除字典', 164, 165, 4, 'system:dictionary:delete', 'ENABLED', 1, '/api/v1/dictionaries/*', 'API', 'DELETE', NULL, NULL, '2021-09-12 17:53:57', NULL),
	(93, 86, '启用禁用字典状态', 166, 167, 4, 'system:dictionary:status', 'ENABLED', 1, '/api/v1/dictionaries/*/status', 'API', 'PATCH', NULL, NULL, '2021-09-12 17:54:38', NULL),
	(94, 86, '获取字典项列表', 168, 169, 4, 'system:dictionaryItem:list', 'ENABLED', 1, '/api/v1/dictionaryItems', 'API', 'GET', NULL, NULL, '2021-09-12 17:55:26', NULL),
	(95, 86, '新建字典项', 170, 171, 4, 'system:dictionaryItem:add', 'ENABLED', 1, '/api/v1/dictionaryItems', 'API', 'POST', NULL, NULL, '2021-09-12 17:56:06', NULL),
	(96, 86, '批量删除字典项', 172, 173, 4, 'system:dictionaryItem:batchDelete', 'ENABLED', 1, '/api/v1/dictionaryItems', 'API', 'DELETE', NULL, NULL, '2021-09-12 17:56:54', NULL),
	(97, 86, '获取字典项详情', 174, 175, 4, 'system:dictionaryItem:detail', 'ENABLED', 1, '/api/v1/dictionaryItems/*', 'API', 'GET', NULL, NULL, '2021-09-12 17:58:01', NULL),
	(98, 86, '更新字典项', 176, 177, 4, 'system:dictionaryItem:update', 'ENABLED', 1, '/api/v1/dictionaryItems/*', 'API', 'PUT', NULL, NULL, '2021-09-12 17:58:46', NULL),
	(99, 86, '删除字典项', 178, 179, 4, 'system:dictionaryItem:delete', 'ENABLED', 1, '/api/v1/dictionaryItems/*', 'API', 'DELETE', NULL, NULL, '2021-09-12 17:59:26', NULL),
	(100, 86, '启用禁用字典项状态', 180, 181, 4, 'system:dictionaryItem:status', 'ENABLED', 1, '/api/v1/dictionaryItems/*/status', 'API', 'PATCH', NULL, NULL, '2021-09-12 18:00:22', NULL);
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_role: ~7 rows (approximately)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `code`, `parent_id`, `name`, `lft`, `rgt`, `level`, `status`, `is_update`, `description`, `create_time`, `update_time`) VALUES
	(1, 'Administration', NULL, '管理员', 1, 14, 1, 'ENABLED', 1, '系统管理员，拥有系统所有资源的管理权限。', '2019-10-07 01:16:01', '2021-07-24 22:31:24'),
	(37, 'user1', 1, '普通角色', 2, 3, 2, 'ENABLED', 1, '只拥有个人相关的权限。', '2020-11-03 00:35:34', '2021-07-31 10:20:22'),
	(38, 'user2', 1, '部门管理员', 4, 9, 2, 'ENABLED', 1, '拥有部门与用户管理相关的所有权限。', '2020-11-03 00:35:41', '2021-07-31 10:20:22'),
	(39, 'user3', 1, '审查角色', 10, 11, 2, 'ENABLED', 1, '只拥有查看权限。', '2020-11-03 00:35:48', '2021-07-24 22:31:24'),
	(40, 'user4', 1, '测试角色', 12, 13, 2, 'ENABLED', 1, '测试只拥有用户权限没有部门权限的情况。', '2020-11-03 00:35:56', '2021-07-24 22:31:24'),
	(47, '001', 38, '支持团队', 5, 6, 3, 'ENABLED', 1, NULL, '2021-07-24 14:30:58', '2021-07-31 10:20:22'),
	(48, '002', 38, 'cloud', 7, 8, 3, 'ENABLED', 1, NULL, '2021-07-24 14:31:24', '2021-07-31 10:20:22');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_role_resource: ~192 rows (approximately)
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
	(946, 40, 25, '2020-12-12 14:51:00', NULL),
	(947, 1, 80, '2021-06-26 23:37:52', NULL),
	(951, 1, 84, '2021-08-19 01:58:58', NULL),
	(952, 1, 85, '2021-08-25 08:43:33', NULL),
	(953, 1, 86, '2021-09-12 17:47:18', NULL),
	(954, 1, 87, '2021-09-12 17:50:21', NULL),
	(955, 1, 88, '2021-09-12 17:50:57', NULL),
	(956, 1, 89, '2021-09-12 17:51:51', NULL),
	(957, 1, 90, '2021-09-12 17:52:29', NULL),
	(958, 1, 91, '2021-09-12 17:53:10', NULL),
	(959, 1, 92, '2021-09-12 17:53:57', NULL),
	(960, 1, 93, '2021-09-12 17:54:38', NULL),
	(961, 1, 94, '2021-09-12 17:55:26', NULL),
	(962, 1, 95, '2021-09-12 17:56:06', NULL),
	(963, 1, 96, '2021-09-12 17:56:54', NULL),
	(964, 1, 97, '2021-09-12 17:58:01', NULL),
	(965, 1, 98, '2021-09-12 17:58:46', NULL),
	(966, 1, 99, '2021-09-12 17:59:26', NULL),
	(967, 1, 100, '2021-09-12 18:00:22', NULL);
/*!40000 ALTER TABLE `sys_role_resource` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_user: ~3 rows (approximately)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `nickname`, `password`, `salt`, `status`, `avatar`, `email`, `phone`, `mobile`, `sex`, `signature`, `profile`, `address`, `last_login_ip`, `last_login_time`, `create_time`, `update_time`) VALUES
	(1, 'admin', '管理员', '$2a$10$BQNgm4HovoDft9kj93gSgOnV0PNjDwSEEqTTt81Ghd5sgH1zAs5kO', '$2a$10$BQNgm4HovoDft9kj93gSgO', 'ENABLED', 'E:/tmp/upload/images/03e537b6-e5f9-4f71-912e-bca86610d9ff.jpg', 'hankaibo@mail.com', '+86-12345678', '+86-18612345678', b'001', '胸无点墨，满腹牢骚', '我是一条狗。', '东城区1号', NULL, NULL, '2019-10-03 00:43:06', '2021-09-12 22:28:04'),
	(32, 'hankaibo', NULL, '$2a$10$BCLF4kcbYfxKftlnWO.0KuVLCMtrnajV4nCPqD05hJWaxKqbf/37i', '$2a$10$BCLF4kcbYfxKftlnWO.0Ku', 'ENABLED', 'E:/tmp/upload/images/e5f122e5-c0a5-4fd1-a2de-fcca5b7b4ad7.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-09-07 14:16:37', '2020-09-08 00:24:48'),
	(42, 'yjftest3', NULL, '$2a$10$95Hr0xZ3RC72ObL.V.fJ7uujgLanV3/rG96xZtoxgQs4XvMttkLKm', '$2a$10$95Hr0xZ3RC72ObL.V.fJ7u', 'ENABLED', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-07-26 10:08:42', '2021-07-27 05:54:03');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- Dumping data for table amy-db.sys_user_role: ~12 rows (approximately)
DELETE FROM `sys_user_role`;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`) VALUES
	(1, 1, 1, '2019-10-07 08:19:20', NULL),
	(39, 32, 40, '2020-12-12 14:51:37', NULL),
	(40, 32, 38, '2020-12-12 16:33:12', NULL),
	(42, 1, 37, '2021-07-09 03:09:55', NULL),
	(43, 1, 38, '2021-07-09 03:09:55', NULL),
	(44, 1, 39, '2021-07-09 03:09:55', NULL),
	(45, 1, 40, '2021-07-09 03:09:55', NULL),
	(51, 42, 1, '2021-07-27 05:54:16', NULL),
	(52, 42, 38, '2021-07-27 05:54:16', NULL),
	(53, 42, 47, '2021-07-27 05:54:16', NULL),
	(54, 42, 48, '2021-07-27 05:54:16', NULL),
	(55, 42, 37, '2021-07-27 07:38:49', NULL);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
