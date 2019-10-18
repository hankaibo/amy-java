package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.SpringbootApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceTest extends SpringbootApplicationTest {

    @Autowired
    private RoleService roleService;

    public void testPageRole() {
    }

    public void testListRoleByCondition() {
    }

    public void testAddRole() {
    }

    public void testGetRoleByIdOrName() {
    }

    public void testUpdateRole() {
    }

    public void testEnableRole() {
    }

    @Test
    public void testDeleteRole() {
        roleService.deleteRole(47L);
    }

    public void testDeleteBatchRole() {
    }

    public void testGrantRoleResource() {
    }

    public void testListRoleByUserIdOrName() {
    }

}