package cn.mypandora.springboot.core.shiro.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

/**
 * PasswordMatcher
 *
 * @author hankaibo
 * @date 2019/1/15
 */
@Component
public class PasswordMatcher implements CredentialsMatcher {

    /**
     * TODO 重写验证实现，不明白作者为什么这么写？
     *
     * @param authenticationToken
     * @param authenticationInfo
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {

        return authenticationToken.getPrincipal().toString().equals(authenticationInfo.getPrincipals().getPrimaryPrincipal().toString())
                && authenticationToken.getCredentials().toString().equals(authenticationInfo.getCredentials().toString());
    }
}
