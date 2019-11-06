package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * RoleMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RoleMapper extends MyBaseMapper<Role> {

    /**
     * 获取整棵树（一次性全部加载，适合数据量少的情况）
     *
     * @param map {status:状态(1:启用，0:禁用)}
     * @return 整棵树
     */
    List<Role> listAll(Map<String, Integer> map);

    /**
     * 获得本节点下面的所有后代节点
     *
     * @param map {id:当前操作节点id, status:状态(1:启用，0:禁用)}
     * @return 本节点下面的所有后代节点
     */
    List<Role> listDescendants(Map<String, Number> map);

    /**
     * 获得本节点的孩子节点
     *
     * @param map {id:当前操作节点id, status:状态(1:启用，0:禁用)}
     * @return 本节点的孩子节点
     */
    List<Role> listChildren(Map<String, Number> map);

    /**
     * 父右节点加N
     *
     * @param map {id:节点id, amount:大于id左值的节点，加上的数值(正数相当于加，负数相当于减)}
     */
    void parentRgtAdd(Map<String, Number> map);

    /**
     * 左节点加N
     *
     * @param map {id:节点id, amount:大于id左值的节点，加上的数值(正数相当于加，负数相当于减)}
     */
    void lftAdd(Map<String, Number> map);

    /**
     * 右节点加N
     *
     * @param map {id:节点id, amount:大于id右值的节点，加上的数值(正数相当于加，负数相当于减)}
     */
    void rgtAdd(Map<String, Number> map);

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
     * 根据用户id或名称查询其所有角色信息。
     *
     * @param userId   用户id
     * @param username 用户名称
     * @return 用户的所有角色
     */
    List<Role> selectByUserIdOrName(@Param(value = "userId") Long userId, @Param(value = "username") String username);
}
