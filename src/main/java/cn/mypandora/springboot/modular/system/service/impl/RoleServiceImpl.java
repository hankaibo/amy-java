package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.mapper.RoleMapper;
import cn.mypandora.springboot.modular.system.mapper.RoleResourceMapper;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.service.RoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, RoleResourceMapper roleResourceMapper) {
        this.roleMapper = roleMapper;
        this.roleResourceMapper = roleResourceMapper;
    }

    @Override
    public PageInfo<Role> selectRolePage(int pageNum, int pageSize, Role role) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = roleMapper.select(role);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(Long id) {
        Role role = new Role();
        role.setId(id);
        roleMapper.delete(role);
        roleResourceMapper.deleteRoleResource(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchRole(String ids) {
        roleMapper.deleteByIds(ids);

        Long[] idList = Stream.of(ids.split(",")).map(s -> Long.valueOf(s)).collect(Collectors.toList()).toArray(new Long[]{});
        roleResourceMapper.deleteBatchRoleResource(idList);
    }

    @Override
    public void updateRole(Role role) {
        Date now = new Date(System.currentTimeMillis());
        role.setUpdateTime(now);
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public boolean enableRole(Long id, Integer status) {
        Role role = new Role();
        role.setId(id);
        role.setStatus(status);
        return roleMapper.updateByPrimaryKeySelective(role) > 0;
    }

    /**
     * TODO 对数据库一知半解，找不出更好的方法。
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean giveRoleResource(Long roleId, Long[] resourceListId) {
        // 删除旧的资源
        roleResourceMapper.deleteRoleResource(roleId);
        // 添加新的资源
        return roleResourceMapper.giveRoleResource(roleId, resourceListId) > 0;
    }

    @Override
    public List<Role> selectRoleByUserIdOrName(Long userId, String username) {
        return roleMapper.selectByUserIdOrName(userId, username);
    }
}
