package cn.mypandora.springboot.core.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.shiro.token.PasswordToken;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.service.UserService;

/**
 * PasswordRealm 登录认证的Realm。
 *
 * @author hankaibo
 * @date 2019/6/18
 */
public class PasswordRealm extends AuthorizingRealm {

    private UserService userService;

    /**
     * 注入接口，从数据库中获取用户进行比对。
     */
    @Autowired
    public PasswordRealm(UserService userService) {
        this.userService = userService;
    }

    /**
     * Realm 只支持PasswordToken
     *
     * @return Class
     */
    @Override
    public Class getAuthenticationTokenClass() {
        return PasswordToken.class;
    }

    /**
     * 因为把认证与授权分在两个realm中实现。所以这个登录realm只负责登录认证，不负责授权。 登录成功之后，通过json web token 授权。
     *
     * @param principalCollection
     *            principalCollection
     * @return authorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证判断。
     *
     * @param token
     *            用户名/密码封装成的PasswordToken
     * @return 认证信息
     * @throws AuthenticationException
     *             登录异常信息，例如：密码错误、用户错误、次数过多、禁用……
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof PasswordToken)) {
            return null;
        }

        if (null == token.getPrincipal() || null == token.getCredentials()) {
            throw new UnknownAccountException();
        }

        String username = (String)token.getPrincipal();
        User info = userService.getUserByIdOrName(null, username);

        if (info == null) {
            throw new UnknownAccountException("用户不存在");
        }

        if (info.getStatus().equals(StatusEnum.DISABLED.getValue())) {
            throw new DisabledAccountException("你的账户已被禁用,请联系管理员开通.");
        }

        // 与新建用户时所采用的加密方法一致。
        ((PasswordToken)token).setPassword(BCrypt.hashpw(((PasswordToken)token).getPassword(), info.getSalt()));
        return new SimpleAuthenticationInfo(username, info.getPassword(), getName());
    }

}
