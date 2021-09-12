package cn.mypandora.springboot.modular.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.core.enums.ResourceTypeEnum;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.shiro.filter.FilterChainManager;
import cn.mypandora.springboot.modular.system.model.po.Resource;

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
     * @param type
     *            资源类型
     * @param status
     *            状态
     * @return 整棵树
     */
    List<Resource> listAll(@Param("type") ResourceTypeEnum type, @Param("status") StatusEnum status);

    /**
     * 根据用户id或名称查询其所有资源信息。
     *
     * @param userId
     *            用户id
     * @param username
     *            用户名称
     * @param type
     *            资源类型
     * @param status
     *            状态
     * @return 用户的所有资源
     */
    List<Resource> listByUserIdOrName(@Param("userId") Long userId, @Param("username") String username,
        @Param("type") ResourceTypeEnum type, @Param("status") StatusEnum status);

    /**
     * 获得本资源的所有祖先资源。
     *
     * @param id
     *            当前操作资源id,
     * @param type
     *            资源类型
     * @param status
     *            状态
     * @return 本资源的所有祖先资源
     */
    List<Resource> listAncestries(@Param("id") Long id, @Param("type") ResourceTypeEnum type,
        @Param("status") StatusEnum status);

    /**
     * 获得本资源的所有后代资源。
     *
     * @param id
     *            前操作资源id
     * @param type
     *            资源类型
     * @param status
     *            状态
     * @return 本资源下面的所有后代资源
     */
    List<Resource> listDescendants(@Param("id") Long id, @Param("type") ResourceTypeEnum type,
        @Param("status") StatusEnum status);

    /**
     * 获得本资源的孩子资源
     *
     * @param id
     *            前操作资源id
     * @param type
     *            资源类型
     * @param status
     *            状态
     * @return 本资源的孩子资源
     */
    List<Resource> listChildren(@Param("id") Long id, @Param("type") ResourceTypeEnum type,
        @Param("status") StatusEnum status);

    /**
     * 将树形结构中所有大于当前节点右值的左节点值+N
     * <p>
     * 大于当前节点左值，方便插入到父节点的头；大于当前节点右值，方便插入到父节点末尾
     * </p>
     *
     * @param id
     *            当前资源id
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
     *            当前资源id
     * @param amount
     *            要加的数值
     * @param range
     *            被修改范围的最大右值
     */
    void rgtAdd(@Param("id") Long id, @Param("amount") Integer amount, @Param("range") Integer range);

    /**
     * 当前资源集合都加上N。
     *
     * @param idList
     *            资源id集合
     * @param amount
     *            资源及子孙都要加上的数值
     * @param level
     *            原层级加N
     */
    void selfAndDescendant(@Param("idList") List<Long> idList, @Param("amount") Integer amount,
        @Param("level") Integer level);

    /**
     * 启用禁用资源状态。
     *
     * @param idList
     *            资源id集合
     * @param status
     *            状态
     */
    void enableDescendants(@Param("idList") List<Long> idList, @Param("status") StatusEnum status);

    /**
     * 锁定数据，防止被修改左右值。
     *
     * @param idList
     *            资源id集合
     * @param isUpdate
     *            是否可更新状态(1:可更新，0:不可更新)
     */
    void locking(@Param("idList") List<Long> idList, @Param("isUpdate") Integer isUpdate);

    /**
     * 查询所有的动态url，动态注册到过滤器链中。
     *
     * @return 资源
     * @see FilterChainManager L91
     */
    List<Resource> listRolePermRules();

    /**
     * 根据角色id查询其所有角色。
     *
     * @param roleIds
     *            角色id数组
     * @param type
     *            资源类型
     * @param status
     *            状态
     * @return 角色所有资源
     */
    List<Resource> listByRoleIds(@Param("roleIds") Long[] roleIds, @Param("type") ResourceTypeEnum type,
        @Param("status") StatusEnum status);

}
