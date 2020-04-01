package cn.mypandora.springboot.modular.system.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mypandora.springboot.config.exception.BusinessException;
import cn.mypandora.springboot.config.exception.EntityNotFoundException;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.shiro.filter.FilterChainManager;
import cn.mypandora.springboot.modular.system.mapper.RoleMapper;
import cn.mypandora.springboot.modular.system.mapper.RoleResourceMapper;
import cn.mypandora.springboot.modular.system.mapper.UserRoleMapper;
import cn.mypandora.springboot.modular.system.model.po.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.service.RoleService;

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
    private FilterChainManager filterChainManager;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, RoleResourceMapper roleResourceMapper, UserRoleMapper userRoleMapper,
        FilterChainManager filterChainManager) {
        this.roleMapper = roleMapper;
        this.roleResourceMapper = roleResourceMapper;
        this.userRoleMapper = userRoleMapper;
        this.filterChainManager = filterChainManager;
    }

    @Override
    public List<Role> listRole(Integer status, Long userId) {
        // 获取用户的所有角色并过滤掉子孙角色，以减少后面重复角色的获取。
        List<Role> allRoleList = roleMapper.listByUserIdOrName(userId, null, status);

        List<Role> roleList = listTopAncestryRole(allRoleList);
        // 自身
        Set<Role> roleSet = new HashSet<>(roleList);
        // 所有后代角色
        for (Role role : roleList) {
            Long id = role.getId();
            List<Role> roleDescendantList = roleMapper.listDescendants(id, status);
            roleSet.addAll(roleDescendantList);
        }
        return roleSet.stream().sorted(Comparator.comparing(Role::getLft)).collect(Collectors.toList());
    }

    @Override
    public List<Role> listChildrenRole(Long id, Integer status, Long userId) {
        List<Role> allRoleList = listRole(null, userId);

        List<Role> roleList = roleMapper.listChildren(id, status);
        return roleList.stream().filter(allRoleList::contains).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRole(Role role, Long userId) {
        List<Role> roleList = listRole(null, userId);
        if (roleList.stream().noneMatch(item -> item.getId().equals(role.getParentId()))) {
            throw new BusinessException(Role.class, "父级角色选择错误。");
        }

        Long parentId = role.getParentId();
        Role parentRole = getRoleByIdOrName(parentId, null, userId);
        LocalDateTime now = LocalDateTime.now();
        role.setCreateTime(now);
        role.setLft(parentRole.getRgt());
        role.setRgt(parentRole.getRgt() + 1);
        role.setLevel(parentRole.getLevel() + 1);
        role.setIsUpdate(1);

        int amount = 2;
        roleMapper.lftAdd(parentId, amount, null);
        roleMapper.rgtAdd(parentId, amount, null);
        roleMapper.insert(role);
        roleMapper.parentRgtAdd(parentId, amount);
    }

    @Override
    public Role getRoleByIdOrName(Long id, String name, Long userId) {
        List<Role> roleList = listRole(null, userId);
        if (roleList.stream().noneMatch(item -> item.getId().equals(id))) {
            throw new BusinessException(Role.class, "无法查看该角色。");
        }

        Role role = new Role();
        role.setId(id);
        role.setName(name);
        Role info = roleMapper.selectByPrimaryKey(role);
        if (info == null) {
            throw new EntityNotFoundException(Role.class, "角色不存在。");
        }
        return info;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(Role role, Long userId) {
        List<Role> roleList = listRole(null, userId);
        if (roleList.stream().noneMatch(item -> item.getId().equals(role.getParentId()))) {
            throw new BusinessException(Role.class, "父级角色错误。");
        }

        if (!isCanUpdateParent(role)) {
            throw new BusinessException(Role.class, "不可以选择子角色作为自己的父级。");
        }

        if (roleList.stream().noneMatch(item -> item.getId().equals(role.getId()))) {
            throw new BusinessException(Role.class, "角色错误。");
        }

        Role info = getRoleByIdOrName(role.getId(), null, userId);

        if (!info.getParentId().equals(role.getParentId())) {
            Role newParentRole = getRoleByIdOrName(role.getParentId(), null, userId);
            Role oldParentRole = getRoleByIdOrName(info.getParentId(), null, userId);
            Role commonAncestry = getCommonAncestry(newParentRole, oldParentRole);

            List<Long> updateIdList = listDescendantId(role.getId());
            int roleNum = updateIdList.size();

            roleMapper.locking(updateIdList, 0);

            Long oldId = info.getId();
            int oldAmount = roleNum * -2;
            int oldRange = commonAncestry.getRgt();
            roleMapper.lftAdd(oldId, oldAmount, oldRange);
            roleMapper.rgtAdd(oldId, oldAmount, oldRange);

            Long newId = newParentRole.getId();
            int newAmount = roleNum * 2;
            int newRange = commonAncestry.getRgt();
            roleMapper.lftAdd(newId, newAmount, newRange);
            roleMapper.rgtAdd(newId, newAmount, newRange);
            roleMapper.parentRgtAdd(newId, newAmount);

            roleMapper.locking(updateIdList, 1);
            int amount = getRoleByIdOrName(role.getParentId(), null, userId).getRgt() - info.getRgt() - 1;
            int level = newParentRole.getLevel() + 1 - info.getLevel();
            roleMapper.selfAndDescendant(updateIdList, amount, level);

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
        List<Long> allIdList = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!allIdList.retainAll(idList)) {
            throw new BusinessException(Role.class, "角色错误。");
        }
        roleMapper.enableDescendants(idList, status);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(Long id, Long userId) {
        List<Role> roleList = listRole(null, userId);
        List<Long> allIdList = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!allIdList.contains(id)) {
            throw new BusinessException(Role.class, "角色错误。");
        }

        Role role = new Role();
        role.setId(id);
        // 先求出要删除的角色的所有信息，利用左值与右值计算出要删除的角色数量。
        // 删除角色数=(角色右值-角色左值+1)/2
        Role info = roleMapper.selectByPrimaryKey(role);
        int deleteAmount = info.getRgt() - info.getLft() + 1;
        // 更新此角色之后的相关角色左右值
        roleMapper.lftAdd(id, -deleteAmount, null);
        roleMapper.rgtAdd(id, -deleteAmount, null);
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
        if (null == targetId || null == sourceId) {
            throw new EntityNotFoundException(Role.class, "该角色不可以移动。");
        }

        List<Role> roleList = listRole(null, userId);
        List<Long> idList = new ArrayList<>();
        idList.add(sourceId);
        idList.add(targetId);
        List<Long> allIdList = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!allIdList.retainAll(idList)) {
            throw new BusinessException(Role.class, "角色错误。");
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
        roleMapper.selfAndDescendant(sourceIdList, targetAmount, 0);
        // 目标角色及子孙角色左右值 sourceAmount
        roleMapper.selfAndDescendant(targetIdList, sourceAmount, 0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void grantRoleResource(Long roleId, long[] plusId, long[] minusId, Long userId) {
        // 删除旧的资源
        if (minusId.length > 0) {
            roleResourceMapper.deleteRoleSomeResource(roleId, minusId);
        }
        // 添加新的资源
        if (plusId.length > 0) {
            roleResourceMapper.grantRoleResource(roleId, plusId);
        }
        filterChainManager.reloadFilterChain();
    }

    @Override
    public List<Role> listRoleByUserIdOrName(Long userId, String username) {
        return roleMapper.listByUserIdOrName(userId, username, null);
    }

    /**
     * 获取此角色及其子孙角色的id。
     *
     * @param id
     *            角色
     * @return 角色主键id集合
     */
    private List<Long> listDescendantId(Long id) {
        List<Role> roleList = roleMapper.listDescendants(id, null);
        List<Long> idList = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }

    /**
     * 获取各角色最顶级的祖先角色。 如果一个用户在父级角色、本级角色、子级角色都存在，则只过滤出父级角色，以减少后边重复查询。
     *
     * @param roleList
     *            角色列表
     * @return 用户所在各角色的最顶级角色。
     */
    private List<Role> listTopAncestryRole(List<Role> roleList) {
        List<Role> result = new ArrayList<>();
        if (roleList.size() == 1) {
            return roleList;
        }
        for (Role role : roleList) {
            if (roleList.stream().filter(item -> item.getLevel() < role.getLevel())
                .noneMatch(item -> item.getLft() < role.getLft() && item.getRgt() > role.getRgt())) {
                result.add(role);
            }
        }
        return result;
    }

    /**
     * 防止更新角色时，指定自己的下级角色作为自己的父级角色。
     *
     * @param role
     *            角色
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
     * @param role1
     *            第一个角色
     * @param role2
     *            第二个角色
     * @return 最近的祖先角色
     */
    private Role getCommonAncestry(Role role1, Role role2) {
        // 首先判断两者是否是包含关系
        if (role1.getLft() < role2.getLft() && role1.getRgt() > role2.getRgt()) {
            return role1;
        }
        if (role2.getLft() < role1.getLft() && role2.getRgt() > role1.getRgt()) {
            return role2;
        }
        // 两者没有包含关系的情况下
        Long newId = role1.getId();
        List<Role> newParentAncestries = roleMapper.listAncestries(newId, StatusEnum.ENABLED.getValue());
        if (newParentAncestries.size() == 0) {
            newParentAncestries.add(role1);
        }

        Long oldId = role2.getId();
        List<Role> oldParentAncestries = roleMapper.listAncestries(oldId, StatusEnum.ENABLED.getValue());
        if (oldParentAncestries.size() == 0) {
            oldParentAncestries.add(role2);
        }

        Comparator<Role> comparator = Comparator.comparing(Role::getLft);
        return newParentAncestries.stream().filter(oldParentAncestries::contains).max(comparator)
            .orElseThrow(RuntimeException::new);
    }

}
