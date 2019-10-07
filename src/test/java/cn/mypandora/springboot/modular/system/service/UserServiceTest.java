package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.SpringbootApplicationTest;
import cn.mypandora.springboot.modular.system.model.po.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends SpringbootApplicationTest {

    @Autowired
    private UserService userService;

    public void testPageUser() {
    }

    public void testGetUserByIdOrName() {
    }

    public void testGetUserById() {
    }

    public void testAddUser() {
    }

    public void testDeleteUser() {
    }

    @Test
    public void testDeleteBatchUser() {
        userService.deleteBatchUser("16,96,103,104");
        long total = userService.pageUser(1, 100, null).getTotal();
        Assert.assertEquals(total, 0);
    }

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

    public void testEnableUser() {
    }

    public void testGrantUserRole() {
    }
}