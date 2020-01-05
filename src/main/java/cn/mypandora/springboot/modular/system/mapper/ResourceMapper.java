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
     * 获取整棵树（一次性全部加载，适合数据量少的情况）。
     *
     * @param map {type:资源类型(1菜单，2接口), status:状态(1:启用，0:禁用)}
     * @return 整棵树
     */
    List<Resource> listAll(Map<String, Object> map);

    /**
     * 获得本资源的所有祖先资源。
     *
     * @param map {id:当前操作资源id, type:资源类型(1菜单，2接口), status:状态(1:启用，0:禁用)}
     * @return 本资源的所有祖先资源
     */
    List<Resource> listAncestries(Map<String, Number> map);

    /**
     * 获得本资源下面的所有后代资源。
     *
     * @param map {id: 前操作资源id, type:资源类型(1菜单，2接口), status:状态(1:启用，0:禁用)}
     * @return 本资源下面的所有后代资源
     */
    List<Resource> listDescendants(Map<String, Number> map);

    /**
     * 获得本资源的孩子资源
     *
     * @param map {id: 前操作资源id, type:资源类型(1菜单，2接口), status:状态(1:启用，0:禁用)}
     * @return 本资源的孩子资源
     */
    List<Resource> listChildren(Map<String, Object> map);

    /**
     * 父资源右值加N
     *
     * @param map map {id:资源id, amount:大于id左值的资源，加上的数值(正数相当于加，负数相当于减)}
     */
    void parentRgtAdd(Map<String, Number> map);

    /**
     * 资源左值加N
     *
     * @param map {id:资源id, amount:大于id左值的资源，加上的数值(正数相当于加，负数相当于减), range:范围值}
     */
    void lftAdd(Map<String, Number> map);

    /**
     * 右资源加N
     *
     * @param map {id:资源id, amount:大于id左值的资源，加上的数值(正数相当于加，负数相当于减), range:范围值}
     */
    void rgtAdd(Map<String, Number> map);

    /**
     * 当前资源集合都加上N。
     *
     * @param map {idList:资源id集合, amount:资源及子孙都要加上的数值, level:原层级加N}
     */
    void selfAndDescendant(Map<String, Object> map);

    /**
     * 启用禁用资源状态。
     *
     * @param map {idList:资源id集合, status:状态(1:启用，0:禁用)}
     */
    void enableDescendants(Map<String, Object> map);

    /**
     * 锁定数据，防止被修改左右值。
     *
     * @param map {idList:部门id集合, isUpdate: 是否可更新状态(1:可更新，0:不可更新)}
     */
    void locking(Map<String, Object> map);

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
