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
     * @param status 状态，1开启；0禁用
     * @return 所有部门数据
     */
    List<Department> listAll(Integer status);

    /**
     * 获得本部门的直接子部门。
     *
     * @param id     当前操作部门id
     * @param status 状态，1开启；0禁用
     * @return 指定部门下的所有部门
     */
    List<Department> listChildren(Long id, Integer status);

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
     * 启用禁用部门。
     *
     * @param id     用户id
     * @param status 启用(1),禁用(0)
     */
    void enableDepartment(Long id, Integer status);

    /**
     * 根据部门Id查询用户数量（包含子孙部门的用户数量）。
     *
     * @param id 部门id
     * @return 用户数量
     */
    int countUserById(Long id);

    /**
     * 判断父id是否存在。
     *
     * @param parentId 父主键id
     * @return true，存在；false，不存在
     */
    boolean isExistParentId(Long parentId);

}
