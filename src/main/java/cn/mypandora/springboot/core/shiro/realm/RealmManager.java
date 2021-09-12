package cn.mypandora.springboot.core.shiro.realm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.mypandora.springboot.core.shiro.matcher.JwtMatcher;
import cn.mypandora.springboot.core.shiro.matcher.PasswordMatcher;
import cn.mypandora.springboot.core.shiro.token.JwtToken;
import cn.mypandora.springboot.core.shiro.token.PasswordToken;
import cn.mypandora.springboot.modular.system.service.UserService;

/**
 * Realm管理器。 需要注入自己的接口，判断用户认证结果、授权结果。
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Component
public class RealmManager {

    /**
     * 1. 注入UserService接口，以供登录时调用数据库获取用户进行比对。 2. 分别针对登录与接口注入相应的匹配器进行规则匹配。
     * <p>
     * 基于username/password 形式登录时的接口与匹配器
     */
    private UserService userService;
    private PasswordMatcher passwordMatcher;
    private JwtMatcher jwtMatcher;

    @Autowired
    public RealmManager(UserService userService, PasswordMatcher passwordMatcher, JwtMatcher jwtMatcher) {
        this.userService = userService;
        this.passwordMatcher = passwordMatcher;
        this.jwtMatcher = jwtMatcher;
    }

    public List<Realm> initRealms() {
        List<Realm> realmList = new LinkedList<>();

        // 基于用户名/密码的 username/password 登录
        PasswordRealm passwordRealm = new PasswordRealm(userService);
        passwordRealm.setCredentialsMatcher(passwordMatcher);
        passwordRealm.setAuthenticationTokenClass(PasswordToken.class);
        realmList.add(passwordRealm);
        // 基于 jwt
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setCredentialsMatcher(jwtMatcher);
        jwtRealm.setAuthenticationTokenClass(JwtToken.class);
        realmList.add(jwtRealm);
        return Collections.unmodifiableList(realmList);
    }

}
