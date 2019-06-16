package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.modular.system.model.User;

public interface LoginService {
    User login(Long id, String name, String password);
}
