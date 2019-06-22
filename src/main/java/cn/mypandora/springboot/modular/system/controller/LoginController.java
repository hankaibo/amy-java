package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.core.utils.JsonWebTokenUtil;
import cn.mypandora.springboot.core.utils.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.modular.system.service.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    /**
     * 用户登录，返回token。
     *
     * @param request request
     * @return token
     */
    @PostMapping("/login")
    public Result<Map> login(HttpServletRequest request) {
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
        Map<String, String> token = new HashMap<>(2);
        token.put("token", jwt);
        token.put("role", roles);

        return ResultGenerator.success(token);
    }

}
