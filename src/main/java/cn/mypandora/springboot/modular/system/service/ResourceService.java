package cn.mypandora.springboot.modular.system.service;

import java.util.List;

import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.modular.system.model.po.Resource;

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
     * 获得指定用户的资源树（一次性全部加载，适合数据量少的情况）。
     *
     * @param type
     *            资源类型(1:菜单，2:接口)
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            用户id
     * @return 资源列表
     */
    List<Resource> listResource(Integer type, Integer status, Long userId);

    /**
     * 获得本资源的直接子资源。
     *
     * @param id
     *            当前操作资源id
     * @param type
     *            资源类型(1:菜单，2:接口)
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            用户id
     * @return 资源列表
     */
    List<Resource> listChildrenResource(Long id, Integer type, Integer status, Long userId);

    /**
     * 添加资源。
     *
     * @param resource
     *            资源的信息
     * @param userId
     *            用户id
     */
    void addResource(Resource resource, Long userId);

    /**
     * 查询一个资源。
     *
     * @param id
     *            当前操作资源id
     * @param userId
     *            用户id
     * @return 资源信息
     */
    Resource getResourceById(Long id, Long userId);

    /**
     * 更新资源。
     *
     * @param resource
     *            资源信息
     * @param userId
     *            用户id
     */
    void updateResource(Resource resource, Long userId);

    /**
     * 启用禁用资源。
     *
     * @param id
     *            当前操作资源id
     * @param status
     *            资源状态
     * @param userId
     *            用户id
     */
    void enableResource(Long id, Integer status, Long userId);

    /**
     * 删除资源。
     *
     * @param id
     *            要删除的资源ID
     * @param userId
     *            用户id
     */
    void deleteResource(Long id, Long userId);

    /**
     * 平移某个资源
     *
     * @param sourceId
     *            源ID
     * @param targetId
     *            目标ID
     * @param userId
     *            用户id
     */
    void moveResource(Long sourceId, Long targetId, Long userId);

    /**
     * 批量导入资源
     * 
     * @param resourceList
     *            资源列表
     * @param userId
     *            用户id
     */
    void importBatchResource(List<Resource> resourceList, Long userId);

    /**
     * 查询角色所包含的所有资源。
     *
     * @param roleIds
     *            角色主键id
     * @param type
     *            资源类型(1:菜单，2:接口)
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            用户id
     * @return 所有资源数据
     */
    List<Resource> listResourceByRoleIds(Long[] roleIds, Integer type, Integer status, Long userId);

    /**
     * 查询用户的所有资源。 注：按钮（接口）类型的资源，用于前台动态按钮显示与隐藏。
     *
     * @param userId
     *            用户id
     * @param username
     *            用户名称
     * @param type
     *            资源类型(1:菜单，2:接口)
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 角色资源
     */
    List<Resource> listResourceByUserIdOrName(Long userId, String username, Integer type, Integer status);

}
