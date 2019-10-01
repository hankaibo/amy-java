package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.UserRole;
import org.apache.ibatis.annotations.Param;

/**
 * UserRoleMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface UserRoleMapper extends MyBaseMapper<UserRole> {

    /**
     * 赋予用户角色。
     *
     * @param userId     用户Id
     * @param roleListId 角色id集合
     */
    void grantUserRole(@Param(value = "userId") Long userId, @Param(value = "roleListId") long[] roleListId);

    /**
     * 删除用户某些角色。
     *
     * @param userId     用户Id
     * @param roleListId 角色id集合
     */
    void deleteUserSomeRole(@Param(value = "userId") Long userId, @Param(value = "roleListId") long[] roleListId);

    /**
     * 删除用户所有角色。
     *
     * @param userId 用户Id
     */
    void deleteUserAllRole(Long userId);

    /**
     * 批量删除用户角色。
     *
     * @param userListId 用户Id
     */
    void deleteBatchUserAllRole(@Param(value = "userListId") Long[] userListId);

    /**
     * 删除角色所有用户。
     *
     * @param roleId 角色Id
     */
    void deleteRoleAllUser(Long roleId);

    /**
     * 批量删除用户角色。
     *
     * @param roleListId 角色Id集合
     */
    void deleteBatchRoleAllUser(@Param(value = "roleListId") Long[] roleListId);

}
