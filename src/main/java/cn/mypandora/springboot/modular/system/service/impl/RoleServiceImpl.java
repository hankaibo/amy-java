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
import cn.mypandora.springboot.modular.system.model.po.*;
import cn.mypandora.springboot.modular.system.service.RoleService;
import tk.mybatis.mapper.entity.Example;

/**
 * RoleServiceImpl
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleResourceMapper roleResourceMapper;
    private final UserRoleMapper userRoleMapper;
    private final FilterChainManager filterChainManager;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, RoleResourceMapper roleResourceMapper, UserRoleMapper userRoleMapper,
        FilterChainManager filterChainManager) {
        this.roleMapper = roleMapper;
        this.roleResourceMapper = roleResourceMapper;
        this.userRoleMapper = userRoleMapper;
        this.filterChainManager = filterChainManager;
    }

    @Override
    public List<Role> listRole(StatusEnum status, Long userId) {
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
    public List<Role> listChildrenRole(Long id, StatusEnum status, Long userId) {
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
        Role info = roleMapper.selectOne(role);
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

        LocalDateTime now = LocalDateTime.now();
        role.setUpdateTime(now);

        Role info = getRoleByIdOrName(role.getId(), null, userId);
        // 父角色修改之后，一是清空该角色的权限。二是修改涉及的左右节点。
        if (!info.getParentId().equals(role.getParentId())) {
            // 清空从旧父角色继承的权限
            clearResource(role.getId());

            // 修改相关节点值
            Role newParentRole = getRoleByIdOrName(role.getParentId(), null, userId);
            Role oldParentRole = getRoleByIdOrName(info.getParentId(), null, userId);
            Role commonAncestry = getCommonAncestry(newParentRole, oldParentRole);

            // 避免下面修改了共同祖先部门的右节点值。
            int range = commonAncestry.getRgt() + 1;

            List<Long> updateIdList = listDescendantId(role.getId());
            int roleNum = updateIdList.size();

            roleMapper.locking(updateIdList, 0);

            Long oldId = info.getId();
            int oldAmount = roleNum * -2;
            roleMapper.lftAdd(oldId, oldAmount, range);
            roleMapper.rgtAdd(oldId, oldAmount, range);

            Long newParentId = newParentRole.getId();
            int newAmount = roleNum * 2;
            roleMapper.lftAdd(newParentId, newAmount, range);
            roleMapper.rgtAdd(newParentId, newAmount, range);

            roleMapper.locking(updateIdList, 1);
            int amount = getRoleByIdOrName(role.getParentId(), null, userId).getRgt() - info.getRgt() - 1;
            int level = newParentRole.getLevel() + 1 - info.getLevel();
            roleMapper.selfAndDescendant(updateIdList, amount, level);

            role.setLevel(newParentRole.getLevel() + 1);
        }
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void enableRole(Long id, StatusEnum status, Long userId) {
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

        // 求出要删除的角色所有子孙角色
        List<Long> idList = listDescendantId(id);
        String ids = StringUtils.join(idList, ',');

        // 先求出要删除的角色的所有信息，利用左值与右值计算出要删除的角色数量。
        // 删除角色数=(角色右值-角色左值+1)/2
        Role info = roleMapper.selectByPrimaryKey(id);
        int deleteAmount = info.getRgt() - info.getLft() + 1;

        // 更新此角色之后的相关角色左右值
        roleMapper.lftAdd(id, -deleteAmount, null);
        roleMapper.rgtAdd(id, -deleteAmount, null);

        // 批量删除角色及子孙角色
        roleMapper.deleteByIds(ids);

        // 删除角色关联的资源
        clearResource(id);

        // 删除角色关联的用户
        clearUser(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveRole(Long sourceId, Long targetId, Long userId) {
        List<Role> roleList = listRole(null, userId);

        Optional<Role> optionalSource = roleList.stream().filter(it -> it.getId().equals(sourceId)).findFirst();
        Role sourceInfo = optionalSource.orElse(null);

        Optional<Role> optionalTarget = roleList.stream().filter(it -> it.getId().equals(targetId)).findFirst();
        Role targetInfo = optionalTarget.orElse(null);

        if (null == sourceInfo || null == targetInfo) {
            throw new BusinessException(Department.class, "所选角色错误，超出权限范围。");
        }

        List<Long> allIdList =
            roleList.stream().sorted(Comparator.comparing(Role::getLevel).thenComparing(Role::getLft))
                .map(BaseEntity::getId).collect(Collectors.toList());

        if (!(sourceInfo.getLevel().equals(targetInfo.getLevel()))
            || (Math.abs(allIdList.indexOf(sourceId) - allIdList.indexOf(targetId)) != 1)) {
            throw new BusinessException(Role.class, "角色错误。");
        }

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
    public void grantRoleResource(Long roleId, Long[] plusResourceIds, Long[] minusResourceIds, Long userId) {
        // 删除旧的资源
        if (minusResourceIds.length > 0) {
            Example roleResource = new Example(RoleResource.class);
            roleResource.createCriteria().andIn("resourceId", Arrays.asList(minusResourceIds)).andEqualTo("roleId",
                roleId);
            roleResourceMapper.deleteByExample(roleResource);
        }

        // 添加新的资源
        if (plusResourceIds.length > 0) {
            LocalDateTime now = LocalDateTime.now();
            List<RoleResource> roleResourceList = new ArrayList<>();
            for (Long resourceId : plusResourceIds) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResource.setCreateTime(now);
                roleResourceList.add(roleResource);
            }
            roleResourceMapper.insertList(roleResourceList);
        }
        filterChainManager.reloadFilterChain();
    }

    @Override
    public List<Role> listRoleByUserIdOrName(Long userId, String username) {
        return roleMapper.listByUserIdOrName(userId, username, null);
    }

    /**
     * 清空指定角色资源
     *
     * @param roleId
     *            角色id
     */
    public void clearResource(Long roleId) {
        // 删除旧的资源
        Example roleResource = new Example(RoleResource.class);
        roleResource.createCriteria().andEqualTo("roleId", roleId);
        roleResourceMapper.deleteByExample(roleResource);

        filterChainManager.reloadFilterChain();
    }

    /**
     * 清空指定角色用户
     *
     * @param roleId
     *            角色id
     */
    public void clearUser(Long roleId) {
        // 删除旧的关联用户
        Example roleUser = new Example(UserRole.class);
        roleUser.createCriteria().andEqualTo("roleId", roleId);
        userRoleMapper.deleteByExample(roleUser);
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
        Role childRole = roleMapper.selectByPrimaryKey(role.getId());

        Role parentRole = roleMapper.selectByPrimaryKey(role.getParentId());
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
        //
        List<Role> newParentAncestries = roleMapper.listAncestries(newId, StatusEnum.ENABLED);
        if (newParentAncestries.size() == 0) {
            newParentAncestries.add(role1);
        }

        Long oldId = role2.getId();
        List<Role> oldParentAncestries = roleMapper.listAncestries(oldId, StatusEnum.ENABLED);
        if (oldParentAncestries.size() == 0) {
            oldParentAncestries.add(role2);
        }

        Comparator<Role> comparator = Comparator.comparing(Role::getLft);
        return newParentAncestries.stream().filter(oldParentAncestries::contains).max(comparator)
            .orElseThrow(RuntimeException::new);
    }

}
