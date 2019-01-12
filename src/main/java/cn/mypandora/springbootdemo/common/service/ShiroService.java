package cn.mypandora.springbootdemo.common.service;

import cn.mypandora.springbootdemo.common.entity.User;

/**
 * ShiroService
 *
 * @author hankaibo
 * @date 2019/1/12
 */
public interface ShiroService {

    /**
     * shiro认证登陆，进行密码及合法性校验。
     *
     * @param userId   用户Id
     * @param username 用户名称
     * @param password 密码
     * @return 若返回为null或者抛出异常，则认证失败，若返回有值，则认证成功
     */
    User login(Long userId, String username, String password);
}
