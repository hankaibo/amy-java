package cn.mypandora.springboot.core.shiro.provider;

import cn.mypandora.springboot.core.shiro.rule.RolePermRule;

import java.util.List;

public interface ShiroFilterRulesProvider {

    List<RolePermRule> loadRolePermRules();
}
