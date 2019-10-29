package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.mapper.RoleMapper;
import cn.mypandora.springboot.modular.system.mapper.RoleResourceMapper;
import cn.mypandora.springboot.modular.system.mapper.UserRoleMapper;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.service.RoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * RoleServiceImpl
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Service
public class RoleServiceImpl implements RoleService {

    private RoleMapper roleMapper;
    private RoleResourceMapper roleResourceMapper;
    private UserRoleMapper userRoleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, RoleResourceMapper roleResourceMapper, UserRoleMapper userRoleMapper) {
        this.roleMapper = roleMapper;
        this.roleResourceMapper = roleResourceMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public PageInfo<Role> pageRole(int pageNum, int pageSize, Role role) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = roleMapper.select(role);
        return new PageInfo<>(roleList);
    }

    @Override
    public List<Role> listRoleByCondition(Map<String, Object> map) {
        Condition condition = new Condition(Role.class);
        Condition.Criteria criteria = condition.createCriteria();
        final String status = "status";
        if (map.get(status) != null) {
            criteria.andEqualTo(status, map.get(status));
        }
        return roleMapper.selectByCondition(condition);
    }

    @Override
    public void addRole(Role role) {
        LocalDateTime now = LocalDateTime.now();
        role.setCreateTime(now);
        roleMapper.insert(role);
    }

    @Override
    public Role getRoleByIdOrName(Long id, String name) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return roleMapper.selectOne(role);
    }

    @Override
    public void updateRole(Role role) {
        LocalDateTime now = LocalDateTime.now();
        role.setUpdateTime(now);
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void enableRole(Long id, Integer status) {
        LocalDateTime now = LocalDateTime.now();
        Role role = new Role();
        role.setId(id);
        role.setStatus(status);
        role.setUpdateTime(now);
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(Long id) {
        Role role = new Role();
        role.setId(id);
        roleMapper.delete(role);
        roleResourceMapper.deleteRoleAllResource(id);
        userRoleMapper.deleteRoleAllUser(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchRole(String ids) {
        roleMapper.deleteByIds(ids);

        long[] idList = Arrays.stream(ids.split(",")).mapToLong(Long::valueOf).toArray();
        roleResourceMapper.deleteBatchRoleAllResource(idList);
        userRoleMapper.deleteBatchRoleAllUser(idList);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void grantRoleResource(Long roleId, long[] plusId, long[] minusId) {
        // 删除旧的资源
        if (minusId.length > 0) {
            roleResourceMapper.deleteRoleSomeResource(roleId, minusId);
        }
        // 添加新的资源
        if (plusId.length > 0) {
            roleResourceMapper.grantRoleResource(roleId, plusId);
        }
    }

    @Override
    public List<Role> listRoleByUserIdOrName(Long userId, String username) {
        return roleMapper.selectByUserIdOrName(userId, username);
    }

}
