package cn.mypandora.springboot.modular.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.mypandora.springboot.core.util.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.vo.Token;
import cn.mypandora.springboot.modular.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * LoginController
 *
 * @author hankaibo
 * @date 2019/6/19
 */
@Api(tags = "登录管理")
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录，返回token。 关于当前登录用户是否有操作部门、角色、资源的权限，有如下两种方案。 方案一：
     * 用户登录时，获取当前登录用户的userId,departmentIds,roleIds,roleCodes,resourceIds,resourceCodes，并作为token数据返回给前端。
     * 用户操作部门、角色和资源时，从token中取出上述资源，进行用户是否有权限的判断。 优点：每次从token中获取，不用再查询数据库，减少了sql操作。
     * 缺点：如果动态修改了当前用户的部门、角色和资源，只要他不退出重新登录就依然有操作权限。
     * <p>
     * 方案二： 用户登录时，获取当前登录用户的userId，并作为token数据返回给前端。
     * 用户操作部门、角色和资源时，从token中取出userId，通过userId从数据库中查询出相应的部门、角色和资源。然后再进行操作权限的判断。
     * 优点：每次都是基于不变的用户userId动态从数据库中查询最新权限，修改可即时生效。 缺点：每次操作都查询数据库，sql压力大。
     *
     * @param request
     *            request
     * @return token
     */
    @ApiOperation(value = "用户登录", notes = "输入名称与密码，返回token与role信息。")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "authorization", paramType = "header", dataType = "String", access = "hidden"),
        @ApiImplicitParam(name = "username", value = "用户名称", required = true, paramType = "body"),
        @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "body")})
    @PostMapping("/login")
    public Token login(HttpServletRequest request) {
        // 获取用户信息
        Map<String, String> params = RequestResponseUtil.getRequestBodyMap(request);
        String username = params.get("username");
        return userService.login(username);
    }

    /**
     * 用户退出，清空token.
     *
     * @param authorization
     *            token
     */
    @ApiOperation(value = "用户登出", notes = "带token。")
    @PostMapping("/logout")
    public void logout(@RequestHeader(value = "authorization") String authorization) {
        userService.logout(authorization);
    }

}
