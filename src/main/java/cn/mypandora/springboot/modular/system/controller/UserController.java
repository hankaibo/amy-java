package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
import cn.mypandora.springboot.modular.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UserController
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private ResourceService resourceService;

    @Autowired
    public UserController(UserService userService, RoleService roleService, ResourceService resourceService) {
        this.userService = userService;
        this.roleService = roleService;
        this.resourceService = resourceService;
    }

    /**
     * 根据token获取用户信息。
     *
     * @param authorization token
     * @return 用户信息，用户权限对应的菜单信息
     */
    @ApiOperation(value = "获取当前登录用户信息", notes = "根据用户的token，查询用户的相关信息。")
    @GetMapping("/info")
    public Map<String, Object> getUserAndMenu(@RequestHeader(value = "Authorization") String authorization) {
        String jwt = JsonWebTokenUtil.unBearer(authorization);
        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserByIdOrName(null, jwtAccount.getAppId());
        user.setPassword(null);
        user.setSalt(null);
        List<String> menuList = resourceService.listResourceMenuByUserId(user.getId()).stream().map(Resource::getCode).distinct().collect(Collectors.toList());
        map.put("user", user);
        map.put("menuList", menuList);
        return map;
    }

    /**
     * 分页查询用户数据。
     *
     * @param pageNum      页码
     * @param pageSize     每页条数
     * @param username     用户名称
     * @param phone        电话
     * @param mobile       手机
     * @param sex          性别
     * @param status       状态
     * @param departmentId 部门id
     * @return 分页数据
     */
    @ApiOperation(value = "用户列表", notes = "分页查询用户列表。")
    @GetMapping
    public PageInfo<User> pageUser(@RequestParam(value = "current", defaultValue = "1") @ApiParam(value = "页码", required = true) int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "每页条数", required = true) int pageSize,
                                   @RequestParam(value = "username", required = false) @ApiParam(value = "用户名称") String username,
                                   @RequestParam(value = "phone", required = false) @ApiParam(value = "电话") String phone,
                                   @RequestParam(value = "mobile", required = false) @ApiParam(value = "手机") String mobile,
                                   @RequestParam(value = "sex", required = false) @ApiParam(value = "性别") Byte sex,
                                   @RequestParam(value = "status", required = false) @ApiParam(value = "状态") Integer status,
                                   @RequestParam(value = "departmentId", required = false) @ApiParam(value = "部门id") Long departmentId) {
        User user = new User();
        if (StringUtils.isNotBlank(username)) {
            user.setUsername(username);
        }
        if (StringUtils.isNotBlank(phone)) {
            user.setPhone(phone);
        }
        if (StringUtils.isNotBlank(mobile)) {
            user.setMobile(mobile);
        }
        if (sex != null) {
            user.setSex(sex);
        }
        if (status != null) {
            user.setStatus(status);
        }
        if (departmentId != null) {
            user.setDepartmentId(departmentId);
        }
        return userService.pageUser(pageNum, pageSize, user);
    }


    /**
     * 新建用户。
     *
     * @param user 用户数据
     */
    @ApiOperation(value = "用户新建", notes = "根据数据新建用户。")
    @PostMapping
    public void addUser(@RequestBody @ApiParam(value = "用户数据", required = true) User user) {
        // 管理员新建用户时，如果密码为空，则统一使用默认密码。
        if (StringUtils.isBlank(user.getPassword())) {
            String defaultPassword = "123456";
            user.setPassword(defaultPassword);
        }
        userService.addUser(user);
    }


    /**
     * 查询用户详细数据。
     *
     * @param id 用户主键id
     * @return 用户信息
     */
    @ApiOperation(value = "用户详情", notes = "根据用户id查询用户详情。")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "用户不存在。");
        }
        user.setLastLoginTime(null);
        user.setCreateTime(null);
        user.setUpdateTime(null);
        return user;
    }

    /**
     * 更新用户。
     *
     * @param user 用户数据
     */
    @ApiOperation(value = "用户更新", notes = "根据用户数据更新用户。")
    @PutMapping("/{id}")
    public void updateUser(@RequestBody @ApiParam(value = "用户数据", required = true) User user) {
        userService.updateUser(user);
    }

    /**
     * 启用|禁用用户。
     *
     * @param id  用户主键id
     * @param map 状态(启用:1，禁用:0)
     */
    @ApiOperation(value = "用户状态启用禁用", notes = "根据用户id启用或禁用其状态。")
    @PatchMapping("/{id}/status")
    public void enableUser(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
                           @RequestBody @ApiParam(value = "启用(1)，禁用(0)", required = true) Map<String, Integer> map) {
        Integer status = map.get("status");
        userService.enableUser(id, status);
    }

    /**
     * 重置密码。
     *
     * @param id  用户主键id
     * @param map 新密码
     */
    @ApiOperation(value = "重置用户密码")
    @PatchMapping("/{id}/password")
    public void resetPassword(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
                              @RequestBody @ApiParam(value = "新密码", required = true) Map<String, String> map) {
        String password = map.get("password");
        userService.resetPassword(id, password);
    }

    /**
     * 修改密码。
     *
     * @param id  用户主键id
     * @param map {oldPassword: 旧密码, newPassword: 新密码}
     */
    @ApiOperation(value = "修改密码")
    @PatchMapping("/{id}/pwd")
    public void updatePassword(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
                               @RequestBody @ApiParam(value = "新旧密码", required = true) Map<String, String> map) {
        String oldPassword = map.get("oldPassword");
        User user = userService.getUserById(id);
        if (user == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "用户不存在。");
        }
        if (!user.getPassword().equals(BCrypt.hashpw(oldPassword, user.getSalt()))) {
            throw new CustomException(HttpStatus.CONFLICT.value(), "密码错误。");
        }
        String newPassword = map.get("newPassword");
        userService.resetPassword(id, newPassword);
    }

    /**
     * 删除用户。
     *
     * @param id 用户主键id
     */
    @ApiOperation(value = "用户删除", notes = "根据用户Id删除用户。")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id) {
        userService.deleteUser(id);
    }

    /**
     * 批量删除用户。
     *
     * @param ids 用户id数组
     */
    @ApiOperation(value = "用户删除(批量)", notes = "根据用户Id批量删除用户。")
    @DeleteMapping
    public void deleteBatchUser(@RequestBody @ApiParam(value = "用户主键数组ids", required = true) Long[] ids) {
        userService.deleteBatchUser(ids);
    }

    /**
     * 查询该用户所包含的角色。
     *
     * @param id     角色主键id
     * @param status 状态(启用:1，禁用:0)
     * @return 用户所包含的角色
     */
    @ApiOperation(value = "查询用户的所有角色", notes = "根据用户id查询其包含的所有角色。")
    @GetMapping("/{id}/roles")
    public Map<String, List<Role>> listUserRole(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
                                                @RequestParam(value = "status", required = false) @ApiParam(value = "状态") Integer status) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("status", status);
        List<Role> roleList = roleService.listRoleByCondition(params);
        List<Role> roleSelectedList = roleService.listRoleByUserIdOrName(id, null);

        Map<String, List<Role>> map = new HashMap<>(2);
        map.put("roleList", roleList);
        map.put("roleSelectedList", roleSelectedList);
        return map;
    }

    /**
     * 赋予某用户某角色。
     *
     * @param id  用户id
     * @param map 增加和删除的角色对象
     */
    @ApiOperation(value = "赋予用户一些角色。", notes = "根据用户id赋予其一些角色。")
    @PostMapping("/{id}/roles")
    public void grantUserRole(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
                              @RequestBody @ApiParam(value = "增加角色与删除角色对象", required = true) Map<String, List<Long>> map) {

        List<Long> plusRole = map.get("plusRole");
        List<Long> minusRole = map.get("minusRole");
        long[] plusId = plusRole.stream().distinct().mapToLong(it -> it).toArray();
        long[] minusId = minusRole.stream().distinct().mapToLong(it -> it).toArray();
        userService.grantUserRole(id, plusId, minusId);
    }

}
