package cn.mypandora.springboot.modular.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.Role;

/**
 * RoleMapper 角色树类比部门树。
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RoleMapper extends MyBaseMapper<Role> {

    /**
     * 获取整棵树（一次性全部加载，适合数据量少的情况）。
     *
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 整棵树
     */
    List<Role> listAll(Integer status);

    /**
     * 根据用户id查询其所有角色。
     *
     * @param userId
     *            用户id
     * @param username
     *            用户名
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 用户所有角色
     */
    List<Role> listByUserIdOrName(@Param("userId") Long userId, @Param("username") String username,
        @Param("status") Integer status);

    /**
     * 获得本角色的所有祖先角色。
     *
     * @param id
     *            当前操作角色id
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 本角色的所有祖先角色
     */
    List<Role> listAncestries(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 获得本角色的所有后代角色。
     *
     * @param id
     *            当前操作角色id
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 本角色的所有后代角色
     */
    List<Role> listDescendants(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 获得本角色的孩子角色。
     *
     * @param id
     *            当前操作角色id
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 本角色的孩子角色
     */
    List<Role> listChildren(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 将树形结构中所有大于当前节点右值的左节点值+N
     * <p>
     * 大于当前节点左值，方便插入到父节点的头；大于当前节点右值，方便插入到父节点末尾
     * </p>
     * 
     * @param id
     *            当前角色id
     * @param amount
     *            要加的数值
     * @param range
     *            被修改范围的最大左值
     */
    void lftAdd(@Param("id") Long id, @Param("amount") Integer amount, @Param("range") Integer range);

    /**
     * 将树形结构中所有大于当前节点右值的右节点值+N *
     * <p>
     * 大于当前节点左值，方便插入到父节点的头；大于当前节点右值，方便插入到父节点末尾 *
     * </p>
     * 
     * @param id
     *            当前角色id
     * @param amount
     *            要加的数值
     * @param range
     *            被修改范围的最大右值
     */
    void rgtAdd(@Param("id") Long id, @Param("amount") Integer amount, @Param("range") Integer range);

    /**
     * 当前角色集合都加上N。
     *
     * @param idList
     *            角色id集合
     * @param amount
     *            角色及子孙都要加上的数值
     * @param level
     *            原层级加N
     */
    void selfAndDescendant(@Param("idList") List<Long> idList, @Param("amount") Integer amount,
        @Param("level") Integer level);

    /**
     * 启用禁用角色状态。
     *
     * @param idList
     *            角色id集合
     * @param status
     *            状态(1:启用，0:禁用)
     */
    void enableDescendants(@Param("idList") List<Long> idList, @Param("status") Integer status);

    /**
     * 锁定数据，防止被修改左右值。
     *
     * @param idList
     *            部门id集合
     * @param isUpdate
     *            是否可更新状态(1:可更新，0:不可更新)
     */
    void locking(@Param("idList") List<Long> idList, @Param("isUpdate") Integer isUpdate);

}
