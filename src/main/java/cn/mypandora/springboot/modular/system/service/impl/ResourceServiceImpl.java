package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.enums.ResourceTypeEnum;
import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.modular.system.mapper.ResourceMapper;
import cn.mypandora.springboot.modular.system.mapper.RoleResourceMapper;
import cn.mypandora.springboot.modular.system.model.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    public ResourceServiceImpl(ResourceMapper resourceMapper, RoleResourceMapper roleResourceMapper) {
        this.resourceMapper = resourceMapper;
        this.roleResourceMapper = roleResourceMapper;
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
    public List<Resource> listAll(Map<String, Object> map) {
        return resourceMapper.listAll(map);
    }

    @Override
    public List<Resource> listChildren(Long id, Map<String,Object> map) {
        map.put("id", id);
        return resourceMapper.listChildren(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addResource(Resource resource) {
        // 如果没有parentId为空，那么就创建为一个新树的根节点，parentId是空的，level是1。
        if (resource.getParentId() == null || resource.getParentId() < 1) {
            resource.setLft(1);
            resource.setRgt(2);
            resource.setLevel(1);
            resource.setParentId(null);
            resource.setType(ResourceTypeEnum.MENU.getValue());
        } else {
            Resource info = getResourceById(resource.getParentId());
            resource.setLft(info.getRgt());
            resource.setRgt(info.getRgt() + 1);
            resource.setLevel(info.getLevel() + 1);
        }
        LocalDateTime now = LocalDateTime.now();
        resource.setCreateTime(now);
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", resource.getParentId());
        map.put("amount", 2);
        resourceMapper.lftAdd(map);
        resourceMapper.rgtAdd(map);
        resourceMapper.insert(resource);
        resourceMapper.parentRgtAdd(map);
    }

    @Override
    public Resource getResourceById(Long id) {
        Resource resource = new Resource();
        resource.setId(id);
        return resourceMapper.selectByPrimaryKey(resource);
    }

    @Override
    public void updateResource(Resource resource) {
        LocalDateTime now = LocalDateTime.now();
        resource.setUpdateTime(now);
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    public void enableResource(Long id, Map<String, Object> map) {
        List<Long> idList = listDescendantId(id);
        map.put("idList", idList);
        resourceMapper.enableDescendants(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteResource(Long id) {
        Resource resource = new Resource();
        resource.setId(id);
        // 先求出要删除的节点的所有信息，利用左值与右值计算出要删除的节点数量。
        // 删除节点数=(节点右值-节点左值+1)/2
        Resource info = resourceMapper.selectByPrimaryKey(resource);
        int deleteAmount = info.getRgt() - info.getLft() + 1;
        // 更新此节点之后的相关节点左右值
        Map<String, Object> map = new HashMap<>(2);
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
    public void moveResource(Long sourceId, Long targetId) {
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
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        List<Resource> resourceList = resourceMapper.listDescendants(params);
        List<Long> idList = resourceList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }
}
