package cn.mypandora.springboot.core.shiro.realm;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

/**
 * AonModularRealmAuthenticator 主要是针对不同的请求使用不同的realm验证。 登录使用PasswordRealm，其它接口使用JwtRealm。
 *
 * @author hankaibo
 * @date 2019/6/18
 */
public class AonModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        assertRealmsConfigured();
        List<Realm> realms =
            this.getRealms().stream().filter(realm -> realm.supports(authenticationToken)).collect(toList());
        if (realms.size() == 1) {
            return doSingleRealmAuthentication(realms.iterator().next(), authenticationToken);
        } else {
            return doMultiRealmAuthentication(realms, authenticationToken);
        }
    }

}
