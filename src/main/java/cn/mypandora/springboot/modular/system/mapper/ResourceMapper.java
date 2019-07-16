package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.modular.system.model.Resource;

import java.util.List;

/**
 * ResourceMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface ResourceMapper extends MyBaseMapper<Resource> {

    /**
     * todo
     *
     * @return list
     */
    List<RolePermRule> selectRoleRules();

}
