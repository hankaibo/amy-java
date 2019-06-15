package cn.mypandora.springboot.core.shiro.provider;

import cn.mypandora.springboot.modular.system.model.Account;

/**
 * AccountProvider
 *
 * @author hankaibo
 * @date 2019/1/15
 */
public interface AccountProvider {
    Account loadAccount(String appId);
}
