package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.mapper.RoleMapper;
import cn.mypandora.springboot.modular.system.mapper.RoleResourceMapper;
import cn.mypandora.springboot.modular.system.mapper.UserRoleMapper;
import cn.mypandora.springboot.modular.system.model.po.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<Role> listAllRole(Integer status) {
        Map<String, Integer> map = new HashMap<>(1);
        map.put("status", status);
        return roleMapper.listAll(map);
    }

    @Override
    public List<Role> listDescendantRole(String name) {
        Role role = new Role();
        role.setCode(name);
        Role info = roleMapper.selectOne(role);
        Map<String, Number> map = new HashMap<>(1);
        map.put("id", info.getId());
        List<Role> result = roleMapper.listDescendants(map);
        result.add(info);
        return result;
    }

    @Override
    public List<Role> listChildrenRole(Role role) {
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", role.getId());
        map.put("status", role.getStatus());
        return roleMapper.listChildren(map);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRole(Role role) {
        // 如果parentId为空，那么就创建为一个新树的根节点，parentId是null，level是1。
        if (role.getParentId() == null || role.getParentId() < 1) {
            role.setLft(1);
            role.setRgt(2);
            role.setLevel(1);
            role.setParentId(null);
        } else {
            Role info = getRoleByIdOrName(role.getParentId(), null);
            role.setLft(info.getRgt());
            role.setRgt(info.getRgt() + 1);
            role.setLevel(info.getLevel() + 1);
        }
        LocalDateTime now = LocalDateTime.now();
        role.setCreateTime(now);
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", role.getParentId());
        map.put("amount", 2);
        roleMapper.lftAdd(map);
        roleMapper.rgtAdd(map);
        roleMapper.insert(role);
        roleMapper.parentRgtAdd(map);
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
        List<Long> idList = listDescendantId(id);
        Map<String, Object> map = new HashMap<>(2);
        map.put("idList", idList);
        map.put("status", status);
        roleMapper.enableDescendants(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(Long id) {
        Role role = new Role();
        role.setId(id);
        // 先求出要删除的节点的所有信息，利用左值与右值计算出要删除的节点数量。
        // 删除节点数=(节点右值-节点左值+1)/2
        Role info = roleMapper.selectByPrimaryKey(role);
        int deleteAmount = info.getRgt() - info.getLft() + 1;
        // 更新此节点之后的相关节点左右值
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", id);
        map.put("amount", -deleteAmount);
        roleMapper.lftAdd(map);
        roleMapper.rgtAdd(map);
        // 求出要删除的节点所有子孙节点
        List<Long> idList = listDescendantId(id);
        String ids = StringUtils.join(idList, ',');
        // 批量删除节点及子孙节点
        roleMapper.deleteByIds(ids);
        // 删除关联的资源
        roleResourceMapper.deleteRoleAllResource(id);
        userRoleMapper.deleteRoleAllUser(id);
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

    /**
     * 获取此节点及其子孙节点的id
     *
     * @param id 节点
     * @return 节点集合
     */
    private List<Long> listDescendantId(Long id) {
        Map<String, Number> map = new HashMap<>(1);
        map.put("id", id);
        List<Role> roleList = roleMapper.listDescendants(map);
        List<Long> idList = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }

}
