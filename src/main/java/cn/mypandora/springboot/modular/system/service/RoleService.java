package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.modular.system.model.po.Role;

import java.util.List;

/**
 * RoleService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RoleService {

    /**
     * 赋予某角色某资源。
     *
     * @param roleId     角色Id
     * @param resourceId 资源Id
     * @return 成功or失败
     */
    boolean authorityRoleResource(Long roleId, Long resourceId);

    /**
     * 删除某角色的某资源。
     *
     * @param roleId     角色Id
     * @param resourceId 资源Id
     * @return 成功or失败
     */
    boolean deleteAuthorityRoleResource(Long roleId, Long resourceId);

    /**
     * 查询所有的角色。
     *
     * @return 角色列表
     */
    List<Role> selectAll();


    /**
     * 根据角色Id或者名称查询角色。
     *
     * @param roleId   角色id
     * @param roleName 角色名称
     * @return 角色信息
     */
    Role queryRoleByIdOrName(Long roleId, String roleName);

    /**
     * 新增角色。
     *
     * @param role 角色
     */
    void addRole(Role role);

    /**
     * 删除角色。
     *
     * @param roleId 角色id
     */
    void deleteRole(Long roleId);

    /**
     * 更新角色。
     *
     * @param role 角色
     */
    void updateRole(Role role);
}
