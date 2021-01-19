package cn.mypandora.springboot.modular.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cn.mypandora.springboot.core.annotation.NullOrNumber;
import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.core.validate.*;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.model.vo.*;
import cn.mypandora.springboot.modular.system.service.RoleService;
import cn.mypandora.springboot.modular.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * UserController
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Validated
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * 获取当前登录用户信息。
     *
     * @param userId
     *            当前登录用户id
     * @return 用户信息，用户权限对应的菜单信息
     */
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/info")
    public User getCurrentUser(@ApiIgnore Long userId) {
        return userService.getUserById(userId);
    }

    /**
     * 更新当前登录用户信息。
     * 
     * @param userId
     *            当前登录用户id
     * @param user
     *            用户数据
     */
    @ApiOperation(value = "更新当前登录用户信息")
    @PutMapping("/info")
    public void updateCurrentUser(@ApiIgnore Long userId,
        @Validated({UpdateGroup.class}) @RequestBody @ApiParam(value = "用户数据", required = true) User user) {
        // 只能修改自己
        if (userId.equals(user.getId())) {
            userService.updateUser(user, null, null);
        }
    }

    /**
     * 分页查询用户数据。
     *
     * @param pageNum
     *            页码
     * @param pageSize
     *            每页条数
     * @param username
     *            用户名称
     * @param phone
     *            电话
     * @param mobile
     *            手机
     * @param sex
     *            性别
     * @param status
     *            状态
     * @param departmentId
     *            部门id
     * @return 分页数据
     */
    @ApiOperation(value = "用户列表")
    @GetMapping
    public PageInfo<User> pageUser(
        @Positive @RequestParam(value = "current", defaultValue = "1") @ApiParam(value = "页码",
            required = true) int pageNum,
        @Positive @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "每页条数",
            required = true) int pageSize,
        @RequestParam(value = "username", required = false) @ApiParam(value = "用户名称") String username,
        @RequestParam(value = "phone", required = false) @ApiParam(value = "电话") String phone,
        @RequestParam(value = "mobile", required = false) @ApiParam(value = "手机") String mobile,
        @RequestParam(value = "sex", required = false) @ApiParam(value = "性别") Byte sex,
        @NullOrNumber @RequestParam(value = "status", required = false) @ApiParam(value = "状态") Integer status,
        @RequestParam(value = "departmentId", required = false) @ApiParam(value = "部门id") Long departmentId) {
        User user = new User();
        user.setUsername(username);
        user.setPhone(phone);
        user.setMobile(mobile);
        user.setSex(sex);
        user.setStatus(status);
        return userService.pageUser(pageNum, pageSize, user, departmentId);
    }

    @ApiOperation(value = "图片上传")
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return userService.saveFile(file);
    }

    /**
     * 新建用户。
     *
     * @param user
     *            用户数据
     */
    @ApiOperation(value = "用户新建")
    @PostMapping
    public void
        addUser(@Validated({AddGroup.class}) @RequestBody @ApiParam(value = "用户数据", required = true) User user) {
        userService.addUser(user);
    }

    /**
     * 查询用户详细数据。
     *
     * @param id
     *            用户主键id
     * @return 用户信息
     */
    @ApiOperation(value = "用户详情")
    @GetMapping("/{id}")
    public User getUserById(@Positive @PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id) {
        User user = userService.getUserById(id);
        user.setPassword(null);
        user.setSalt(null);
        return user;
    }

    /**
     * 更新用户。
     *
     * @param userUpdate
     *            用户数据
     */
    @ApiOperation(value = "用户更新")
    @PutMapping("/{id}")
    public void updateUser(
        @Validated({UpdateGroup.class}) @RequestBody @ApiParam(value = "用户数据", required = true) UserUpdate userUpdate) {
        User user = userUpdate.getUser();
        Long[] plusDepartmentIds = userUpdate.getPlusDepartmentIds();
        Long[] minusDepartmentIds = userUpdate.getMinusDepartmentIds();
        userService.updateUser(user, plusDepartmentIds, minusDepartmentIds);
    }

    /**
     * 启用禁用用户。
     *
     * @param id
     *            用户主键id
     * @param status
     *            状态(1:启用，0:禁用)
     */
    @ApiOperation(value = "用户状态启用禁用")
    @PatchMapping("/{id}/status")
    public void enableUser(@Positive @PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
        @Range(min = 0, max = 1) @RequestParam @ApiParam(value = "启用(1)，禁用(0)", required = true) Integer status) {
        userService.enableUser(id, status);
    }

    /**
     * 重置密码。
     *
     * @param id
     *            用户主键id
     * @param userPassword
     *            新密码
     */
    @ApiOperation(value = "重置用户密码")
    @PatchMapping("/{id}/password")
    public void resetPassword(@Positive @PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
        @Validated({ResetPasswordGroup.class}) @RequestBody @ApiParam(value = "新密码",
            required = true) UserPassword userPassword) {
        String password = userPassword.getNewPassword();
        userService.resetPassword(id, password);
    }

    /**
     * 修改密码。
     *
     * @param id
     *            用户主键id
     * @param userPassword
     *            {oldPassword: 旧密码, newPassword: 新密码}
     */
    @ApiOperation(value = "修改密码")
    @PatchMapping("/{id}/pwd")
    public void updatePassword(@Positive @PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
        @Validated({UpdatePasswordGroup.class}) @RequestBody @ApiParam(value = "新旧密码",
            required = true) UserPassword userPassword) {
        String oldPassword = userPassword.getOldPassword();
        String newPassword = userPassword.getNewPassword();
        userService.updatePassword(id, oldPassword, newPassword);
    }

    /**
     * 删除用户。
     *
     * @param id
     *            用户主键id
     * @param userDelete
     *            部门主键对象
     */
    @ApiOperation(value = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@Positive @PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
        @Validated({SingleGroup.class}) @RequestBody @ApiParam(value = "部门主键id",
            required = true) UserDelete userDelete) {
        Long departmentId = userDelete.getDepartmentId();
        userService.deleteUser(id, departmentId);
    }

    /**
     * 批量删除用户。
     *
     * @param userDelete
     *            用户与部门对象
     */
    @ApiOperation(value = "用户删除(批量)")
    @DeleteMapping
    public void deleteBatchUser(@Validated({BatchGroup.class}) @RequestBody @ApiParam(value = "用户与部门对象",
        required = true) UserDelete userDelete) {
        Long[] ids = userDelete.getUserIds();
        Long departmentId = userDelete.getDepartmentId();
        userService.deleteBatchUser(ids, departmentId);
    }

    /**
     * 查询该用户所包含的角色。
     *
     * @param id
     *            用户主键id
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            当前登录用户id
     * @return 用户所包含的角色
     */
    @ApiOperation(value = "查询用户的所有角色")
    @GetMapping("/{id}/roles")
    public Map<String, List> listUserRole(
        @Positive @PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
        @NullOrNumber @RequestParam(value = "status", required = false) @ApiParam(value = "状态") Integer status,
        @ApiIgnore Long userId) {
        // 获取当前登录用户的所有角色
        List<Role> roleList = roleService.listRole(status, userId);
        List<RoleTree> roleTrees = TreeUtil.role2Tree(roleList);

        // 获取指定用户的角色
        List<Role> roleSelectedList = roleService.listRoleByUserIdOrName(id, null);

        Map<String, List> map = new HashMap<>(2);
        map.put("roleTree", roleTrees);
        map.put("roleSelected", roleSelectedList);

        return map;
    }

    /**
     * 赋予某用户某角色。
     *
     * @param id
     *            用户id
     * @param userGrant
     *            增加和删除的角色对象
     */
    @ApiOperation(value = "赋予用户角色。")
    @PostMapping("/{id}/roles")
    public void grantUserRole(@Positive @PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
        @Validated @RequestBody @ApiParam(value = "增加角色与删除角色对象", required = true) UserGrant userGrant) {
        Long[] plusRoleIds = userGrant.getPlusRoleIds();
        Long[] minusRoleIds = userGrant.getMinusRoleIds();
        userService.grantUserRole(id, plusRoleIds, minusRoleIds);
    }

}
