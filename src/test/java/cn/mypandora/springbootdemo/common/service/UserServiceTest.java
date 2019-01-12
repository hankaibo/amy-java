package cn.mypandora.springbootdemo.common.service;

import cn.mypandora.springbootdemo.SpringbootDemoApplicationTest;
import cn.mypandora.springbootdemo.common.entity.User;
import cn.mypandora.springbootdemo.common.enums.BooleanEnum;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserServiceTest extends SpringbootDemoApplicationTest {
    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setNickName("test");
        user.setEmail("test@email.com");
        user.setStateCode(BooleanEnum.YES.getValue());
        user.setMobile("13412345678");

        userService.addUser(user);

        User result = userService.queryByIdOrName(null, "admin");
        log.info("------------------------------------------------------------");
        log.info("查询结果=> {}", result);
        log.info("------------------------------------------------------------");
        TestCase.assertNotNull(user);
    }
}