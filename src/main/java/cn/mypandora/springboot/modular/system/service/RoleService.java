package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.modular.system.model.po.Role;

import java.util.List;
import java.util.Map;

/**
 * RoleService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RoleService {

    /**
     * 获取所有角色（一次性全部加载，适合数据量少的情况）。
     *
     * @param status 状态(1:启用，0:禁用)，默认为空查询所有。
     * @return 整棵角色树
     */
    List<Role> listAllRole(Integer status);

    /**
     * 获得本角色的直接子角色。
     *
     * @param role     角色条件
     * @return 角色列表
     */
    List<Role> listChildrenRole(Role role);

    /**
     * 根据条件查询角色。
     *
     * @param params 角色条件
     * @return 角色列表
     */
    List<Role> listRoleByCondition(Map<String, Object> params);

    /**
     * 新增角色。
     *
     * @param role 角色
     */
    void addRole(Role role);

    /**
     * 根据角色Id或者名称查询角色。
     *
     * @param id   角色id
     * @param name 角色名称
     * @return 角色信息
     */
    Role getRoleByIdOrName(Long id, String name);

    /**
     * 更新角色。
     *
     * @param role 角色
     */
    void updateRole(Role role);

    /**
     * 启用|禁用角色。 1：开启；0：禁用。
     *
     * @param id     角色id
     * @param status 启用(1),禁用(0)
     */
    void enableRole(Long id, Integer status);

    /**
     * 删除角色。
     *
     * @param id 角色id
     */
    void deleteRole(Long id);

    /**
     * 赋予角色某资源。
     *
     * @param roleId  角色Id
     * @param plusId  增加资源Id集合
     * @param minusId 删除资源Id集合
     */
    void grantRoleResource(Long roleId, long[] plusId, long[] minusId);

    /**
     * 根据用户id或者名称查询用户的所有角色。
     * 默认只查询可用状态的角色。
     *
     * @param userId   用户id
     * @param username 用户名称
     * @return 角色列表
     */
    List<Role> listRoleByUserIdOrName(Long userId, String username);

}
