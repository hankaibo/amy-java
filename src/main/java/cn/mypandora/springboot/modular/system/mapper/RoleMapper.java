package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.Role;

import java.util.List;
import java.util.Map;

/**
 * RoleMapper
 * 角色树类比部门树。
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RoleMapper extends MyBaseMapper<Role> {

    /**
     * 获取整棵树（一次性全部加载，适合数据量少的情况）。
     *
     * @param map {status:状态(1:启用，0:禁用)}
     * @return 整棵树
     */
    List<Role> listAll(Map<String, Integer> map);

    /**
     * 根据用户id查询其所有角色。
     *
     * @param map {userId:用户id, username:用户名, status:状态(1:启用，0:禁用)}
     * @return 用户所有角色
     */
    List<Role> listByUserIdOrName(Map<String, Object> map);

    /**
     * 获得本角色的所有祖先角色。
     *
     * @param map {id:当前操作部门id, status:状态(1:启用，0:禁用)}
     * @return 本角色的所有祖先角色
     */
    List<Role> listAncestries(Map<String, Number> map);

    /**
     * 获得本角色的所有后代角色。
     *
     * @param map {id:当前操作角色id, status:状态(1:启用，0:禁用)}
     * @return 本角色的所有后代角色
     */
    List<Role> listDescendants(Map<String, Number> map);

    /**
     * 获得本角色的孩子角色。
     *
     * @param map {id:当前操作角色id, status:状态(1:启用，0:禁用)}
     * @return 本角色的孩子角色
     */
    List<Role> listChildren(Map<String, Number> map);

    /**
     * 父角色右值加N。
     *
     * @param map {id:角色id, amount:大于id左值的角色，加上的数值(正数相当于加，负数相当于减)}
     */
    void parentRgtAdd(Map<String, Number> map);

    /**
     * 角色左值加N。
     *
     * @param map {id:角色id, amount:大于id左值的角色，加上的数值(正数相当于加，负数相当于减), range:范围值}
     */
    void lftAdd(Map<String, Number> map);

    /**
     * 角色右值加N。
     *
     * @param map {id:角色id, amount:大于id右值的角色，加上的数值(正数相当于加，负数相当于减), range:范围值}
     */
    void rgtAdd(Map<String, Number> map);

    /**
     * 当前角色集合都加上n。
     *
     * @param map {idList:角色id集合, amount:角色及子孙都要加上的数值, level:原层级加N}
     */
    void selfAndDescendant(Map<String, Object> map);

    /**
     * 启用禁用角色状态。
     *
     * @param map {idList:角色id集合, status:状态(1:启用，0:禁用)}
     */
    void enableDescendants(Map<String, Object> map);

    /**
     * 锁定数据，防止被修改左右值。
     *
     * @param map {idList:部门id集合, isUpdate: 是否可更新状态(1:可更新，0:不可更新)}
     */
    void locking(Map<String, Object> map);

}
