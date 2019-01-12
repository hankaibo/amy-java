package cn.mypandora.springbootdemo.common.service.impl;

import cn.mypandora.springbootdemo.common.entity.Role;
import cn.mypandora.springbootdemo.common.mapper.RoleMapper;
import cn.mypandora.springbootdemo.common.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;

/**
 * RoleServiceImpl
 *
 * @author hankaibo
 * @date 2019/1/12
 */
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void addRole(Role role) {
        Date now = new Date(System.currentTimeMillis());
//        vali
        role.setCreateTime(now);
        role.setModifyTime(now);
        roleMapper.insert(role);
    }
}
