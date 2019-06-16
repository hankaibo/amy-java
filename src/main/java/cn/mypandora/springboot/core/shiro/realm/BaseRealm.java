package cn.mypandora.springboot.core.shiro.realm;

import cn.mypandora.springboot.core.enums.SessionEnum;
import cn.mypandora.springboot.core.shiro.token.IdPasswordToken;
import cn.mypandora.springboot.core.utils.SessionUtil;
import cn.mypandora.springboot.modular.system.model.Resource;
import cn.mypandora.springboot.modular.system.model.Role;
import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.modular.system.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * BaseRealm
 * 需要注入自己的接口，判断用户认证结果、授权结果。
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Slf4j
public class BaseRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    /**
     * 授权。
     * <p>
     * 权限信息：
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果修改了用户的权限，而不退出，无法立即生效。
     * </p>
     *
     * @param principals 权限集合
     * @return 授权结果
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("BaseRealm.doGetAuthorizationInfo() shiro授权");

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = SessionUtil.getCurrentUser();
        if (user != null) {
            Set<String> roleCodes = new HashSet<>();
            List<Role> roleList = user.getRoleList();
            for (Role role : roleList) {
                roleCodes.add(role.getCode());
            }
            authorizationInfo.addRoles(roleCodes);

            Set<String> stringPermissions = new HashSet<>();
            List<Resource> resourceList = user.getResourceList();
            for (Resource resource : resourceList) {
                stringPermissions.add(resource.getCode());
            }
            authorizationInfo.addStringPermissions(stringPermissions);
        }
        return authorizationInfo;
    }

    /**
     * 认证。
     *
     * @param token 用户登录之后生成token信息
     * @return 认证信息
     * @throws AuthenticationException 认证异常信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("BaseRealm.doGetAuthenticationInfo() shiro认证");
        AuthenticationInfo authenticationInfo = null;
        User user = null;

        //  用户名密码类型的token
        if (token instanceof UsernamePasswordToken) {
            log.info("Use UsernamePasswordToken for authentication");

            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
            user = loginService.login(null, usernamePasswordToken.getUsername(), String.valueOf(usernamePasswordToken.getPassword()));
            if (user != null) {
                authenticationInfo = new SimpleAuthenticationInfo(usernamePasswordToken.getUsername(), usernamePasswordToken.getPassword(), this.getName());
            }
        }
        // ID密码类型的token，此token为自定义token类型。如果有其它类型的登录可以添加再相应的token
        else if (token instanceof IdPasswordToken) {
            log.info("Use IdPasswordToken for authentication");
            IdPasswordToken idPasswordToken = (IdPasswordToken) token;
            user = loginService.login(Long.valueOf(idPasswordToken.getId()), null, String.valueOf(idPasswordToken.getPassword()));
            if (user != null) {
                authenticationInfo = new SimpleAuthenticationInfo(idPasswordToken.getId(), idPasswordToken.getPassword(), this.getName());
            }
        }

        if (user != null) {
            SessionUtil.setAttribute(SessionEnum.CURRENT_USER, user);
        }
        return authenticationInfo;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        boolean support = token instanceof UsernamePasswordToken || token instanceof IdPasswordToken;
        return token != null && support;
    }
}
