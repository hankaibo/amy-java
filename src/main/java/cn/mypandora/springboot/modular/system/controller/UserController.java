package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.modular.system.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * UserController
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@RestController
@RequestMapping("/api/v1/users")
@Api(value = "User API接口", tags = "用户管理", description = "User API接口")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @ApiOperation(value = "用户列表", notes = "查询用户列表")
    @GetMapping
    public Result<PageInfo> listAll(@RequestParam(value = "page", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        return ResultGenerator.success(userService.selectByPage(pageNum, pageSize, null));
    }

    @ApiOperation(value = "新建用户")
    @PostMapping
    public Result<User> insert(User user) {
        userService.addUser(user);
        return ResultGenerator.success();
    }

    @ApiOperation(value = "删除用户", notes = "根据用户Id删除")
    @DeleteMapping("/{id}")
    public Result<User> remove(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResultGenerator.success();
    }

    @ApiOperation(value = "更新用户")
    @PutMapping
    public Result<User> update(User user) {
        userService.updateUser(user);
        return ResultGenerator.success();
    }

}
