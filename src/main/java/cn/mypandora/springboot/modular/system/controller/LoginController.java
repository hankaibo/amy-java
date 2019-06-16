package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api(value = "Login API接口", tags = "login", description = "Login API接口")
public class LoginController {

    @PostMapping("/login")
    public Result login(HttpServletRequest request, Map<String, Object> map) throws Exception {
        String exception = (String) request.getAttribute("shiroLoginFailure");
        String message = "账号/密码错误";

        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                log.error("UnknownAccountException");
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                log.error("IncorrectCredentialsException");
            } else if ("kaptchaValidateFailed".equals(exception)) {
                log.error("kaptchaValidateFailed");
            } else {
                log.error(exception);
            }
            return ResultGenerator.error(message);
        }
        return ResultGenerator.success();
    }
}
