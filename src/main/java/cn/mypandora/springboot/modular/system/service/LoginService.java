package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.modular.system.model.po.User;
/**
 * LoginService
 *
 * @author hankaibo
 * @date 2019/6/19
 */
public interface LoginService {
    /**
     * 根据用户/密码查询指定用户。
     *
     * @param id       用户id
     * @param name     用户姓名
     * @param password 用户密码
     * @return 用户
     */
    User login(Long id, String name, String password);
}
