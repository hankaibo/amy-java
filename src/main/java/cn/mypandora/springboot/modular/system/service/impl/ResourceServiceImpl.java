package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.modular.system.mapper.ResourceMapper;
import cn.mypandora.springboot.modular.system.model.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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
@Service("ResourceService")
public class ResourceServiceImpl implements ResourceService {

    private ResourceMapper resourceMapper;

    @Autowired
    public ResourceServiceImpl(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
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
    public List<Resource> listAll(Integer type) {
        return resourceMapper.listAll(type);
    }

    @Override
    public List<Resource> listDescendants(Map map) {
        return resourceMapper.listDescendants(map);
    }

    @Override
    public List<Resource> listChildren(Map map) {
        return resourceMapper.listChildren(map);
    }

    @Override
    public Resource getParent(Long id) {
        return resourceMapper.getParent(id);
    }

    @Override
    public List<Resource> listAncestries(Long id) {
        return resourceMapper.getAncestries(id);
    }

    @Override
    public List<Resource> listSiblings(Long id) {
        return resourceMapper.listSiblings(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addResource(Resource resource) {
        LocalDateTime now=LocalDateTime.now();
        resource.setCreateTime(now);
        resourceMapper.lftAdd(resource.getParentId(), 2);
        resourceMapper.rgtAdd(resource.getParentId(), 2);
        resourceMapper.insert(resource);
        resourceMapper.parentRgtAdd(resource.getParentId(), 2);
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
        resourceMapper.lftAdd(id, -deleteAmount);
        resourceMapper.rgtAdd(id, -deleteAmount);
        // 求出要删除的节点所有子孙节点
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", id);
        List<Resource> willDelResourceList = resourceMapper.listDescendants(map);
        List<Long> idList = willDelResourceList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        String ids = StringUtils.join(idList, ",");
        // 批量删除节点及子孙节点
        resourceMapper.deleteByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveResource(Long sourceId, Long targetId) {
        // 先取出源节点与目标节点两者的信息
        Resource targetResource = new Resource();
        Resource sourceResource = new Resource();

        targetResource.setId(targetId);
        sourceResource.setId(sourceId);

        Resource targetInfo = resourceMapper.selectByPrimaryKey(targetResource);
        Resource sourceInfo = resourceMapper.selectByPrimaryKey(sourceResource);

        int targetAmount = targetInfo.getRgt() - targetInfo.getLft() + 1;
        int sourceAmount = sourceInfo.getRgt() - sourceInfo.getLft() + 1;

        List<Long> sourceIdList = getDescendantId(sourceId);
        List<Long> targetIdList = getDescendantId(targetId);

        // 确定方向，目标大于源，下移；反之，上移。
        if (targetInfo.getRgt() > sourceInfo.getRgt()) {
            sourceAmount *= -1;
        } else {
            targetAmount *= -1;
        }
        // 源节点及子孙节点左右值 targetAmount
        resourceMapper.selfAndDescendant(sourceIdList, targetAmount);
        // 目标节点及子孙节点左右值 sourceAmount
        resourceMapper.selfAndDescendant(targetIdList, sourceAmount);
    }

    @Override
    public Resource getResourceById(Long id) {
        Resource resource = new Resource();
        resource.setId(id);
        return resourceMapper.selectByPrimaryKey(resource);
    }

    @Override
    public void updateResource(Resource resource) {
        LocalDateTime now=LocalDateTime.now();
        resource.setUpdateTime(now);
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    /**
     * 获取此节点及其子孙节点的id
     *
     * @param id 节点
     * @return 节点集合
     */
    private List<Long> getDescendantId(Long id) {
        Map<String, Number> params = new HashMap<>(1);
        params.put("id", id);
        List<Resource> resourceList = resourceMapper.listDescendants(params);
        List<Long> idList = resourceList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }

    @Override
    public List<Resource> listResourceByRoleId(Long roleId) {
        return resourceMapper.listByRoleId(roleId);
    }

    @Override
    public List<Resource> listResourceByUserId(Long userId) {
        return resourceMapper.listByUserId(userId);
    }

    @Override
    public List<Resource> listResourceByUserIdOrName(Long userId, String username) {
        return resourceMapper.listByUserIdOrName(userId, username);
    }
}
