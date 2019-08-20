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
