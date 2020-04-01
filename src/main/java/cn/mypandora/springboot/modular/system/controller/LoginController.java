package cn.mypandora.springboot.modular.system.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import cn.mypandora.springboot.core.util.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import cn.mypandora.springboot.modular.system.model.vo.Token;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
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
    private RoleService roleService;
    private ResourceService resourceService;
    private StringRedisTemplate redisTemplate;

    @Autowired
    public LoginController(UserService userService, RoleService roleService, ResourceService resourceService,
        StringRedisTemplate redisTemplate) {
        this.userService = userService;
        this.roleService = roleService;
        this.resourceService = resourceService;
        this.redisTemplate = redisTemplate;
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
        @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "String", access = "hidden"),
        @ApiImplicitParam(name = "username", value = "用户名称", required = true, paramType = "body"),
        @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "body")})
    @PostMapping("/login")
    public Token login(HttpServletRequest request) {
        // 获取用户信息
        Map<String, String> params = RequestResponseUtil.getRequestBodyMap(request);
        String username = params.get("username");
        User user = userService.getUserByIdOrName(null, username);
        Long userId = user.getId();

        // 获取角色信息
        List<Role> roleList = roleService.listRoleByUserIdOrName(null, username);
        List<String> roleCodeList = roleList.stream().map(Role::getCode).collect(Collectors.toList());
        List<Long> roleIdList = roleList.stream().map(Role::getId).collect(Collectors.toList());

        String roleCodes = StringUtils.join(roleCodeList, ',');
        String roleIds = StringUtils.join(roleIdList, ',');

        // 获取资源信息
        List<Resource> resourceList =
            resourceService.listResourceByUserIdOrName(null, username, null, StatusEnum.ENABLED.getValue());
        List<String> resourceCodeList = resourceList.stream().map(Resource::getCode).collect(Collectors.toList());

        String resourceCodes = StringUtils.join(resourceCodeList, ',');

        // 时间以秒计算,token有效刷新时间是token有效过期时间的2倍
        long refreshPeriodTime = 36000L;

        // 生成jwt并将签发的JWT存储到Redis： {JWT-ID-{username} , jwt}
        String jwt = JsonWebTokenUtil.createJwt(UUID.randomUUID().toString(), "token-server", username,
            refreshPeriodTime >> 1, userId, roleIds, roleCodes, resourceCodes);
        redisTemplate.opsForValue().set(StringUtils.upperCase("JWT-ID-" + username), jwt, refreshPeriodTime,
            TimeUnit.SECONDS);

        // 返回给前台数据
        Token token = new Token();
        token.setToken(jwt);
        token.setRoles(roleCodes);
        token.setResources(resourceCodes);

        return token;
    }

    /**
     * 用户退出，清空token.
     *
     * @param authorization
     *            token
     * @return 成功或异常
     */
    @ApiOperation(value = "用户登出", notes = "带token。")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(value = "Authorization") String authorization) {
        SecurityUtils.getSubject().logout();
        String jwt = JsonWebTokenUtil.unBearer(authorization);
        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        String username = jwtAccount.getAppId();
        if (StringUtils.isEmpty(username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户为空。");
        }
        String jwtInRedis = redisTemplate.opsForValue().get(StringUtils.upperCase("JWT-ID-" + username));
        if (StringUtils.isEmpty(jwtInRedis)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户为空。");
        }
        redisTemplate.opsForValue().getOperations().delete(StringUtils.upperCase("JWT-ID-" + username));
        return ResponseEntity.ok().body("成功");
    }

}
