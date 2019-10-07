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
    List<Department> listAll();

    /**
     * 获得本部门下面的所有后代部门。
     *
     * @param id 当前操作部门
     * @return 指定部门下的所有后代部门
     */
    List<Department> listDescendants(Long id);

    /**
     * 获得本部门的孩子部门。
     *
     * @param id 当前操作部门id
     * @return 指定部门下的所有部门
     */
    List<Department> listChildren(Long id);

    /**
     * 获得本部门的父部门。
     *
     * @param id 当前操作部门id
     * @return 本部门的父部门
     */
    Department getParent(Long id);

    /**
     * 获得本部门的祖先部门
     *
     * @param id 当前操作部门id
     * @return 本部门的祖先部门
     */
    List<Department> listAncestries(Long id);

    /**
     * 获得本部门的所有兄弟部门。
     *
     * @param id 当前操作部门id
     * @return 本部门的兄弟部门
     */
    List<Department> listSiblings(Long id);

    /**
     * 添加部门。
     *
     * @param department 部门的信息
     */
    void addDepartment(Department department);

    /**
     * 删除部门。
     *
     * @param id 部门id
     */
    void deleteDepartment(Long id);

    /**
     * 平移部门。
     *
     * @param sourceId 源id
     * @param targetId 目标id
     */
    void moveDepartment(Long sourceId, Long targetId);

    /**
     * 查询一个部门。
     *
     * @param id 当前操作部门id
     * @return 部门信息
     */
    Department getDepartmentById(Long id);

    /**
     * 更新一个部门。
     *
     * @param department 部门信息
     */
    void updateDepartment(Department department);

    /**
     * 根据部门Id查询用户数量（包含子孙部门的用户数量）。
     *
     * @param id 部门Id
     * @return 用户数量
     */
    int countUserById(Long id);

}
