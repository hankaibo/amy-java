package cn.mypandora.springboot.modular.system.service;

import java.util.List;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.modular.system.model.po.Role;

/**
 * RoleService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RoleService {

    /**
     * 获得指定用户的角色树（一次性全部加载，适合数据量少的情况）。
     *
     * @param status
     *            状态，默认为空查询所有
     * @param userId
     *            用户id
     * @return 角色列表
     */
    List<Role> listRole(StatusEnum status, Long userId);

    /**
     * 获得本角色的直接子角色。
     *
     * @param id
     *            当前操作角色id
     * @param status
     *            状态
     * @param userId
     *            用户id
     * @return 角色列表
     */
    List<Role> listChildrenRole(Long id, StatusEnum status, Long userId);

    /**
     * 添加角色。
     *
     * @param role
     *            角色数据
     * @param userId
     *            用户id
     */
    void addRole(Role role, Long userId);

    /**
     * 根据角色Id或者名称查询角色。
     *
     * @param id
     *            角色id
     * @param name
     *            角色名称
     * @param userId
     *            用户id
     * @return 角色信息
     */
    Role getRoleByIdOrName(Long id, String name, Long userId);

    /**
     * 更新角色。
     *
     * @param role
     *            角色数据
     * @param userId
     *            用户id
     */
    void updateRole(Role role, Long userId);

    /**
     * 启用禁用角色。
     *
     * @param id
     *            角色id
     * @param status
     *            状态
     * @param userId
     *            用户id
     */
    void enableRole(Long id, StatusEnum status, Long userId);

    /**
     * 删除角色。
     *
     * @param id
     *            角色id
     * @param userId
     *            用户id
     */
    void deleteRole(Long id, Long userId);

    /**
     * 平移角色。
     *
     * @param sourceId
     *            源id
     * @param targetId
     *            目标id
     * @param userId
     *            用户id
     */
    void moveRole(Long sourceId, Long targetId, Long userId);

    /**
     * 赋予角色某资源。
     *
     * @param roleId
     *            角色Id
     * @param plusResourceIds
     *            增加资源Id数组
     * @param minusResourceIds
     *            删除资源Id数组
     * @param userId
     *            用户id
     */
    void grantRoleResource(Long roleId, Long[] plusResourceIds, Long[] minusResourceIds, Long userId);

    /**
     * 根据用户id或者名称查询用户的所有角色。 默认只查询可用状态的角色。
     *
     * @param userId
     *            用户id
     * @param username
     *            用户名称
     * @return 角色列表
     */
    List<Role> listRoleByUserIdOrName(Long userId, String username);

}
