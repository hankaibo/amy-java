package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.User;

import java.util.List;

/**
 * UserMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface UserMapper extends MyBaseMapper<User> {
    /**
     * 查询某个用户的所有角色信息
     *
     * @param userId 用户id
     * @return 某个用户的角色
     */
    String selectRoleByUserId(Long userId);

    /**
     * 查询某个角色的所有用户信息
     *
     * @param roleId 角色id
     * @return 某个角色的所有用户
     */
    List<User> selectUserByRoleId(Long roleId);
}
