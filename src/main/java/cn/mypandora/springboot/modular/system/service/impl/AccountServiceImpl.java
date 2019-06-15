package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.model.Account;
import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.modular.system.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * AccountServiceImpl
 *
 * @author hankaibo
 * @date 2019/1/16
 */
@Service("AccountService")
public class AccountServiceImpl implements AccountService {
    @Override
    public Account loadAccount(String appId) {
        return null;
    }

    @Override
    public boolean isAccountExistbyUid(Long userId) {
        return false;
    }

    @Override
    public boolean registerAccount(User user) {
        return false;
    }

    @Override
    public String loadAccountRole(String appId) {
        return null;
    }
}
