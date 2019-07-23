package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.po.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserRoleMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface UserRoleMapper extends MyBaseMapper<UserRole> {

    /**
     * 根据用户id与用户名称查询用户的所有角色信息。
     *
     * @param userId   用户id
     * @param username 用户名称
     * @return 用户的所有角色
     */
    List<Role> selectUserRole(@Param(value = "userId") Long userId, @Param(value = "username") String username);

    /**
     * 赋予用户角色。
     *
     * @param userId     用户Id
     * @param roleListId 角色id集合
     * @return 成功与否
     */
    int giveUserRole(@Param(value = "userId") Long userId, @Param(value = "roleListId") Long[] roleListId);

    /**
     * 删除用户角色。
     *
     * @param userId 用户Id
     */
    void deleteUserRole(Long userId);

    /**
     * 批量删除用户角色。
     *
     * @param userListId 用户Id
     */
    void deleteBatchUserRole(@Param(value = "userListId") Long[] userListId);

}
