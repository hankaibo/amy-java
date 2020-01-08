package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import cn.mypandora.springboot.core.util.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import cn.mypandora.springboot.modular.system.model.vo.Token;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
import cn.mypandora.springboot.modular.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public LoginController(UserService userService, RoleService roleService, ResourceService resourceService, StringRedisTemplate redisTemplate) {
        this.userService = userService;
        this.roleService = roleService;
        this.resourceService = resourceService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 用户登录，返回token。
     *
     * @param request request
     * @return token
     */
    @ApiOperation(value = "用户登录", notes = "输入名称与密码，返回token与role信息。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "String", access = "hidden"),
            @ApiImplicitParam(name = "username", value = "用户名称", required = true, paramType = "body"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "body")
    })
    @PostMapping("/login")
    public Token login(HttpServletRequest request) {
        Map<String, String> params = RequestResponseUtil.getRequestBodyMap(request);
        String username = params.get("username");
        Long userId = userService.getUserByIdOrName(null, username).getId();

        List<String> roleList = roleService.listRoleByUserIdOrName(null, username).stream().map(item -> item.getCode()).collect(Collectors.toList());
        String roles = String.join(",", roleList);

        List<String> resourceList = resourceService.listResourceByUserIdOrName(null, username).stream().map(item -> item.getCode()).collect(Collectors.toList());
        String resources = String.join(",", resourceList);

        // 时间以秒计算,token有效刷新时间是token有效过期时间的2倍
        long refreshPeriodTime = 36000L;
        String jwt = JsonWebTokenUtil.createJwt(UUID.randomUUID().toString(), "token-server", username, refreshPeriodTime >> 1, userId, roles, resources);
        // 将签发的JWT存储到Redis： {JWT-ID-{username} , jwt}
        redisTemplate.opsForValue().set(StringUtils.upperCase("JWT-ID-" + username), jwt, refreshPeriodTime, TimeUnit.SECONDS);

        Token token = new Token();
        token.setToken(jwt);
        token.setRole(roles);
        token.setResources(resources);

        return token;
    }

    /**
     * 用户退出，清空token.
     *
     * @param authorization token
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
