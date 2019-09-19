package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import cn.mypandora.springboot.core.util.RequestResponseUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
     * @param request request
     * @return 用户信息，用户权限对应的菜单信息
     */
    @ApiOperation(value = "获取当前登录用户信息", notes = "根据用户的token，查询用户的相关信息返回。")
    @GetMapping("/info")
    public Result<Map<String, Object>> userInfo(HttpServletRequest request) {
        String jwt = JsonWebTokenUtil.unBearer(RequestResponseUtil.getHeader(request, "Authorization"));
        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.selectUserByIdOrName(null, jwtAccount.getAppId());
        user.setPassword(null);
        user.setSalt(null);
        List<String> menuList = resourceService.selectResourceByUserId(user.getId()).stream().map(item -> item.getCode()).collect(Collectors.toList());
        map.put("user", user);
        map.put("menuList", menuList);

        return ResultGenerator.success(map);
    }

    /**
     * 分页查询用户数据。
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 分页数据
     */
    @ApiOperation(value = "用户列表", notes = "查询用户列表")
    @GetMapping
    public Result<PageInfo<User>> selectPage(@RequestParam(value = "current", defaultValue = "1") @ApiParam(value = "页码", required = true) int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "每页条数", required = true) int pageSize) {
        return ResultGenerator.success(userService.selectUserPage(pageNum, pageSize, null));
    }

    /**
     * 查询用户详细数据。
     *
     * @param id 用户主键id
     * @return 数据
     */
    @ApiOperation(value = "用户详情", notes = "根据用户id查询用户详情。")
    @GetMapping("/{id}")
    public Result<User> selectOneById(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id) {
        User user = userService.selectUserByIdOrName(id, null);
        user.setPassword(null);
        user.setSalt(null);
        user.setLastLoginTime(null);
        user.setCreateTime(null);
        user.setModifyTime(null);
        return ResultGenerator.success(user);
    }

    /**
     * 新建用户。
     *
     * @param user 用户数据
     * @return 新建结果
     */
    @ApiOperation(value = "新建用户", notes = "根据用户数据新建用户。")
    @PostMapping
    public Result insert(@RequestBody @ApiParam(value = "用户数据", required = true) User user) {
        // 管理员新建用户时，如果密码为空，则统一使用默认密码。
        if (StringUtils.isBlank(user.getPassword())) {
            String defaultPassword = "123456";
            user.setPassword(defaultPassword);
        }
        userService.addUser(user);
        return ResultGenerator.success();
    }

    /**
     * 删除用户。
     *
     * @param id 用户主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除用户", notes = "根据用户Id删除用户。")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id) {
        userService.deleteUser(id);
        return ResultGenerator.success();
    }

    /**
     * 批量删除用户。
     *
     * @param ids 用户id数组
     * @return 删除结果
     */
    @ApiOperation(value = "删除用户(批量)", notes = "根据用户Id批量删除用户。")
    @DeleteMapping
    public Result removeBatch(@RequestBody @ApiParam(value = "用户主键数组ids", required = true) Map<String, Long[]> ids) {
        userService.deleteBatchUser(StringUtils.join(ids.get("ids"), ","));
        return ResultGenerator.success();
    }

    /**
     * 更新用户。
     *
     * @param user 用户数据
     * @return 更新结果
     */
    @ApiOperation(value = "更新用户", notes = "根据用户数据更新用户。")
    @PutMapping("/{id}")
    public Result update(@RequestBody @ApiParam(value = "用户数据", required = true) User user) {
        userService.updateUser(user);
        return ResultGenerator.success();
    }

    /**
     * 启用|禁用用户。
     *
     * @param id     用户主键id
     * @param status 启用:1，禁用:0
     * @return 启用成功与否。
     */
    @ApiOperation(value = "启用|禁用用户", notes = "根据用户id启用或禁用用户。")
    @PutMapping("/{id}/status")
    public Result enable(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
                         @RequestBody @ApiParam(value = "启用(1)，禁用(0)", required = true) Map<String, Integer> status) {
        Integer s = status.get("status");
        boolean result = userService.enableUser(id, s);
        return result ? ResultGenerator.success() : ResultGenerator.failure();
    }

    /**
     * 查询该用户所包含的角色。
     *
     * @param id 角色主键id
     * @return 用户所包含的角色
     */
    @ApiOperation(value = "查询用户的所有角色")
    @GetMapping("/{id}/roles")
    public Result<Map> selectUserRole(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id) {
        List<Role> roleList = roleService.selectRoleList();
        List<Role> roleSelected = roleService.selectRoleByUserIdOrName(id, null);

        Map<String, List> map = new HashMap<>(2);
        map.put("roleList", roleList);
        map.put("roleSelected", roleSelected);
        return ResultGenerator.success(map);
    }

    /**
     * 赋予某用户某角色。
     *
     * @param id  用户id
     * @param ids 角色id数组
     * @return 成功与否
     */
    @ApiOperation(value = "赋予用户一些角色。")
    @PostMapping("/{id}/roles")
    public Result giveUserRole(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
                               @RequestBody @ApiParam(value = "角色主键数组ids", required = true) Map<String, Long[]> ids) {
        Long[] idList = ids.get("ids");
        boolean result = userService.giveUserRole(id, idList);

        return result ? ResultGenerator.success() : ResultGenerator.failure();
    }

}
