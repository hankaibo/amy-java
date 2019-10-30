package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.Department;

import java.util.List;
import java.util.Map;

/**
 * DepartmentMapper
 * 为了方便后期扩展，将参数封装成 Map 类型了。
 * 如果业务稳定，可自行将其转换为单参数、多参数等简单参数形式。
 *
 * @author hankaibo
 * @date 2019/9/25
 * @see <a href="https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#Parameters">参数</a>
 */
public interface DepartmentMapper extends MyBaseMapper<Department> {

    /**
     * 获取整棵树（一次性全部加载，适合数据量少的情况）
     *
     * @param map {status:状态(1:启用，0:禁用)}
     * @return 整棵树
     */
    List<Department> listAll(Map<String, Integer> map);

    /**
     * 获得本节点下面的所有后代节点
     *
     * @param map {id:当前操作节点id, status:状态(1:启用，0:禁用)}
     * @return 本节点下面的所有后代节点
     */
    List<Department> listDescendants(Map<String, Number> map);

    /**
     * 获得本节点的孩子节点
     *
     * @param map {id:当前操作节点id, status:状态(1:启用，0:禁用)}
     * @return 本节点的孩子节点
     */
    List<Department> listChildren(Map<String, Number> map);

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

}
