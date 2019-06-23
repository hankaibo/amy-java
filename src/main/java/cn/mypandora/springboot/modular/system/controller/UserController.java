package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.core.utils.JsonWebTokenUtil;
import cn.mypandora.springboot.core.utils.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import cn.mypandora.springboot.modular.system.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * UserController
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Api(tags = "用户管理", description = "用户操作相关接口")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Resource
    private UserService userService;


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
        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt, JsonWebTokenUtil.SECRET_KEY);
        User user = userService.selectByIdOrName(null, jwtAccount.getAppId());

        return ResultGenerator.success(user);
    }

    /**
     * 分页查询用户数据
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 分页用户数据
     */
    @ApiOperation(value = "用户列表", notes = "查询用户列表")
    @GetMapping
    public Result<PageInfo<User>> listAll(@RequestParam(value = "pageNum", defaultValue = "1") @ApiParam(value = "页码", required = true) int pageNum,
                                          @RequestParam(value = "sizeSize", defaultValue = "10") @ApiParam(value = "每页条数", required = true) int pageSize) {
        return ResultGenerator.success(userService.selectByPage(pageNum, pageSize, null));
    }

    /**
     * 新建用户。
     *
     * @param user 用户数据
     * @return 成功
     */
    @ApiOperation(value = "新建用户")
    @PostMapping
    public Result insert(@RequestBody @ApiParam(value = "用户json数据", required = true) User user) {
        userService.addUser(user);
        return ResultGenerator.success();
    }

    /**
     * 删除用户。
     *
     * @param userId 用户主键id
     * @return 成功
     */
    @ApiOperation(value = "删除用户", notes = "根据用户Id删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") @ApiParam(value = "用户主键id", required = true) Long userId) {
        userService.deleteUser(userId);
        return ResultGenerator.success();
    }

    /**
     * 更新用户。
     *
     * @param user 用户信息
     * @return 成功
     */
    @ApiOperation(value = "更新用户")
    @PutMapping
    public Result update(@RequestBody @ApiParam(value = "用户数据", required = true) User user) {
        userService.updateUser(user);
        return ResultGenerator.success();
    }

}
