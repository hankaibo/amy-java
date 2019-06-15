package cn.mypandora.springboot.core.shiro.provider.impl;

import cn.mypandora.springboot.modular.system.model.Account;
import cn.mypandora.springboot.modular.system.service.AccountService;
import cn.mypandora.springboot.core.shiro.provider.AccountProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * AccountProviderImpl
 *
 * @author hankaibo
 * @date 2019/1/15
 */
@Service("AccountProvider")
public class AccountProviderImpl implements AccountProvider {
    @Autowired
    @Qualifier("AccountService")
    private AccountService accountService;

    @Override
    public Account loadAccount(String appId) {
        return accountService.loadAccount(appId);
    }
}
