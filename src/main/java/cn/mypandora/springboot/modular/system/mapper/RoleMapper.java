package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RoleMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RoleMapper extends MyBaseMapper<Role> {

    /**
     * 根据用户id或名称查询其所有角色信息。
     *
     * @param userId   用户id
     * @param username 用户名称
     * @return 用户的所有角色
     */
    List<Role> selectByUserIdOrName(@Param(value = "userId") Long userId, @Param(value = "username") String username);
}
