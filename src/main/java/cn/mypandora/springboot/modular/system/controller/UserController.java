package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.core.utils.JsonWebTokenUtil;
import cn.mypandora.springboot.core.utils.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import cn.mypandora.springboot.modular.system.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * UserController
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Api(tags = "用户管理", description = "用户相关接口")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 根据token获取用户信息。
     *
     * @param request request
     * @return User
     */
    @ApiOperation(value = "获取当前登录用户信息", notes = "根据用户的token，查询用户的相关信息返回。")
    @GetMapping("/info")
    public Result<User> userInfo(HttpServletRequest request) {
        String jwt = RequestResponseUtil.getHeader(request, "authorization");
        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        User user = userService.selectUserByIdOrName(null, jwtAccount.getAppId());

        return ResultGenerator.success(user);
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
    public Result<PageInfo<User>> listAll(@RequestParam(value = "pageNum", defaultValue = "1") @ApiParam(value = "页码", required = true) int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "每页条数", required = true) int pageSize) {
        return ResultGenerator.success(userService.selectUserList(pageNum, pageSize, null));
    }

    /**
     * 查询用户详细数据。
     *
     * @param id 用户主键id
     * @return 数据
     */
    @ApiOperation(value = "用户详情", notes = "根据用户id查询用户详情。")
    @GetMapping("/{id}")
    public Result<User> listById(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id) {
        User user = userService.selectUserByIdOrName(id, null);
        user.setPassword(null);
        user.setSalt(null);
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
    public Result remove(@RequestBody @ApiParam(value = "用户主键数组ids", required = true) Map<String, Long[]> ids) {
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
    @PutMapping
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
    @PutMapping("/{id}")
    public Result enable(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long id,
                         @RequestBody @ApiParam(value = "启用(1)，禁用(0)", required = true) Map<String, Integer> status) {
        Integer s = status.get("status");
        boolean result = userService.enableUser(id, s);
        return result ? ResultGenerator.success() : ResultGenerator.failure();
    }

}
