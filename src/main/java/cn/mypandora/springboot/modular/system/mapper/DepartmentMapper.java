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
     * @param status 状态，1启用；0禁用
     * @return 整棵树
     */
    List<Department> listAll(Integer status);

    /**
     * 获得本节点下面的所有后代节点
     *
     * @param id     当前操作节点id
     * @param status 状态，1启用；0禁用
     * @return 本节点下面的所有后代节点
     */
    List<Department> listDescendants(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 获得本节点的孩子节点
     *
     * @param id     当前操作节点id
     * @param status 状态，1启用；0禁用
     * @return 本节点的孩子节点
     */
    List<Department> listChildren(@Param("id") Long id, @Param("status") Integer status);

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
     * 启用禁用节点状态
     *
     * @param idList 节点id集合
     * @param status 状态，1开启；0禁用
     */
    void enableDescendants(@Param("idList") List<Long> idList, @Param("status") Integer status);

}
