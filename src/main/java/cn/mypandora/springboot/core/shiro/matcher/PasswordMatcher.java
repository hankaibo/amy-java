package cn.mypandora.springboot.core.shiro.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

/**
 * PasswordMatcher 注：自定义比对器，供realm使用。
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Component
public class PasswordMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        return token.getPrincipal().toString().equals(info.getPrincipals().getPrimaryPrincipal().toString())
            && token.getCredentials().toString().equals(info.getCredentials().toString());
    }

}
