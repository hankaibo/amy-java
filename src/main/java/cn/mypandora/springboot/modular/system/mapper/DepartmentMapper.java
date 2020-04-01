package cn.mypandora.springboot.modular.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.Department;

/**
 * DepartmentMapper
 *
 * @author hankaibo
 * @date 2019/9/25
 * @see <a href="https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#Parameters">参数</a>
 */
public interface DepartmentMapper extends MyBaseMapper<Department> {

    /**
     * 获取整棵树（一次性全部加载，适合数据量少的情况）。
     *
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 整棵树
     */
    List<Department> listAll(Integer status);

    /**
     * 根据用户id查询其所有部门。
     *
     * @param userId
     *            用户id
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 用户所有部门
     */
    List<Department> listByUserId(@Param("userId") Long userId, @Param("status") Integer status);

    /**
     * 获得本部门的所有祖先部门。
     *
     * @param id
     *            当前操作部门id
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 本部门的所有祖先部门
     */
    List<Department> listAncestries(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 获得本部门的所有后代部门。
     *
     * @param id
     *            当前操作部门id
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 本部门的所有后代部门
     */
    List<Department> listDescendants(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 获得本部门的孩子部门。
     *
     * @param id
     *            当前操作部门id
     * @param status
     *            状态(1:启用，0:禁用)
     * @return 本部门的孩子部门
     */
    List<Department> listChildren(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 父部门右值加N。
     *
     * @param id
     *            部门id
     * @param amount
     *            大于id左值的部门，加上的数值(正数相当于加，负数相当于减)
     */
    void parentRgtAdd(@Param("id") Long id, @Param("amount") Integer amount);

    /**
     * 部门左值加N。
     *
     * @param id
     *            部门id,
     * @param amount
     *            大于id左值的部门，加上的数值(正数相当于加，负数相当于减),
     * @param range
     *            范围值
     */
    void lftAdd(@Param("id") Long id, @Param("amount") Integer amount, @Param("range") Integer range);

    /**
     * 部门右值加N。
     *
     * @param id
     *            部门id
     * @param amount
     *            大于id右值的部门，加上的数值(正数相当于加，负数相当于减)
     * @param range
     *            范围值
     */
    void rgtAdd(@Param("id") Long id, @Param("amount") Integer amount, @Param("range") Integer range);

    /**
     * 当前部门集合都加上N。
     *
     * @param idList
     *            部门id集合
     * @param amount
     *            部门及子孙都要加上的数值
     * @param level
     *            原层级加N
     */
    void selfAndDescendant(@Param("idList") List<Long> idList, @Param("amount") Integer amount,
        @Param("level") Integer level);

    /**
     * 启用禁用部门状态。
     *
     * @param idList
     *            部门id集合
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
