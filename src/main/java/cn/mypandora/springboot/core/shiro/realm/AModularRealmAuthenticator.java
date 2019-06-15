package cn.mypandora.springboot.core.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * AModularRealmAuthenticator
 *
 * @author hankaibo
 * @date 2019/1/15
 */
public class AModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        assertRealmsConfigured();
        // 应该是过滤认证器
        // 覆盖原实现开始------------------------------------
        List<Realm> realms = this.getRealms()
                .stream()
                .filter(realm -> {
                    return realm.supports(authenticationToken);
                })
                .collect(toList());
        // 覆盖原实现结束------------------------------------
        if (realms.size() == 1) {
            return this.doSingleRealmAuthentication(realms.iterator().next(), authenticationToken);
        } else {
            return this.doMultiRealmAuthentication(realms, authenticationToken);
        }
    }
}
