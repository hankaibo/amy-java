package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.core.utils.JsonWebTokenUtil;
import cn.mypandora.springboot.core.utils.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.vo.Token;
import cn.mypandora.springboot.modular.system.service.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * LoginController
 *
 * @author hankaibo
 * @date 2019/6/19
 */
@Api(tags = "登录管理", description = "登录登出相关接口")
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private UserService userService;
    private StringRedisTemplate redisTemplate;

    @Autowired
    public LoginController(UserService userService, StringRedisTemplate redisTemplate) {
        this.userService = userService;
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
            @ApiImplicitParam(name = "username", value = "用户名称", required = true, paramType = "body"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "body")
    })
    @PostMapping("/login")
    public Result<Token> login(HttpServletRequest request) {
        Map<String, String> params = RequestResponseUtil.getRequestBodyMap(request);
        String username = params.get("username");
        // TODO 根据用户id/姓名获取用户角色、权限
        String roles = "admin";
        String perms = null;
        // 时间以秒计算,token有效刷新时间是token有效过期时间的2倍
        long refreshPeriodTime = 36000L;
        String jwt = JsonWebTokenUtil.issuejwt(UUID.randomUUID().toString(), username, "token-server",
                refreshPeriodTime >> 1, roles, perms, SignatureAlgorithm.HS512);
        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
        redisTemplate.opsForValue().set("JWT-SESSION-" + username, jwt, refreshPeriodTime, TimeUnit.SECONDS);
        Token token = new Token();
        token.setToken(jwt);
        token.setRole(roles);

        return ResultGenerator.success(token);
    }

}
