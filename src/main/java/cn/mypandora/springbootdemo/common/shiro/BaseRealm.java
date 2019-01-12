package cn.mypandora.springbootdemo.common.shiro;

import cn.mypandora.springbootdemo.common.entity.Permission;
import cn.mypandora.springbootdemo.common.entity.Role;
import cn.mypandora.springbootdemo.common.entity.User;
import cn.mypandora.springbootdemo.common.enums.SessionEnum;
import cn.mypandora.springbootdemo.common.service.ShiroService;
import cn.mypandora.springbootdemo.common.utils.SessionUtil;
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
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Slf4j
public class BaseRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

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
                roleCodes.add(role.getRoleCode());
            }
            authorizationInfo.addRoles(roleCodes);

            Set<String> stringPermissionList = new HashSet<>();
            List<Permission> permissionList = user.getPermissionList();
            for (Permission permission : permissionList) {
                stringPermissionList.add(permission.getPermissionCode());
            }
            authorizationInfo.addStringPermissions(stringPermissionList);
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("BaseRealm.doGetAuthenticationInfo() shiro认证");
        AuthenticationInfo authenticationInfo = null;
        User user = null;

        if (token instanceof UsernamePasswordToken) {
            log.info("Use UsernamePasswordToken for authentication");

            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
            user = shiroService.login(null, usernamePasswordToken.getUsername(), String.valueOf(usernamePasswordToken.getPassword()));
            if (user != null) {
                authenticationInfo = new SimpleAuthenticationInfo(usernamePasswordToken.getUsername(), usernamePasswordToken.getPassword(), this.getName());
            }
        } else if (token instanceof IdPasswordToken) {
            log.info("Use IdPasswordToken for authentication");
            IdPasswordToken idPasswordToken = (IdPasswordToken) token;
            user = shiroService.login(Long.valueOf(idPasswordToken.getId()), null, String.valueOf(idPasswordToken.getPassword()));
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
