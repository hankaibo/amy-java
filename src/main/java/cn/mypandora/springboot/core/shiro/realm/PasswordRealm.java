package cn.mypandora.springboot.core.shiro.realm;

import cn.mypandora.springboot.core.shiro.token.PasswordToken;
import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.modular.system.service.UserService;
import lombok.Setter;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * PasswordRealm
 *
 * @author hankaibo
 * @date 2019/6/18
 */
public class PasswordRealm extends AuthorizingRealm {

    /**
     * 注入接口，从数据库中获取用户进行比对。
     */
    @Setter
    private UserService userService;

    /**
     * Realm只支持PasswordToken
     *
     * @return Class
     */
    @Override
    public Class getAuthenticationTokenClass() {
        return PasswordToken.class;
    }

    /**
     * 这里只需要认证登录，成功之后派发 json web token 授权在那里进行
     *
     * @param principalCollection principalCollection
     * @return authorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证判断。
     *
     * @param authenticationToken 用户名/密码封装成的PasswordToken
     * @return 认证信息
     * @throws AuthenticationException 登录异常信息，例如：密码错误、用户错误、次数过多、禁用……
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (!(authenticationToken instanceof PasswordToken)) {
            return null;
        }

        if (null == authenticationToken.getPrincipal() || null == authenticationToken.getCredentials()) {
            throw new UnknownAccountException();
        }

        String username = (String) authenticationToken.getPrincipal();
        User user = userService.selectByIdOrName(null, username);
        if (user != null) {
            // TODO 用盐对authenticationToken密码进行MD5加密,再比较
            return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
        } else {
            return new SimpleAuthenticationInfo(username, "", getName());
        }

    }

}
