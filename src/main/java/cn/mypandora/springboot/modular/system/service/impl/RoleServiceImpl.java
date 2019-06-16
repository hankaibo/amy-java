package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.model.Role;
import cn.mypandora.springboot.modular.system.model.RoleResource;
import cn.mypandora.springboot.modular.system.mapper.RoleMapper;
import cn.mypandora.springboot.modular.system.mapper.RoleResourceMapper;
import cn.mypandora.springboot.modular.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * RoleServiceImpl
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Service("RoleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    public boolean authorityRoleResource(Long roleId, Long resourceId) {
        Date now = new Date(System.currentTimeMillis());
        RoleResource roleResource = new RoleResource();
        roleResource.setRoleId(roleId);
        roleResource.setResourceId(resourceId);
        roleResource.setCreateTime(now);
        roleResource.setModifyTime(now);
        return roleResourceMapper.insert(roleResource) == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean deleteAuthorityRoleResource(Long roleId, Long resourceId) {
        RoleResource roleResource = new RoleResource();
        roleResource.setRoleId(roleId);
        roleResource.setResourceId(resourceId);
        return roleResourceMapper.delete(roleResource) == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public Role queryRoleByIdOrName(Long roleId, String roleName) {
        Role role = new Role();
        role.setId(roleId);
        role.setName(roleName);
        return roleMapper.selectOne(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleMapper.deleteByIds(roleId.toString());
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }


    @Override
    public void addRole(Role role) {
        Date now = new Date(System.currentTimeMillis());
//        vali
        role.setCreateTime(now);
        role.setModifyTime(now);
        roleMapper.insert(role);
    }
}
