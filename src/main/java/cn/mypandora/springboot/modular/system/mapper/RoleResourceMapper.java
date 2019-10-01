package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.RoleResource;
import org.apache.ibatis.annotations.Param;

/**
 * RoleResourceMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RoleResourceMapper extends MyBaseMapper<RoleResource> {

    /**
     * 赋予角色资源。
     *
     * @param roleId         角色Id
     * @param resourceListId 资源id集合
     */
    void grantRoleResource(@Param(value = "roleId") Long roleId, @Param(value = "resourceListId") long[] resourceListId);

    /**
     * 删除角色某些资源。
     *
     * @param roleId         角色Id
     * @param resourceListId 资源id集合
     */
    void deleteRoleSomeResource(@Param(value = "roleId") Long roleId, @Param(value = "resourceListId") long[] resourceListId);

    /**
     * 删除角色所有资源。
     *
     * @param roleId 角色Id
     */
    void deleteRoleAllResource(Long roleId);

    /**
     * 批量删除角色资源。
     *
     * @param roleListId 角色Id
     * @see <a href="https://chenzhou123520.iteye.com/blog/1921284">mybatis foreach参数问题</a>
     */
    void deleteBatchRoleAllResource(@Param(value = "roleListId") Long[] roleListId);

    /**
     * 删除资源所有的角色。
     *
     * @param resourceId 资源Id
     */
    void deleteResourceAllRole(Long resourceId);

    /**
     * 批量删除资源角色。
     *
     * @param resourceListId 资源Id集合
     */
    void deleteBatchResourceAllRole(@Param(value = "resourceListId") Long[] resourceListId);
}
