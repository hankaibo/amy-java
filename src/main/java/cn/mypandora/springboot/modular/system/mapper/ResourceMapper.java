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
    List<Resource> loadFullTree(@Param(value = "type") Integer type);

    /**
     * 获取某一层级节点。
     *
     * @param level 节点层级
     * @return 指定层级的树
     * TODO SQL未实现
     */
    List<Resource> loadTreeWithLevel(int level);

    /**
     * 获得本节点下面的所有后代节点
     *
     * @param params {id:前操作节点id,type 资源类型}
     * @return 本节点下面的所有后代节点
     */
    List<Resource> getDescendant(Map params);

    /**
     * 获得本节点的孩子节点
     *
     * @param params {id:前操作节点id,type 资源类型}
     * @return 本节点的孩子节点
     */
    List<Resource> getChild(Map params);

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
    List<Resource> getAncestry(Long id);

    /**
     * 获得本节点的所有兄弟节点
     *
     * @param id 当前操作节点id
     * @return 本节点的所有兄弟节点
     */
    List<Resource> getSiblings(Long id);

    /**
     * 左节点加2
     *
     * @param id 节点id
     */
    void lftPlus2(Long id);

    /**
     * 右节点加2
     *
     * @param id 节点id
     */
    void rgtPlus2(Long id);

    /**
     * 父右节点加2
     *
     * @param id 节点id
     */
    void parentRgtPlus2(Long id);

    /**
     * 左节点减2
     *
     * @param id 节点id
     */
    void lftMinus2(Long id);

    /**
     * 右节点减2
     *
     * @param id 节点id
     */
    void rgtMinus2(Long id);

    /**
     * 左右节点加2
     *
     * @param id 节点id
     */
    void bothPlus2(Long id);

    /**
     * 左右节点减2
     *
     * @param id 节点id
     */
    void bothMinus2(Long id);

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
     * 查询该角色所包含的资源。
     *
     * @param id 角色id
     * @return 查询该角色所包含的资源
     */
    List<Resource> getResourceByRole(Long id);

    /**
     * 查询所有的动态url，动态注册到过滤器链中。
     * @return 资源
     *
     * @see FilterChainManager L82
     */
    List<Resource> selectRolePermRules();

}
