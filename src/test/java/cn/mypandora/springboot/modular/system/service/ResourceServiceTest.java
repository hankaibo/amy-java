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
        List<RolePermRule> rolePermRuleList = resourceService.selectRolePermRules();
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
        resourceService.delResource(132L);
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
}