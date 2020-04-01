package cn.mypandora.springboot.modular.system.mapper;

import org.apache.ibatis.annotations.Param;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.UserRole;

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
     * @param userId
     *            用户Id
     * @param roleIdList
     *            角色id集合
     */
    void grantUserRole(@Param(value = "userId") Long userId, @Param(value = "roleIdList") long[] roleIdList);

    /**
     * 删除用户某些角色。
     *
     * @param userId
     *            用户Id
     * @param roleIdList
     *            角色id集合
     */
    void deleteUserSomeRole(@Param(value = "userId") Long userId, @Param(value = "roleIdList") long[] roleIdList);

    /**
     * 删除用户所有角色。
     *
     * @param userId
     *            用户Id
     */
    void deleteUserAllRole(Long userId);

    /**
     * 删除用户所有角色（批量）。
     *
     * @param userIdList
     *            用户Id
     */
    void deleteBatchUserAllRole(@Param(value = "userIdList") Long[] userIdList);

    /**
     * 删除角色所有用户。
     *
     * @param roleId
     *            角色Id
     */
    void deleteRoleAllUser(Long roleId);

    /**
     * 删除角色所有用户（批量）。
     *
     * @param roleIdList
     *            角色Id集合
     */
    void deleteBatchRoleAllUser(@Param(value = "roleIdList") long[] roleIdList);

}
