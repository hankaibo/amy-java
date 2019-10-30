package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.core.shiro.filter.FilterChainManager;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ResourceMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface ResourceMapper extends MyBaseMapper<Resource> {

    /**
     * 获取整棵树（一次性全部加载，适合数据量少的情况）
     *
     * @param map {type:资源类型(1菜单，2接口), status:状态(1:启用，0:禁用)}
     * @return 整棵树
     */
    List<Resource> listAll(Map<String, Object> map);

    /**
     * 获得本节点下面的所有后代节点
     *
     * @param map {id: 前操作节点id, type:资源类型(1菜单，2接口), status:状态(1:启用，0:禁用)}
     * @return 本节点下面的所有后代节点
     */
    List<Resource> listDescendants(Map<String, Object> map);

    /**
     * 获得本节点的孩子节点
     *
     * @param map {id: 前操作节点id, type:资源类型(1菜单，2接口), status:状态(1:启用，0:禁用)}
     * @return 本节点的孩子节点
     */
    List<Resource> listChildren(Map<String, Object> map);

    /**
     * 父右节点加N
     *
     * @param map map {id:节点id, amount:大于id左值的节点，加上的数值(正数相当于加，负数相当于减)}
     */
    void parentRgtAdd(Map<String, Object> map);

    /**
     * 左节点加N
     *
     * @param map {id:节点id, amount:大于id左值的节点，加上的数值(正数相当于加，负数相当于减)}
     */
    void lftAdd(Map<String, Object> map);

    /**
     * 右节点加N
     *
     * @param map {id:节点id, amount:大于id左值的节点，加上的数值(正数相当于加，负数相当于减)}
     */
    void rgtAdd(Map<String, Object> map);

    /**
     * 当前节点集合都加上n
     *
     * @param map {idList:节点id集合, amount:节点及子孙都要加上的数值}
     */
    void selfAndDescendant(Map<String, Object> map);

    /**
     * 启用禁用节点状态
     *
     * @param map {idList:节点id集合, status:状态(1:启用，0:禁用)}
     */
    void enableDescendants(Map<String, Object> map);

    /**
     * 查询所有的动态url，动态注册到过滤器链中。
     *
     * @return 资源
     * @see FilterChainManager L82
     */
    List<Resource> listRolePermRules();

    /**
     * 根据角色id查询其所有资源信息。
     *
     * @param roleId 角色id
     * @return 用户的所有角色
     */
    List<Resource> listByRoleId(Long roleId);

    /**
     * 根据用户id查询所拥有的菜单
     *
     * @param userId 用户主键id
     * @return 该用户拥有的菜单
     */
    List<Resource> listResourceMenuByUserId(Long userId);

    /**
     * 根据用户id或名称查询其所有资源信息。
     *
     * @param userId   用户id
     * @param username 用户名称
     * @return 用户的所有资源
     */
    List<Resource> listByUserIdOrName(@Param(value = "userId") Long userId, @Param(value = "username") String username);

}
