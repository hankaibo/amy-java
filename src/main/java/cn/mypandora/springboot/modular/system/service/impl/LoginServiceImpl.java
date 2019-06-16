package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.modular.system.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public User login(Long id, String name, String password) {
        return null;
    }
}
