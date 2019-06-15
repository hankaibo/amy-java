package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.modular.system.model.Account;
import cn.mypandora.springboot.modular.system.model.User;

/**
 * AccountService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface AccountService {

    Account loadAccount(String appId);

    boolean isAccountExistbyUid(Long userId);

    boolean registerAccount(User user);

    String loadAccountRole(String appId);
}
