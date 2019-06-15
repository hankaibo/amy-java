package cn.mypandora.springboot.core.shiro.provider.impl;

import cn.mypandora.springboot.modular.system.mapper.ResourceMapper;
import cn.mypandora.springboot.core.shiro.provider.ShiroFilterRulesProvider;
import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ShiroFilterRulesProviderImpl
 *
 * @author hankaibo
 * @date 2019/1/15
 */
@Service("ShiroFilterRulesProvider")
public class ShiroFilterRulesProviderImpl implements ShiroFilterRulesProvider {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<RolePermRule> loadRolePermRules() {
        return null;
    }
}
