package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.modular.system.service.LoginService;
import org.springframework.stereotype.Service;

/**
 * LoginServiceImpl
 *
 * @author hankaibo
 * @date 2019/6/19
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public User login(Long id, String name, String password) {
        return null;
    }
}
