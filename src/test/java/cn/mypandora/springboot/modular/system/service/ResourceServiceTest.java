package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.SpringbootApplicationTest;
import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ResourceServiceTest extends SpringbootApplicationTest {

    @Autowired
    private ResourceService resourceService;

    @Test
    public void selectRolePermRules() {
        List<RolePermRule> rolePermRuleList = resourceService.listRolePermRules();
        System.out.println(rolePermRuleList);
    }

    @Test
    public void loadFullResource() {
    }

    @Test
    public void getResourceDescendant() {
    }

    @Test
    public void getResourceChild() {
    }

    @Test
    public void getResourceParent() {
    }

    @Test
    public void getResourceAncestry() {
    }

    @Test
    public void getResourceSibling() {
    }

    @Test
    public void addResource() {
    }

    @Test
    public void delResource() {
        resourceService.deleteResource(47L);
    }

    @Test
    public void moveResource() {
    }

    @Test
    public void findResourceById() {
    }

    @Test
    public void updateResource() {
    }

    @Test
    public void selectResourceByRoleId() {
    }

    @Test
    public void selectResourceByUserId() {
    }

    @Test
    public void selectResourceByUserIdOrName() {
    }

    public void testListRolePermRules() {
    }

    public void testListAll() {
    }

    public void testListDescendants() {
    }

    public void testListChildren() {
    }

    public void testGetParent() {
    }

    public void testListAncestries() {
    }

    public void testListSiblings() {
    }

    public void testTestAddResource() {
    }

    public void testDeleteResource() {
    }

    public void testTestMoveResource() {
    }

    public void testGetResourceById() {
    }

    public void testTestUpdateResource() {
    }

    public void testListResourceByRoleId() {
    }

    public void testListResourceByUserId() {
    }

    public void testListResourceByUserIdOrName() {
    }
}