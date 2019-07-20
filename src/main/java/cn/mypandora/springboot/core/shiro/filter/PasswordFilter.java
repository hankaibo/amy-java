package cn.mypandora.springboot.core.shiro.filter;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.shiro.token.PasswordToken;
import cn.mypandora.springboot.core.utils.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.vo.Message;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Map;

/**
 * PasswordFilter
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Slf4j
public class PasswordFilter extends AccessControlFilter {

    private StringRedisTemplate redisTemplate;

    @Autowired
    public PasswordFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        // 如果其已经登录，再此发送登录请求
        //  拒绝，统一交给 onAccessDenied 处理
        return null != subject && subject.isAuthenticated();
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken authenticationToken = null;
        try {
            authenticationToken = createPasswordToken(request);
        } catch (Exception e) {
            // response 告知无效请求
            Message message = new Message().error(1111, "error request");
            Result result = new Result();
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
            return false;
        }

        Subject subject = getSubject(request, response);
        try {
            subject.login(authenticationToken);
            //登录认证成功,进入请求派发json web token url资源内
            return true;
        } catch (AuthenticationException e) {
            log.warn(authenticationToken.getPrincipal() + "::" + e.getMessage());
            // 返回response告诉客户端认证失败
            Message message = new Message().error(1002, "login fail");
            RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
            return false;
        } catch (Exception e) {
            log.error(authenticationToken.getPrincipal() + "::认证异常::" + e.getMessage(), e);
            // 返回response告诉客户端认证失败
            Message message = new Message().error(1002, "login fail");
            RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
            return false;
        }
    }

    private AuthenticationToken createPasswordToken(ServletRequest request) throws Exception {
        Map<String, String> map = RequestResponseUtil.getRequestBodyMap(request);
        String username = map.get("username");
        String password = map.get("password");
        return new PasswordToken(username, password);
    }

}
