package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.core.utils.JsonWebTokenUtil;
import cn.mypandora.springboot.core.utils.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.service.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * LoginController
 *
 * @author hankaibo
 * @date 2019/6/19
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api(value = "Login API接口", tags = "登录管理", description = "Login API接口")
public class LoginController {

    private UserService userService;
    private StringRedisTemplate redisTemplate;

    @Autowired
    public LoginController(UserService userService, StringRedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/login")
    public Result<User> login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = RequestResponseUtil.getRequestBodyMap(request);
        String username = params.get("username");
        User user = userService.selectByIdOrName(null, username);
        // TODO 根据用户id获取用户角色
//        String roles = userService.selectRoleByUserId(user.getId());
        String roles = "admin";
        // 时间以秒计算,token有效刷新时间是token有效过期时间的2倍
        long refreshPeriodTime = 36000L;
        String jwt = JsonWebTokenUtil.issuejwt(UUID.randomUUID().toString(), username, "token-server",
                refreshPeriodTime >> 1, roles, null, SignatureAlgorithm.HS512);
        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
        redisTemplate.opsForValue().set("JWT-SESSION-" + username, jwt, refreshPeriodTime, TimeUnit.SECONDS);
        user.setPassword(null);
        user.setSalt(null);
        user.setToken(jwt);

        return ResultGenerator.success(user);
    }
}
