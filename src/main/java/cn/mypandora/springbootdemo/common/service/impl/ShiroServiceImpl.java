package cn.mypandora.springbootdemo.common.service.impl;

import cn.mypandora.springbootdemo.common.entity.User;
import cn.mypandora.springbootdemo.common.enums.BooleanEnum;
import cn.mypandora.springbootdemo.common.service.ShiroService;
import cn.mypandora.springbootdemo.common.service.UserService;
import cn.mypandora.springbootdemo.common.utils.PasswordUtil;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ShiroServiceImpl
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Service("ShiroServiceImpl")
public class ShiroServiceImpl implements ShiroService {

    private final UserService userService;

    @Autowired
    public ShiroServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User login(Long userId, String username, String password) {
        User user = userService.queryByIdOrName(userId, username);
        this.validateUserPassword(user, password);
        this.hidePassword(user);
        return user;
    }

    private void hidePassword(User user) {
        user.setPassword("");
    }

    private void validateUserPassword(User user, String password) {
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (BooleanEnum.NO.getValue() == user.getStateCode()) {
            throw new LockedAccountException();
        }
        String passwordDb = user.getPassword();
        if (!passwordDb.equals(PasswordUtil.encrypt(password, user.getSalt()))) {
            throw new IncorrectCredentialsException();
        }
    }
}
