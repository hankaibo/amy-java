package cn.mypandora.springboot.core.shiro.realm;

import cn.mypandora.springboot.modular.system.model.Account;
import cn.mypandora.springboot.core.shiro.provider.AccountProvider;
import cn.mypandora.springboot.core.shiro.token.PasswordToken;
import cn.mypandora.springboot.core.utils.MD5Util;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * PasswordRealm
 *
 * @author hankaibo
 * @date 2019/1/15
 */
public class PasswordRealm extends AuthorizingRealm {
    private AccountProvider accountProvider;

    //此Realm只支持PasswordToken
    @Override
    public Class<?> getAuthenticationTokenClass() {
        return PasswordToken.class;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof PasswordToken)) {
            return null;
        }

        if (null == token.getPrincipal() || null == token.getCredentials()) {
            throw new UnknownAccountException();
        }
        String appId = (String) token.getPrincipal();
        Account account = accountProvider.loadAccount(appId);
        if (account != null) {
            // 用盐对密码进行MD5加密
            ((PasswordToken) token).setPassword(MD5Util.md5(((PasswordToken) token).getPassword() + account.getSalt()));
            return new SimpleAuthenticationInfo(appId, account.getPassword(), getName());
        } else {
            return new SimpleAuthenticationInfo(appId, "", getName());
        }
    }

    public void setAccountProvider(AccountProvider accountProvider) {
        this.accountProvider = accountProvider;
    }
}
