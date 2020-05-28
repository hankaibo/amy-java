package cn.mypandora.springboot.core.shiro.filter;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpStatus;

import cn.mypandora.springboot.core.shiro.token.PasswordToken;
import cn.mypandora.springboot.core.util.RequestResponseUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * PasswordFilter
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Slf4j
public class PasswordFilter extends AccessControlFilter {

    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param mappedValue
     *            map
     * @return boolean
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        // 如果其已经登录，再此发送登录请求
        // 拒绝，统一交给 onAccessDenied 处理
        return null != subject && subject.isAuthenticated();
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     *
     * @param request
     *            request
     * @param response
     *            response
     * @return boolean
     * @throws Exception
     *             认证失败异常
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken authenticationToken;
        try {
            authenticationToken = createPasswordToken(request);
        } catch (Exception e) {
            // response 告知无效请求
            RequestResponseUtil.responseWrite(HttpStatus.BAD_REQUEST.value(), "参数错误", response);
            return false;
        }

        Subject subject = getSubject(request, response);
        try {
            subject.login(authenticationToken);
            // 登录认证成功,进入请求派发json web token url资源内
            return true;
        } catch (DisabledAccountException e) {
            RequestResponseUtil.responseWrite(HttpStatus.BAD_REQUEST.value(), "用户账号被禁用。", response);
            return false;
        } catch (AuthenticationException e) {
            log.warn(authenticationToken.getPrincipal() + "::" + e.getMessage());
            // 返回response告诉客户端认证失败
            RequestResponseUtil.responseWrite(HttpStatus.UNAUTHORIZED.value(), "用户名/密码错误", response);
            return false;
        } catch (Exception e) {
            log.error(authenticationToken.getPrincipal() + "::认证异常::" + e.getMessage(), e);
            // 返回response告诉客户端认证失败
            RequestResponseUtil.responseWrite(HttpStatus.UNAUTHORIZED.value(), "用户名/密码错误", response);
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
