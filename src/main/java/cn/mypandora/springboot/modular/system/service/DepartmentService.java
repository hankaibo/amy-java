package cn.mypandora.springboot.modular.system.service;

import java.util.List;

import cn.mypandora.springboot.modular.system.model.po.Department;

/**
 * DepartmentService
 *
 * @author hankaibo
 * @date 2019/9/25
 */
public interface DepartmentService {

    /**
     * 获得指定用户的部门树（一次性全部加载，适合数据量少的情况）。
     *
     * @param status
     *            状态(1:启用，0:禁用)，默认为空查询所有
     * @param userId
     *            用户id
     * @return 部门列表
     */
    List<Department> listDepartment(Integer status, Long userId);

    /**
     * 获得本部门的直接子部门。
     *
     * @param id
     *            当前操作部门id
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            用户id
     * @return 部门列表
     */
    List<Department> listChildrenDepartment(Long id, Integer status, Long userId);

    /**
     * 添加部门。
     *
     * @param department
     *            部门数据
     * @param userId
     *            用户id
     */
    void addDepartment(Department department, Long userId);

    /**
     * 查询一个部门。
     *
     * @param id
     *            部门id
     * @param userId
     *            用户id
     * @return 部门信息
     */
    Department getDepartmentById(Long id, Long userId);

    /**
     * 更新部门。
     *
     * @param department
     *            部门数据
     * @param userId
     *            用户id
     */
    void updateDepartment(Department department, Long userId);

    /**
     * 启用禁用部门。
     *
     * @param id
     *            部门id
     * @param status
     *            启用(1),禁用(0)
     * @param userId
     *            用户id
     */
    void enableDepartment(Long id, Integer status, Long userId);

    /**
     * 删除部门。
     *
     * @param id
     *            部门id
     * @param userId
     *            用户id
     */
    void deleteDepartment(Long id, Long userId);

    /**
     * 平移部门。
     *
     * @param sourceId
     *            源部门id
     * @param targetId
     *            目标部门id
     * @param userId
     *            用户id
     */
    void moveDepartment(Long sourceId, Long targetId, Long userId);

}
