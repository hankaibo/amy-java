package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.modular.system.mapper.RoleMapper;
import cn.mypandora.springboot.modular.system.mapper.RoleResourceMapper;
import cn.mypandora.springboot.modular.system.mapper.UserRoleMapper;
import cn.mypandora.springboot.modular.system.model.po.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.time.LocalDateTime;
import java.util.*;
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
    public List<Role> listRole(Integer status, Long userId) {
        // 获取用户的所有角色并过滤掉子孙角色，以减少后面重复角色的获取。
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("status", status);
        List<Role> allRoleList = roleMapper.listByUserIdOrName(map);

        List<Role> roleList = listTopAncestryRole(allRoleList);

        // 自身
        Set<Role> roleSet = new HashSet<>(roleList);
        // 所有后代角色
        for (Role role : roleList) {
            role.setParentId(null);
            Map<String, Number> descendantMap = new HashMap<>(2);
            descendantMap.put("id", role.getId());
            descendantMap.put("status", status);
            List<Role> roleDescendantList = roleMapper.listDescendants(descendantMap);
            roleSet.addAll(roleDescendantList);
        }
        return roleSet.stream().sorted(Comparator.comparing(Role::getLft)).collect(Collectors.toList());
    }

    @Override
    public List<Role> listChildrenRole(Long id, Integer status, Long userId) {
        List<Role> allRoleList = listRole(status, userId);

        Map<String, Number> map = new HashMap<>(2);
        map.put("id", id);
        map.put("status", status);
        List<Role> roleList = roleMapper.listChildren(map);
        return roleList.stream().filter(allRoleList::contains).collect(Collectors.toList());
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
    public void addRole(Role role, Long userId) {
        List<Role> roleList = listRole(1, userId);
        if (roleList.stream().noneMatch(item -> item.getId().equals(role.getParentId()))) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "父级角色选择错误。");
        }

        Role parentRole = getRoleByIdOrName(role.getParentId(), null, userId);
        LocalDateTime now = LocalDateTime.now();
        role.setCreateTime(now);
        role.setLft(parentRole.getRgt());
        role.setRgt(parentRole.getRgt() + 1);
        role.setLevel(parentRole.getLevel() + 1);
        role.setIsUpdate(1);

        Map<String, Number> map = new HashMap<>(2);
        map.put("id", role.getParentId());
        map.put("amount", 2);
        roleMapper.lftAdd(map);
        roleMapper.rgtAdd(map);
        roleMapper.insert(role);
        roleMapper.parentRgtAdd(map);
    }

    @Override
    public Role getRoleByIdOrName(Long id, String name, Long userId) {
        List<Role> roleList = listRole(1, userId);
        if (roleList.stream().noneMatch(item -> item.getId().equals(id))) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "f无法查看该角色。");
        }

        Role role = new Role();
        role.setId(id);
        role.setName(name);
        Role info = roleMapper.selectOne(role);
        if (info == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "角色不存在。");
        }
        return info;
    }

    @Override
    public void updateRole(Role role, Long userId) {
        List<Role> roleList = listRole(1, userId);
        if (roleList.stream().noneMatch(item -> item.getId().equals(role.getParentId()))) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "父级角色错误。");
        }

        if (!isCanUpdateParent(role)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "不可以选择子角色作为自己的父级。");
        }
        Role info = getRoleByIdOrName(role.getId(), null, userId);

        if (!info.getParentId().equals(role.getParentId())) {
            Role newParentRole = getRoleByIdOrName(role.getParentId(), null, userId);
            Role oldParentRole = getRoleByIdOrName(info.getParentId(), null, userId);
            Role commonAncestry = getCommonAncestry(newParentRole, oldParentRole);

            List<Long> updateIdList = listDescendantId(role.getId());
            int roleNum = updateIdList.size();

            Map<String, Object> lockMap = new HashMap<>(2);
            lockMap.put("idList", updateIdList);
            lockMap.put("isUpdate", 0);
            roleMapper.locking(lockMap);

            Map<String, Number> oldParentMap = new HashMap<>(3);
            oldParentMap.put("id", info.getId());
            oldParentMap.put("amount", roleNum * -2);
            oldParentMap.put("range", commonAncestry.getRgt());
            roleMapper.lftAdd(oldParentMap);
            roleMapper.rgtAdd(oldParentMap);

            Map<String, Number> newParentMap = new HashMap<>(2);
            newParentMap.put("id", newParentRole.getId());
            newParentMap.put("amount", roleNum * 2);
            newParentMap.put("range", commonAncestry.getRgt());
            roleMapper.lftAdd(newParentMap);
            roleMapper.rgtAdd(newParentMap);
            roleMapper.parentRgtAdd(newParentMap);

            lockMap.put("isUpdate", 1);
            roleMapper.locking(lockMap);
            int amount = getRoleByIdOrName(role.getParentId(), null, userId).getRgt() - info.getRgt() - 1;
            int level = newParentRole.getLevel() + 1 - info.getLevel();
            Map<String, Object> updateMap = new HashMap<>(3);
            updateMap.put("idList", updateIdList);
            updateMap.put("amount", amount);
            updateMap.put("level", level);
            roleMapper.selfAndDescendant(updateMap);

            LocalDateTime now = LocalDateTime.now();
            role.setUpdateTime(now);
            role.setLevel(newParentRole.getLevel() + 1);
        }
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void enableRole(Long id, Integer status, Long userId) {
        List<Role> roleList = listRole(null, userId);
        List<Long> idList = listDescendantId(id);
        List<Long> list = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!list.retainAll(idList)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "角色错误。");
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("idList", idList);
        map.put("status", status);
        roleMapper.enableDescendants(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(Long id, Long userId) {
        List<Role> roleList = listRole(null, userId);
        List<Long> list = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!list.contains(id)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "角色错误。");
        }

        Role role = new Role();
        role.setId(id);
        // 先求出要删除的角色的所有信息，利用左值与右值计算出要删除的角色数量。
        // 删除角色数=(角色右值-角色左值+1)/2
        Role info = roleMapper.selectByPrimaryKey(role);
        int deleteAmount = info.getRgt() - info.getLft() + 1;
        // 更新此角色之后的相关角色左右值
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", id);
        map.put("amount", -deleteAmount);
        roleMapper.lftAdd(map);
        roleMapper.rgtAdd(map);
        // 求出要删除的角色所有子孙角色
        List<Long> idList = listDescendantId(id);
        String ids = StringUtils.join(idList, ',');
        // 批量删除角色及子孙角色
        roleMapper.deleteByIds(ids);
        // 删除关联的资源
        roleResourceMapper.deleteRoleAllResource(id);
        userRoleMapper.deleteRoleAllUser(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveRole(Long sourceId, Long targetId, Long userId) {
        List<Role> roleList = listRole(null, userId);
        List<Long> idList = new ArrayList<>();
        idList.add(sourceId);
        idList.add(targetId);
        List<Long> list = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!list.retainAll(idList)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "角色错误。");
        }

        // 先取出源角色与目标角色两者的信息
        Role sourceRole = new Role();
        Role targetRole = new Role();

        sourceRole.setId(sourceId);
        targetRole.setId(targetId);

        Role sourceInfo = roleMapper.selectByPrimaryKey(sourceRole);
        Role targetInfo = roleMapper.selectByPrimaryKey(targetRole);

        int sourceAmount = sourceInfo.getRgt() - sourceInfo.getLft() + 1;
        int targetAmount = targetInfo.getRgt() - targetInfo.getLft() + 1;

        List<Long> sourceIdList = listDescendantId(sourceId);
        List<Long> targetIdList = listDescendantId(targetId);

        // 确定方向，目标大于源：下称；反之：上移。
        if (targetInfo.getRgt() > sourceInfo.getRgt()) {
            sourceAmount *= -1;
        } else {
            targetAmount *= -1;
        }
        // 源角色及子孙角色左右值 targetAmount
        Map<String, Object> sourceMap = new HashMap<>(2);
        sourceMap.put("idList", sourceIdList);
        sourceMap.put("amount", targetAmount);
        roleMapper.selfAndDescendant(sourceMap);
        // 目标角色及子孙角色左右值 sourceAmount
        Map<String, Object> targetMap = new HashMap<>(2);
        targetMap.put("idList", targetIdList);
        targetMap.put("amount", sourceAmount);
        roleMapper.selfAndDescendant(targetMap);
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
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("username", username);
        return roleMapper.listByUserIdOrName(map);
    }

    /**
     * 获取此角色及其子孙角色的id
     *
     * @param id 角色
     * @return 角色集合
     */
    private List<Long> listDescendantId(Long id) {
        Map<String, Number> map = new HashMap<>(1);
        map.put("id", id);
        List<Role> roleList = roleMapper.listDescendants(map);
        List<Long> idList = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }

    /**
     * 获取各角色最顶级的祖先角色。
     * 如果一个用户在父级角色、本级角色、子级角色都存在，则只过滤出父级角色，以减少后边重复查询。
     *
     * @param roleList 角色列表
     * @return 用户所在各角色的最顶级角色。
     */
    private List<Role> listTopAncestryRole(List<Role> roleList) {
        List<Role> result = new ArrayList<>();
        if (roleList.size() == 1) {
            return roleList;
        }
        for (Role role : roleList) {
            if (roleList
                    .stream()
                    .filter(item -> item.getLevel() < role.getLevel())
                    .noneMatch(item -> item.getLft() < role.getLft() && item.getRgt() > role.getRgt())) {
                result.add(role);
            }
        }
        return result;
    }

    /**
     * 防止更新角色时，指定自己的下级角色作为自己的父级角色。
     *
     * @param role 角色
     * @return true可以更新；false不可以更新
     */
    private boolean isCanUpdateParent(Role role) {
        Role child = new Role();
        child.setId(role.getId());
        Role childRole = roleMapper.selectByPrimaryKey(child);

        Role parent = new Role();
        parent.setId(role.getParentId());
        Role parentRole = roleMapper.selectByPrimaryKey(parent);
        return !(parentRole.getLft() >= childRole.getLft() && parentRole.getRgt() <= childRole.getRgt());
    }

    /**
     * 获取两个角色最近的共同祖先角色。
     *
     * @param role1 第一个角色
     * @param role2 第二个角色
     * @return 最近的祖先角色
     */
    private Role getCommonAncestry(Role role1, Role role2) {
        Map<String, Number> newParentMap = new HashMap<>(2);
        newParentMap.put("id", role1.getId());
        newParentMap.put("status", 1);
        List<Role> newParentAncestries = roleMapper.listAncestries(newParentMap);
        if (newParentAncestries.size() == 0) {
            newParentAncestries.add(role1);
        }

        Map<String, Number> oldParentMap = new HashMap<>(2);
        oldParentMap.put("id", role2.getId());
        oldParentMap.put("status", 1);
        List<Role> oldParentAncestries = roleMapper.listAncestries(oldParentMap);
        if (oldParentAncestries.size() == 0) {
            oldParentAncestries.add(role2);
        }

        Comparator<Role> comparator = Comparator.comparing(Role::getLft);
        return newParentAncestries.stream().filter(oldParentAncestries::contains).max(comparator).get();
    }

}
