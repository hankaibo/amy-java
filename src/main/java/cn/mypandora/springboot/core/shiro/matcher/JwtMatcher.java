package cn.mypandora.springboot.core.shiro.matcher;

import cn.mypandora.springboot.core.utils.JsonWebTokenUtil;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

/**
 * JwtMatcher
 * 注：自定义比对器，供realm使用。
 *
 * @author hankaibo
 * @date 2019/6/19
 */
@Component
public class JwtMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String jwt = (String) info.getCredentials();
        JwtAccount jwtAccount = null;
        try {
            jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        } catch (SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | ExpiredJwtException e) {
            throw new AuthenticationException("errJwt");
        } catch (Exception e) {
            throw new AuthenticationException("expiredJwt");
        }
        if (null == jwtAccount) {
            throw new AuthenticationException("errJwt");
        }
        return true;
    }

}
