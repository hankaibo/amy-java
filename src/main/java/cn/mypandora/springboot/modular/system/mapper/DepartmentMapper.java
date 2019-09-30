package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DepartmentMapper
 *
 * @author hankaibo
 * @date 2019/9/25
 */
public interface DepartmentMapper extends MyBaseMapper<Department> {

    /**
     * 获取整棵树（一次性全部加载，适合数据量少的情况）
     *
     * @return 整棵树
     */
    List<Department> listAll();

    /**
     * 获取某一层级节点。
     *
     * @param level 节点层级
     * @return 指定层级的树
     * TODO SQL未实现
     */
    List<Department> listByLevel(int level);

    /**
     * 获得本节点下面的所有后代节点
     *
     * @param id 当前操作节点id
     * @return 本节点下面的所有后代节点
     */
    List<Department> listDescendants(Long id);

    /**
     * 获得本节点的孩子节点
     *
     * @param id 当前操作节点id
     * @return 本节点的孩子节点
     */
    List<Department> listChildren(Long id);

    /**
     * 获得本节点的父节点
     *
     * @param id 当前操作节点id
     * @return 本节点的父节点
     */
    Department getParent(Long id);

    /**
     * 获得本节点的祖先节点
     *
     * @param id 当前操作节点id
     * @return 本节点的祖先节点
     */
    List<Department> listAncestries(Long id);

    /**
     * 获得本节点的所有兄弟节点
     *
     * @param id 当前操作节点id
     * @return 本节点的所有兄弟节点
     */
    List<Department> listSiblings(Long id);

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

}
