package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.modular.system.mapper.ResourceMapper;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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
 */
@Service("ResourceService")
public class ResourceServiceImpl implements ResourceService {

    private ResourceMapper resourceMapper;

    @Autowired
    public ResourceServiceImpl(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    @Override
    public List<RolePermRule> selectRolePermRules() {
        List<RolePermRule> rolePermRules = new ArrayList<>();
        List<Resource> resourceList = resourceMapper.selectRolePermRules();
        for (Resource resource : resourceList) {
            RolePermRule rolePermRule = new RolePermRule();
            rolePermRule.setUrl(resource.getUri());
            rolePermRule.setNeedRoles(resource.getNeedRoles());
        }
        return rolePermRules;
    }

    @Override
    public List<Resource> loadFullResource(Integer type) {
        return resourceMapper.loadFullTree(type);
    }

    @Override
    public List<Resource> getResourceDescendant(Map map) {
        return resourceMapper.getDescendant(map);
    }

    @Override
    public List<Resource> getResourceChild(Map map) {
        return resourceMapper.getChild(map);
    }

    @Override
    public Resource getResourceParent(Long id) {
        return resourceMapper.getParent(id);
    }

    @Override
    public List<Resource> getResourceAncestry(Long id) {
        return resourceMapper.getAncestry(id);
    }

    @Override
    public List<Resource> getResourceSibling(Long id) {
        return resourceMapper.getSiblings(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addResource(Resource resource) {
        resourceMapper.lftPlus2(resource.getParentId());
        resourceMapper.rgtPlus2(resource.getParentId());
        resourceMapper.insert(resource);
        resourceMapper.parentRgtPlus2(resource.getParentId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delResource(Long id) {
        Resource resource = new Resource();
        resource.setId(id);
        // 先求出要删除的节点的所有信息，利用左值与右值计算出要删除的节点数量。
        // 删除节点数=(节点右值-节点左值+1)/2
        Resource info = resourceMapper.selectByPrimaryKey(resource);
        Long leftNode = info.getLft();
        Long rightNode = info.getRgt();
        Long deleteAmount = rightNode - leftNode + 1;
        // 更新此节点之后的相关节点左右值
        resourceMapper.lftMinusN(id, deleteAmount);
        resourceMapper.rgtMinusN(id, deleteAmount);
        // 求出要删除的节点所有子孙节点
        Map<String, Number> map = new HashMap<>(2);
        map.put("id", id);
        List<Resource> willDelResourcList = resourceMapper.getDescendant(map);
        List<Long> idList = willDelResourcList.stream().map(item -> item.getId()).collect(Collectors.toList());
        idList.add(id);
        String ids = StringUtils.join(idList, ",");
        // 批量删除节点及子孙节点
        resourceMapper.deleteByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveUpResource(Long id, Long upId) {
        // 当前节点不是首节点
        if (!resourceMapper.isFirstNode(id)) {
            // 弟弟（自身）节点左右值减2
            resourceMapper.bothMinus2(id);
            // 哥哥节点左右值加去2
            resourceMapper.bothPlus2(upId);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveDownResource(Long id, Long downId) {
        // 当前节点不是末节点
        if (!resourceMapper.isLastNode(id)) {
            // 哥哥节点（自身）左右值加2
            resourceMapper.bothPlus2(id);
            // 弟弟节点左右值减2
            resourceMapper.bothMinus2(downId);
        }
    }

    @Override
    public Resource findResourceById(Long id) {
        Resource resource = new Resource();
        resource.setId(id);
        return resourceMapper.selectByPrimaryKey(resource);
    }

    @Override
    public void updateResource(Resource resource) {
        Date now = new Date(System.currentTimeMillis());
        resource.setModifyTime(now);
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

}
