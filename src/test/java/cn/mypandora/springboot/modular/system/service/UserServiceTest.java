package cn.mypandora.springboot.modular.system.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mypandora.springboot.SpringbootApplicationTest;
import cn.mypandora.springboot.modular.system.model.po.User;

public class UserServiceTest extends SpringbootApplicationTest {

    @Autowired
    private UserService userService;

    public void testPageUser() {}

    public void testGetUserByIdOrName() {}

    public void testAddUser() {}

    public void testGetUserById() {}

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        user.setNickname("管理员");
        user.setStatus(1);
        user.setDepartmentId(1L);
        userService.addUser(user);
        Assert.assertNotNull(user.getId());
    }

    public void testEnableUser() {}

    public void testDeleteUser() {}

    @Test
    public void testDeleteBatchUser() {}

    @Test
    public void testGrantUserRole() {
        long[] add = {6};
        long[] minus = {};
        userService.grantUserRole(1L, add, minus);
    }
}