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
    List<RolePermRule> listRolePermRules();

    /**
     * 获取所有资源（一次性全部加载，适合数据量少的情况）。
     *
     * @param map {type:资源类型(1菜单，2接口), status:状态(1开启；0禁用)}
     * @return 所有资源数据
     */
    List<Resource> listAll(Map<String, Object> map);

    /**
     * 获得本资源的孩子资源。
     *
     * @param id  当前操作资源id
     * @param map {type: 资源类型, status: 资源状态}
     * @return 指定资源下的所有资源
     */
    List<Resource> listChildren(Long id, Map map);

    /**
     * 添加孩子资源
     *
     * @param resource 子资源的信息
     */
    void addResource(Resource resource);

    /**
     * 查询一个资源。
     *
     * @param id 当前操作资源id
     * @return 一个资源
     */
    Resource getResourceById(Long id);

    /**
     * 更新一个资源。
     *
     * @param resource 资源信息
     */
    void updateResource(Resource resource);

    /**
     * 启用禁用部门。
     *
     * @param id  当前操作资源id
     * @param map {type: 资源类型, status: 资源状态}
     */
    void enableResource(Long id, Map<String, Object> map);

    /**
     * 删除资源
     *
     * @param id 要删除的资源ID
     */
    void deleteResource(Long id);

    /**
     * 平移某个资源
     *
     * @param sourceId 源ID
     * @param targetId 目标ID
     */
    void moveResource(Long sourceId, Long targetId);

    /**
     * 查询角色所包含的所有资源。
     *
     * @param roleId 角色主键id
     * @return 所有资源数据
     */
    List<Resource> listResourceByRoleId(Long roleId);

    /**
     * 根据用户id查询其拥有的资源。
     * 注：菜单类型的资源，用于前台动态菜单构造。
     *
     * @param userId 用户id
     * @return 用户菜单
     */
    List<Resource> listResourceMenuByUserId(Long userId);

    /**
     * 根据用户id或者名称查询用户的所有资源。
     * 注：按钮（接口）类型的资源，用于前台动态按钮显示与隐藏。
     *
     * @param userId   用户id
     * @param username 用户名称
     * @return 角色资源
     */
    List<Resource> listResourceByUserIdOrName(Long userId, String username);

}
