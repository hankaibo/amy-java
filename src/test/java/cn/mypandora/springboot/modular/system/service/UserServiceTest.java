package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.SpringbootApplicationTest;
import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.core.enums.BooleanEnum;
import com.github.pagehelper.PageInfo;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserServiceTest extends SpringbootApplicationTest {
    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@email.com");
        user.setState(BooleanEnum.YES.getValue());
        user.setMobile("13412345678");

        userService.addUser(user);

        User result = userService.selectByIdOrName(null, "admin");
        log.info("------------------------------------------------------------");
        log.info("查询结果=> {}", result);
        log.info("------------------------------------------------------------");
        TestCase.assertNotNull(user);
    }

    @Test
    public void updateUser() {
        User user = userService.selectByIdOrName(null, "test");
        user.setPhone("010-12345678");

        userService.updateUser(user);
    }

    @Test
    public void selectById() {
        User user = userService.selectById(2L);
        log.info("------------------------------------------------------------");
        log.info("查询结果=> {}", user);
        log.info("------------------------------------------------------------");
    }

    @Test
    public void selectByPage() {
        PageInfo<User> userList = userService.selectByPage(1, 10, null);
        log.info("------------------------------------------------------------");
        log.info("查询结果=> {}", userList);
        log.info("------------------------------------------------------------");
    }


}
