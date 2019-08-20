package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.modular.system.model.po.Resource;

import java.util.List;
import java.util.Map;

/**
 * ResourceService
 *
 * @author hankaibo
 * @date 2019/1/15
 */
public interface ResourceService {

    /**
     * 查询所有的动态权限菜单。
     *
     * @return 权限菜单
     */
    List<RolePermRule> selectRolePermRules();

    /**
     * 获取所有资源（一次性全部加载，适合数据量少的情况）。
     *
     * @param type 资源类型
     * @return 所有资源数据
     */
    List<Resource> loadFullResource(Integer type);

    /**
     * 获得本资源（节点）下面的所有后代资源（节点）。
     *
     * @param map {id:当前操作资源（节点）id,type:浆糊类型}
     * @return 指定资源下的所有后代资源
     */
    List<Resource> getResourceDescendant(Map map);

    /**
     * 获得本资源（节点）的孩子资源（节点）。
     *
     * @param map {id:当前操作资源（节点）id,type:浆糊类型}
     * @return 指定资源下的所有资源
     */
    List<Resource> getResourceChild(Map map);

    /**
     * 获得本资源（节点）的父资源（节点）
     *
     * @param id 当前操作资源（节点）id
     * @return 本资源的父资源
     */
    Resource getResourceParent(Long id);

    /**
     * 获得本资源（节点）的祖先资源（节点）
     *
     * @param id 当前操作资源（节点）id
     * @return 本资源的祖先资源
     */
    List<Resource> getResourceAncestry(Long id);

    /**
     * 获得本资源(节点)的所有兄弟资源（节点）
     *
     * @param id 当前操作资源（节点）id
     * @return 本资源的兄弟节点
     */
    List<Resource> getResourceSibling(Long id);

    /**
     * 添加孩子资源（节点）
     *
     * @param resource 子资源（节点）的信息
     */
    void addResource(Resource resource);

    /**
     * 删除资源（节点）
     *
     * @param id 要删除的资源ID
     */
    void delResource(Long id);

    /**
     * 平移某个资源（节点）
     *
     * @param sourceId 源（节点）ID
     * @param targetId 目标（节点）ID
     */
    void moveResource(Long sourceId, Long targetId);

    /**
     * 查询一个资源。
     *
     * @param id 当前操作资源（节点）id
     * @return 一个资源
     */
    Resource findResourceById(Long id);

    /**
     * 更新一个资源。
     *
     * @param resource 资源信息
     */
    void updateResource(Resource resource);

    /**
     * 查询角色所包含的所有资源。
     *
     * @param roleId 角色主键id
     * @return 所有资源数据
     */
    List<Resource> selectResourceByRoleId(Long roleId);

    /**
     * 根据用户id查询其拥有的资源。
     *
     * @param userId 用户id
     * @return 用户菜单
     */
    List<Resource> selectResourceByUserId(Long userId);

    /**
     * 根据用户id或者名称查询用户的所有资源。
     *
     * @param userId   用户id
     * @param username 用户名称
     * @return 角色资源
     */
    List<Resource> selectResourceByUserIdOrName(Long userId, String username);

}
