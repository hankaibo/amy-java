package cn.mypandora.springboot.core.shiro.filter;


import cn.mypandora.springboot.modular.system.model.Message;
import cn.mypandora.springboot.core.shiro.token.PasswordToken;
import cn.mypandora.springboot.core.utils.CommonUtil;
import cn.mypandora.springboot.core.utils.IpUtil;
import cn.mypandora.springboot.core.utils.RequestResponseUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static tk.mybatis.spring.annotation.MapperScannerRegistrar.LOGGER;

/* *
 * @Author tomsun28
 * @Description 基于 用户名密码 的认证过滤器
 * @Date 20:18 2018/2/10
 */
@Slf4j
public class PasswordFilter extends AccessControlFilter {

    private StringRedisTemplate redisTemplate;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        Subject subject = getSubject(request, response);
        // 如果其已经登录，再此发送登录请求
        if (null != subject && subject.isAuthenticated()) {
            return true;
        }
        //  拒绝，统一交给 onAccessDenied 处理
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        // 判断若为获取登录注册加密动态秘钥请求
        if (isPasswordTokenGet(request)) {
            //动态生成秘钥，redis存储秘钥供之后秘钥验证使用，设置有效期5秒用完即丢弃
            String tokenKey = CommonUtil.getRandomString(16);
            String userKey = CommonUtil.getRandomString(6);
            try {
                redisTemplate.opsForValue().set("TOKEN_KEY_" + IpUtil.getIpFromRequest(WebUtils.toHttp(request)).toUpperCase() + userKey.toUpperCase(), tokenKey, 5, TimeUnit.SECONDS);
                // 动态秘钥response返回给前端
                Message message = new Message();
                message.ok(1000, "issued tokenKey success")
                        .addData("tokenKey", tokenKey).addData("userKey", userKey.toUpperCase());
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);

            } catch (Exception e) {
                LOGGER.warn("签发动态秘钥失败" + e.getMessage(), e);
                Message message = new Message();
                message.ok(1000, "issued tokenKey fail");
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
            }
            return false;
        }

        // 判断是否是登录请求
        if (isPasswordLoginPost(request)) {

            AuthenticationToken authenticationToken = null;
            try {
                authenticationToken = createPasswordToken(request);
            } catch (Exception e) {
                // response 告知无效请求
                Message message = new Message().error(1111, "error request");
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
                return false;
            }

            Subject subject = getSubject(request, response);
            try {
                subject.login(authenticationToken);
                //登录认证成功,进入请求派发json web token url资源内
                return true;
            } catch (AuthenticationException e) {
                LOGGER.warn(authenticationToken.getPrincipal() + "::" + e.getMessage());
                // 返回response告诉客户端认证失败
                Message message = new Message().error(1002, "login fail");
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
                return false;
            } catch (Exception e) {
                LOGGER.error(authenticationToken.getPrincipal() + "::认证异常::" + e.getMessage(), e);
                // 返回response告诉客户端认证失败
                Message message = new Message().error(1002, "login fail");
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
                return false;
            }
        }
        // 判断是否为注册请求,若是通过过滤链进入controller注册
        if (isAccountRegisterPost(request)) {
            return true;
        }
        // 之后添加对账户的找回等

        // response 告知无效请求
        Message message = new Message().error(1111, "error request");
        RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
        return false;
    }

    private boolean isPasswordTokenGet(ServletRequest request) {

        String tokenKey = RequestResponseUtil.getParameter(request, "tokenKey");

        return (request instanceof HttpServletRequest)
                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("GET")
                && null != tokenKey && "get".equals(tokenKey);
    }

    private boolean isPasswordLoginPost(ServletRequest request) {

        Map<String, String> map = RequestResponseUtil.getRequestBodyMap(request);
        String password = map.get("password");
        String timestamp = map.get("timestamp");
        String methodName = map.get("methodName");
        String appId = map.get("appId");
        return (request instanceof HttpServletRequest)
                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("POST")
                && null != password
                && null != timestamp
                && null != methodName
                && null != appId
                && methodName.equals("login");
    }

    private boolean isAccountRegisterPost(ServletRequest request) {

        Map<String, String> map = RequestResponseUtil.getRequestBodyMap(request);
        String uid = map.get("uid");
        String username = map.get("username");
        String methodName = map.get("methodName");
        String password = map.get("password");
        return (request instanceof HttpServletRequest)
                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("POST")
                && null != username
                && null != password
                && null != methodName
                && null != uid
                && methodName.equals("register");
    }

    private AuthenticationToken createPasswordToken(ServletRequest request) throws Exception {

        Map<String, String> map = RequestResponseUtil.getRequestBodyMap(request);
        String appId = map.get("appId");
        String timestamp = map.get("timestamp");
        String password = map.get("password");
        String host = IpUtil.getIpFromRequest(WebUtils.toHttp(request));
        String userKey = map.get("userKey");
        String tokenKey = redisTemplate.opsForValue().get("TOKEN_KEY_" + host.toUpperCase() + userKey);
        return new PasswordToken(appId, password, timestamp, host, tokenKey);
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
