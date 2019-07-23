package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.mapper.ResourceMapper;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.po.RoleResource;
import cn.mypandora.springboot.modular.system.mapper.RoleMapper;
import cn.mypandora.springboot.modular.system.mapper.RoleResourceMapper;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RoleServiceImpl
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Service("RoleService")
public class RoleServiceImpl implements RoleService {

    private RoleMapper roleMapper;
    private RoleResourceMapper roleResourceMapper;
    private ResourceMapper resourceMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, RoleResourceMapper roleResourceMapper, ResourceMapper resourceMapper) {
        this.roleMapper = roleMapper;
        this.roleResourceMapper = roleResourceMapper;
        this.resourceMapper = resourceMapper;
    }

    @Override
    public PageInfo<Role> selectRoleList(int pageNum, int pageSize, Role role) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = roleMapper.selectByCondition(role);
        return new PageInfo<>(roleList);
    }

    @Override
    public List<Role> selectRoleList() {
        return roleMapper.selectAll();
    }

    @Override
    public Role selectRoleByIdOrName(Long id, String name) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return roleMapper.selectOne(role);
    }

    @Override
    public void addRole(Role role) {
        Date now = new Date(System.currentTimeMillis());
        role.setCreateTime(now);
        roleMapper.insert(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleMapper.deleteByIds(roleId.toString());
    }

    @Override
    public void deleteBatchRole(String ids) {
        roleMapper.deleteByIds(ids);
    }

    @Override
    public void updateRole(Role role) {
        Date now = new Date(System.currentTimeMillis());
        role.setModifyTime(now);
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public boolean enableRole(Long id, Integer status) {
        Role role = new Role();
        role.setId(id);
        role.setStatus(status);
        return roleMapper.updateByPrimaryKeySelective(role) > 0;
    }

    @Override
    public List<Resource> selectResourceByRole(Long id) {
        return resourceMapper.getResourceByRole(id);
    }

    /**
     * TODO 对数据库一知半解，找不出更好的方法。
     */
    @Override
    public boolean giveRoleResource(Long roleId, Long[] resourceListId) {
        // 删除旧的资源
        roleResourceMapper.deleteRoleResource(roleId);
        // 添加新的资源
        int result = roleResourceMapper.giveRoleResource(roleId, resourceListId);
        return result > 0;
    }

}
