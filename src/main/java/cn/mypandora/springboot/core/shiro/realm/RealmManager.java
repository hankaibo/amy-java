package cn.mypandora.springboot.core.shiro.realm;

import cn.mypandora.springboot.core.shiro.matcher.JwtMatcher;
import cn.mypandora.springboot.core.shiro.matcher.PasswordMatcher;
import cn.mypandora.springboot.core.shiro.provider.AccountProvider;
import cn.mypandora.springboot.core.shiro.token.JwtToken;
import cn.mypandora.springboot.core.shiro.token.PasswordToken;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * RealmManager
 *
 * @author hankaibo
 * @date 2019/1/15
 */
@Component
public class RealmManager {

    private PasswordMatcher passwordMatcher;
    private JwtMatcher jwtMatcher;
    private AccountProvider accountProvider;

    @Autowired
    public RealmManager(AccountProvider accountProvider, PasswordMatcher passwordMatcher, JwtMatcher jwtMatcher) {
        this.accountProvider = accountProvider;
        this.passwordMatcher = passwordMatcher;
        this.jwtMatcher = jwtMatcher;
    }

    /**
     * 一个密码一个jwt验证
     * @return
     */
    public List<Realm> initGetRealm() {
        List<Realm> realmList = new LinkedList<>();
        // ----- password
        PasswordRealm passwordRealm = new PasswordRealm();
        passwordRealm.setAccountProvider(accountProvider);
        passwordRealm.setCredentialsMatcher(passwordMatcher);
        passwordRealm.setAuthenticationTokenClass(PasswordToken.class);
        realmList.add(passwordRealm);
        // ----- jwt
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setCredentialsMatcher(jwtMatcher);
        jwtRealm.setAuthenticationTokenClass(JwtToken.class);
        realmList.add(jwtRealm);
        return Collections.unmodifiableList(realmList);
    }
}
