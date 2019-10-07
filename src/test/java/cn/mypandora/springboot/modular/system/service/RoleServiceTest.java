package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.SpringbootApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceTest extends SpringbootApplicationTest {

    @Autowired
    private RoleService roleService;

    public void testPageRole() {
    }

    public void testListRole() {
    }

    public void testGetRoleByIdOrName() {
    }

    public void testAddRole() {
    }

    @Test
    public void testDeleteRole() {
        roleService.deleteRole(47L);
    }

    public void testDeleteBatchRole() {
    }

    public void testUpdateRole() {
    }

    public void testEnableRole() {
    }

    public void testGrantRoleResource() {
    }

    public void testListRoleByUserIdOrName() {
    }
}