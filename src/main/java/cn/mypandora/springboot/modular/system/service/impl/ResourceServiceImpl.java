package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.modular.system.model.Resource;
import cn.mypandora.springboot.modular.system.model.Role;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ResourceServiceImpl
 *
 * @author hankaibo
 * @date 2019/1/15
 */
@Service("ResourceService")
public class ResourceServiceImpl implements ResourceService {

    @Override
    public List<RolePermRule> rolePermRules() {
        List<RolePermRule> rolePermRules = new ArrayList<>();

        RolePermRule foo5 = new RolePermRule();
        foo5.setUrl("/api/v1/users==GET");
        foo5.setNeedRoles("admin,user");
        rolePermRules.add(foo5);

        RolePermRule foo4 = new RolePermRule();
        foo4.setUrl("/api/v1/users/info==GET");
        foo4.setNeedRoles("admin,user");
        rolePermRules.add(foo4);

        return rolePermRules;
    }

}
