package cn.mypandora.springboot.core.shiro.realm;

import cn.mypandora.springboot.core.shiro.token.JwtToken;
import cn.mypandora.springboot.core.utils.JsonWebTokenUtil;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Map;
import java.util.Set;

/**
 * JwtRealm
 *
 * @author hankaibo
 * @date 2019/1/15
 */
public class JwtRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String payload = (String) principals.getPrimaryPrincipal();
        // likely to be json, parse it:
        if (payload.startsWith("jwt:") && payload.charAt(4) == '{'
                && payload.charAt(payload.length() - 1) == '}') {

            Map<String, Object> payloadMap = JsonWebTokenUtil.readValue(payload.substring(4));
            Set<String> roles = JsonWebTokenUtil.split((String) payloadMap.get("roles"));
            Set<String> permissions = JsonWebTokenUtil.split((String) payloadMap.get("perms"));
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            if (null != roles && !roles.isEmpty()) {
                info.setRoles(roles);
            }
            if (null != permissions && !permissions.isEmpty()){
                info.setStringPermissions(permissions);
            }
            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof JwtToken)) {
            return null;
        }
        JwtToken jwtToken = (JwtToken) token;
        String jwt = (String) jwtToken.getCredentials();
        String payload = null;
        try {
            // 预先解析Payload
            // 没有做任何的签名校验
            payload = JsonWebTokenUtil.parseJwtPayload(jwt);
        } catch (MalformedJwtException e) {
            throw new AuthenticationException("errJwt");     //令牌格式错误
        } catch (Exception e) {
            throw new AuthenticationException("errsJwt");    //令牌无效
        }
        if (null == payload) {
            throw new AuthenticationException("errJwt");    //令牌无效
        }
        return new SimpleAuthenticationInfo("jwt:" + payload, jwt, this.getName());
    }
}
