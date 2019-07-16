package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.modular.system.model.Resource;
import cn.mypandora.springboot.modular.system.model.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * ResourceService
 *
 * @author hankaibo
 * @date 2019/1/15
 */
public interface ResourceService {

    List<RolePermRule> rolePermRules();

}
