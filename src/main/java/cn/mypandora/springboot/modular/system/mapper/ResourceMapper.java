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
     * @param type 资源类型
     * @return 整棵树
     */
    List<Resource> listAll(@Param(value = "type") Integer type);

    /**
     * 获取某一层级节点。
     *
     * @param level 节点层级
     * @return 指定层级的树
     * TODO SQL未实现
     */
    List<Resource> listByLevel(int level);

    /**
     * 获得本节点下面的所有后代节点
     *
     * @param params {id:前操作节点id,type 资源类型}
     * @return 本节点下面的所有后代节点
     */
    List<Resource> listDescendants(Map params);

    /**
     * 获得本节点的孩子节点
     *
     * @param params {id:前操作节点id,type 资源类型}
     * @return 本节点的孩子节点
     */
    List<Resource> listChildren(Map params);

    /**
     * 获得本节点的父节点
     *
     * @param id 当前操作节点id
     * @return 本节点的父节点
     */
    Resource getParent(Long id);

    /**
     * 获得本节点的祖先节点
     *
     * @param id 当前操作节点id
     * @return 本节点的祖先节点
     */
    List<Resource> getAncestries(Long id);

    /**
     * 获得本节点的所有兄弟节点
     *
     * @param id 当前操作节点id
     * @return 本节点的所有兄弟节点
     */
    List<Resource> listSiblings(Long id);

    /**
     * 父右节点加N
     *
     * @param id     节点id
     * @param amount 大于id左值的节点，操作的数（正数相当于加，负数相当于减）
     */
    void parentRgtAdd(@Param("id") Long id, @Param("amount") Integer amount);

    /**
     * 左节点加N
     *
     * @param id     节点id
     * @param amount 大于id左值的节点，操作的数（正数相当于加，负数相当于减）
     */
    void lftAdd(@Param("id") Long id, @Param("amount") Integer amount);

    /**
     * 右节点加N
     *
     * @param id     节点id
     * @param amount 大于id右值的节点，操作的数（正数相当于加，负数相当于减）
     */
    void rgtAdd(@Param("id") Long id, @Param("amount") Integer amount);

    /**
     * 当前节点集合都加上n
     *
     * @param idList 节点id集合
     * @param amount 节点及子孙都加上 amount
     */
    void selfAndDescendant(@Param("idList") List<Long> idList, @Param("amount") Integer amount);

    /**
     * 判断是否是第一个节点
     *
     * @param id 节点id
     * @return 是第一个节点，返回真；反之，则假。
     */
    boolean isFirstNode(Long id);

    /**
     * 判断是否是最后一个节点
     *
     * @param id 节点id
     * @return 是最后一个节点，返回真；反之，则假。
     */
    boolean isLastNode(Long id);

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
    List<Resource> listByUserId(Long userId);

    /**
     * 根据用户id或名称查询其所有资源信息。
     *
     * @param userId   用户id
     * @param username 用户名称
     * @return 用户的所有资源
     */
    List<Resource> listByUserIdOrName(@Param(value = "userId") Long userId, @Param(value = "username") String username);

}
