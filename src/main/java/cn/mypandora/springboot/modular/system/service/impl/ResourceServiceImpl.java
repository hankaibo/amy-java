package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.modular.system.mapper.ResourceMapper;
import cn.mypandora.springboot.modular.system.mapper.RoleMapper;
import cn.mypandora.springboot.modular.system.mapper.RoleResourceMapper;
import cn.mypandora.springboot.modular.system.model.po.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ResourceServiceImpl
 *
 * @author hankaibo
 * @date 2019/1/15
 * @see <a href="http://www.spoofer.top/2017/07/14/%E5%9F%BA%E4%BA%8ENested-Sets%E7%9A%84%E6%A0%91%E5%BD%A2%E6%95%B0%E6%8D%AE%E5%BA%93%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1">左右节点树操作</a>
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private ResourceMapper resourceMapper;
    private RoleResourceMapper roleResourceMapper;
    private RoleMapper roleMapper;

    @Autowired
    public ResourceServiceImpl(ResourceMapper resourceMapper, RoleResourceMapper roleResourceMapper, RoleMapper roleMapper) {
        this.resourceMapper = resourceMapper;
        this.roleResourceMapper = roleResourceMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RolePermRule> listRolePermRules() {
        List<RolePermRule> rolePermRuleList = new ArrayList<>();
        List<Resource> resourceList = resourceMapper.listRolePermRules();
        for (Resource resource : resourceList) {
            RolePermRule rolePermRule = new RolePermRule();
            rolePermRule.setUrl(resource.getUri());
            rolePermRule.setNeedRoles(resource.getNeedRoles());
            rolePermRuleList.add(rolePermRule);
        }
        return rolePermRuleList;
    }

    @Override
    public List<Resource> listResource(Map<String, Object> map) {
        List<Resource> allResourceList = resourceMapper.listByUserIdOrName((Long) map.get("userId"), null);

        List<Resource> resourceList = listTopAncestryResource(allResourceList);
        // 自身
        Set<Resource> departmentSet = new HashSet<>(resourceList);
        // 所有后代资源
        for (Resource resource : resourceList) {
            // 将自己上级置为空，方便工具类构建树。
            resource.setParentId(null);
            Map<String, Number> descendantMap = new HashMap<>(2);
            descendantMap.put("id", resource.getId());
            descendantMap.put("status", (Number) map.get("status"));
            descendantMap.put("type", (Number) map.get("type"));
            List<Resource> departmentDescendantList = resourceMapper.listDescendants(descendantMap);
            departmentSet.addAll(departmentDescendantList);
        }
        return departmentSet.stream().sorted(Comparator.comparing(Resource::getLft)).collect(Collectors.toList());
    }

    @Override
    public List<Resource> listChildren(Long id, Map<String, Object> map) {
        List<Resource> allResourceList = listResource(map);

        map.put("id", id);
        List<Resource> resourceList = resourceMapper.listChildren(map);
        return resourceList.stream().filter(allResourceList::contains).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addResource(Resource resource, Long userId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId", userId);
        List<Resource> resourceList = listResource(params);
        if (resourceList.stream().noneMatch(item -> item.getId().equals(resource.getParentId()))) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "父级资源选择错误。");
        }

        Resource parentResource = getResourceById(resource.getParentId(), userId);
        LocalDateTime now = LocalDateTime.now();
        resource.setCreateTime(now);
        resource.setLft(parentResource.getRgt());
        resource.setRgt(parentResource.getRgt() + 1);
        resource.setLft(parentResource.getLevel() + 1);
        resource.setIsUpdate(1);

        Map<String, Number> map = new HashMap<>(2);
        map.put("id", resource.getParentId());
        map.put("amount", 2);
        resourceMapper.lftAdd(map);
        resourceMapper.rgtAdd(map);
        resourceMapper.insert(resource);
        resourceMapper.parentRgtAdd(map);
        // 所有资源默认添加到根角色
        Role role = getRootRole();
        long[] plusId = {resource.getId()};
        roleResourceMapper.grantRoleResource(role.getId(), plusId);
    }

    @Override
    public Resource getResourceById(Long id, Long userId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId", userId);
        List<Resource> resourceList = listResource(params);
        if (resourceList.stream().noneMatch(item -> item.getId().equals(id))) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "无法查看该资源。");
        }

        Resource resource = new Resource();
        resource.setId(id);
        Resource info = resourceMapper.selectByPrimaryKey(resource);
        if (info == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "部门不存在。");
        }
        return info;
    }

    @Override
    public void updateResource(Resource resource, Long userId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId", userId);
        List<Resource> resourceList = listResource(params);
        if (resourceList.stream().noneMatch(item -> item.getId().equals(resource.getParentId()))) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "父级部门错误。");
        }

        if (!isCanUpdateParent(resource)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "不可以选择子部门作为自己的父级。");
        }
        Resource info = getResourceById(resource.getId(), userId);

        if (!info.getParentId().equals(resource.getParentId())) {
            // 求出新旧两个父部门的最近共同祖先部门，确定修改范围
            Resource newParentResource = getResourceById(resource.getParentId(), userId);
            Resource oldParentResource = getResourceById(info.getParentId(), userId);
            Resource commonAncestry = getCommonAncestry(newParentResource, oldParentResource);
            // 要修改的部门及子孙部门共多少个
            List<Long> updateIdList = listDescendantId(resource.getId());
            int departmentNum = updateIdList.size();
            // 首先锁定被修改部门及子孙部门，保证左右值不会被下面操作修改。
            Map<String, Object> lockMap = new HashMap<>(2);
            lockMap.put("idList", updateIdList);
            lockMap.put("isUpdate", 0);
            resourceMapper.locking(lockMap);
            // 旧父部门之后左右值修改
            Map<String, Number> oldParentMap = new HashMap<>(3);
            oldParentMap.put("id", info.getId());
            oldParentMap.put("amount", departmentNum * -2);
            oldParentMap.put("range", commonAncestry.getRgt());
            resourceMapper.lftAdd(oldParentMap);
            resourceMapper.rgtAdd(oldParentMap);
            // 新父部门之后左右值修改
            Map<String, Number> newParentMap = new HashMap<>(2);
            newParentMap.put("id", newParentResource.getId());
            newParentMap.put("amount", departmentNum * 2);
            newParentMap.put("range", commonAncestry.getRgt());
            resourceMapper.lftAdd(newParentMap);
            resourceMapper.rgtAdd(newParentMap);
            resourceMapper.parentRgtAdd(newParentMap);
            // 被修改部门及子孙部门左右值修改
            lockMap.put("isUpdate", 1);
            resourceMapper.locking(lockMap);
            int amount = getResourceById(resource.getParentId(), userId).getRgt() - info.getRgt() - 1;
            int level = newParentResource.getLevel() + 1 - info.getLevel();
            Map<String, Object> updateMap = new HashMap<>(3);
            updateMap.put("idList", updateIdList);
            updateMap.put("amount", amount);
            updateMap.put("level", level);
            resourceMapper.selfAndDescendant(updateMap);
            // 修改本身
            LocalDateTime now = LocalDateTime.now();
            resource.setUpdateTime(now);
            resource.setLevel(newParentResource.getLevel() + 1);
        }
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    public void enableResource(Long id, Map<String, Object> map) {
        List<Resource> resourceList = listResource(map);
        List<Long> idList = listDescendantId(id);
        List<Long> list = resourceList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!list.retainAll(idList)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "部门错误。");
        }

        map.put("idList", idList);
        resourceMapper.enableDescendants(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteResource(Long id, Long userId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId", userId);
        List<Resource> resourceList = listResource(params);
        List<Long> list = resourceList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!list.contains(id)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "部门错误。");
        }

        Resource resource = new Resource();
        resource.setId(id);
        // 先求出要删除的节点的所有信息，利用左值与右值计算出要删除的节点数量。
        // 删除节点数=(节点右值-节点左值+1)/2
        Resource info = resourceMapper.selectByPrimaryKey(resource);
        int deleteAmount = info.getRgt() - info.getLft() + 1;
        // 更新此节点之后的相关节点左右值
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", id);
        map.put("amount", -deleteAmount);
        resourceMapper.lftAdd(map);
        resourceMapper.rgtAdd(map);
        // 求出要删除的资源所有子孙资源的id
        List<Long> idList = listDescendantId(id);
        String ids = StringUtils.join(idList, ',');
        // 批量删除资源及子孙资源
        resourceMapper.deleteByIds(ids);
        // 批量删除资源及子孙资源对应的角色数据
        roleResourceMapper.deleteBatchResourceAllRole(idList.stream().distinct().mapToLong(it -> it).toArray());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveResource(Long sourceId, Long targetId, Long userId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId", userId);
        List<Resource> resourceList = listResource(params);
        List<Long> idList = new ArrayList<>();
        idList.add(sourceId);
        idList.add(targetId);
        List<Long> list = resourceList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (!list.retainAll(idList)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "部门错误。");
        }

        // 先取出源节点与目标节点两者的信息
        Resource sourceResource = new Resource();
        Resource targetResource = new Resource();

        sourceResource.setId(sourceId);
        targetResource.setId(targetId);

        Resource sourceInfo = resourceMapper.selectByPrimaryKey(sourceResource);
        Resource targetInfo = resourceMapper.selectByPrimaryKey(targetResource);

        int sourceAmount = sourceInfo.getRgt() - sourceInfo.getLft() + 1;
        int targetAmount = targetInfo.getRgt() - targetInfo.getLft() + 1;

        List<Long> sourceIdList = listDescendantId(sourceId);
        List<Long> targetIdList = listDescendantId(targetId);

        // 确定方向，目标大于源，下移；反之，上移。
        if (targetInfo.getRgt() > sourceInfo.getRgt()) {
            sourceAmount *= -1;
        } else {
            targetAmount *= -1;
        }
        // 源节点及子孙节点左右值 targetAmount
        Map<String, Object> sourceMap = new HashMap<>(2);
        sourceMap.put("idList", sourceIdList);
        sourceMap.put("amount", targetAmount);
        resourceMapper.selfAndDescendant(sourceMap);
        // 目标节点及子孙节点左右值 sourceAmount
        Map<String, Object> targetMap = new HashMap<>(2);
        targetMap.put("idList", targetIdList);
        targetMap.put("amount", sourceAmount);
        resourceMapper.selfAndDescendant(targetMap);
    }

    @Override
    public List<Resource> listResourceByRoleId(Long roleId) {
        return resourceMapper.listByRoleId(roleId);
    }

    @Override
    public List<Resource> listResourceMenuByUserId(Long userId) {
        return resourceMapper.listResourceMenuByUserId(userId);
    }

    @Override
    public List<Resource> listResourceByUserIdOrName(Long userId, String username) {
        return resourceMapper.listByUserIdOrName(userId, username);
    }

    /**
     * 获取此节点及其子孙节点的id
     *
     * @param id 节点
     * @return 节点集合
     */
    private List<Long> listDescendantId(Long id) {
        Map<String, Number> params = new HashMap<>(1);
        params.put("id", id);
        List<Resource> resourceList = resourceMapper.listDescendants(params);
        List<Long> idList = resourceList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }

    /**
     * 得到根角色
     * <p>
     * 根角色特征：层级level为1，父级parentId为空。
     *
     * @return 根角色
     */
    private Role getRootRole() {
        Role role = new Role();
        role.setLevel(1);
        role.setParentId(null);
        return roleMapper.selectOne(role);
    }

    /**
     * 获取各资源最顶级的祖先资源。
     * 如果一个用户在父级资源、本级资源、子级资源都存在，则只过滤出父级资源，以减少后边重复查询。
     *
     * @param resourceList 资源列表
     * @return 用户所在各资源的最顶级资源。
     */
    private List<Resource> listTopAncestryResource(List<Resource> resourceList) {
        List<Resource> result = new ArrayList<>();
        if (resourceList.size() == 1) {
            return resourceList;
        }
        for (Resource resource : resourceList) {
            if (resourceList
                    .stream()
                    .filter(item -> item.getLevel() < resource.getLevel())
                    .noneMatch(item -> item.getLft() < resource.getLft() && item.getRgt() > resource.getRgt())) {
                result.add(resource);
            }
        }
        return result;
    }

    /**
     * 防止更新资源时，指定自己的下级资源作为自己的父级资源。
     *
     * @param resource 资源
     * @return true可以更新；false不可以更新
     */
    private boolean isCanUpdateParent(Resource resource) {
        Resource child = new Resource();
        child.setId(resource.getId());
        Resource childResource = resourceMapper.selectByPrimaryKey(child);

        Resource parent = new Resource();
        parent.setId(resource.getParentId());
        Resource parentResource = resourceMapper.selectByPrimaryKey(parent);
        return !(parentResource.getLft() >= childResource.getLft() && parentResource.getRgt() <= childResource.getRgt());
    }


    /**
     * 获取两个资源最近的共同祖先资源。
     *
     * @param resource1 第一个资源
     * @param resource2 第二个资源
     * @return 最近的祖先资源
     */
    private Resource getCommonAncestry(Resource resource1, Resource resource2) {
        Map<String, Number> newParentMap = new HashMap<>(2);
        newParentMap.put("id", resource1.getId());
        newParentMap.put("status", 1);
        List<Resource> newParentAncestries = resourceMapper.listAncestries(newParentMap);
        if (newParentAncestries.size() == 0) {
            newParentAncestries.add(resource1);
        }

        Map<String, Number> oldParentMap = new HashMap<>(2);
        oldParentMap.put("id", resource2.getId());
        oldParentMap.put("status", 1);
        List<Resource> oldParentAncestries = resourceMapper.listAncestries(oldParentMap);
        if (oldParentAncestries.size() == 0) {
            oldParentAncestries.add(resource2);
        }

        Comparator<Resource> comparator = Comparator.comparing(Resource::getLft);
        return newParentAncestries.stream().filter(oldParentAncestries::contains).max(comparator).get();
    }
}
