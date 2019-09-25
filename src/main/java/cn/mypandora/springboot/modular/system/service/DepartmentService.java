package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.modular.system.model.po.Department;

import java.util.List;

/**
 * DepartmentService
 *
 * @author hankaibo
 * @date 2019/9/25
 */
public interface DepartmentService {

    /**
     * 获取所有部门（一次性全部加载，适合数据量少的情况）。
     *
     * @return 所有部门数据
     */
    List<Department> loadFullDepartment();

    /**
     * 获得本部门（节点）下面的所有后代部门（节点）。
     *
     * @param id 当前操作部门（节点）
     * @return 指定部门下的所有后代部门
     */
    List<Department> getDepartmentDescendant(Long id);

    /**
     * 获得本部门（节点）的孩子部门（节点）。
     *
     * @param id 当前操作部门（节点）
     * @return 指定部门下的所有部门
     */
    List<Department> getDepartmentChild(Long id);

    /**
     * 获得本部门（节点）的父部门（节点）
     *
     * @param id 当前操作部门（节点）id
     * @return 本部门的父部门
     */
    Department getDepartmentParent(Long id);

    /**
     * 获得本部门（节点）的祖先部门（节点）
     *
     * @param id 当前操作部门（节点）id
     * @return 本部门的祖先部门
     */
    List<Department> getDepartmentAncestry(Long id);

    /**
     * 获得本部门(节点)的所有兄弟部门（节点）
     *
     * @param id 当前操作部门（节点）id
     * @return 本部门的兄弟节点
     */
    List<Department> getDepartmentSibling(Long id);

    /**
     * 添加孩子部门（节点）
     *
     * @param department 子部门（节点）的信息
     */
    void addDepartment(Department department);

    /**
     * 删除部门（节点）
     *
     * @param id 要删除的部门ID
     */
    void delDepartment(Long id);

    /**
     * 平移某个部门（节点）
     *
     * @param sourceId 源（节点）ID
     * @param targetId 目标（节点）ID
     */
    void moveDepartment(Long sourceId, Long targetId);

    /**
     * 查询一个部门。
     *
     * @param id 当前操作部门（节点）id
     * @return 一个部门
     */
    Department findDepartmentById(Long id);

    /**
     * 更新一个部门。
     *
     * @param department 部门信息
     */
    void updateDepartment(Department department);

}
