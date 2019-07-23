package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.SpringbootApplicationTest;
import cn.mypandora.springboot.core.enums.BooleanEnum;
import cn.mypandora.springboot.modular.system.model.po.Role;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class RoleServiceTest extends SpringbootApplicationTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void addRole() {
        Role role = new Role();
        role.setName("admin");
        role.setCode("admin");
        role.setStatus(BooleanEnum.YES.getValue());

        roleService.addRole(role);

        Role result = roleService.selectRoleByIdOrName(null, "admin");
        log.info("------------------------------------------------------------");
        log.info("查询结果=> {}", result);
        log.info("------------------------------------------------------------");
        TestCase.assertNotNull(result);
    }
}