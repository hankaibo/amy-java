package cn.mypandora.springboot.modular.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.model.vo.DepartmentTree;
import cn.mypandora.springboot.modular.system.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * DepartmentController
 *
 * @author hankaibo
 * @date 2019/9/25
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * 获取部门树。
     *
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            用户id
     * @return 部门树
     */
    @ApiOperation(value = "获取部门树", notes = "根据状态获取部门树。")
    @GetMapping
    public List<DepartmentTree> listDepartmentTree(
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status,
        Long userId) {
        List<Department> departmentList = departmentService.listDepartment(status, userId);
        return TreeUtil.department2Tree(departmentList);
    }

    /**
     * 获取子部门列表。
     *
     * @param id
     *            主键id
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            用户id
     * @return 某个部门的直接子部门
     */
    @ApiOperation(value = "获取子部门列表", notes = "根据部门id查询其下的所有直接子部门。")
    @GetMapping("/{id}/children")
    public List<Department> listDepartmentChildren(
        @PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status,
        Long userId) {
        return departmentService.listChildrenDepartment(id, status, userId);
    }

    /**
     * 新建部门。
     *
     * @param department
     *            部门数据
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "新建部门", notes = "根据数据新建部门。")
    @PostMapping
    public void addDepartment(@RequestBody @ApiParam(value = "部门数据", required = true) Department department,
        Long userId) {
        departmentService.addDepartment(department, userId);
    }

    /**
     * 获取部门详情。
     *
     * @param id
     *            部门主键id
     * @param userId
     *            用户id
     * @return 部门信息
     */
    @ApiOperation(value = "获取部门详情", notes = "根据部门id查询部门详情。")
    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id,
        Long userId) {
        Department department = departmentService.getDepartmentById(id, userId);
        department.setRgt(null);
        department.setLft(null);
        department.setLevel(null);
        department.setCreateTime(null);
        department.setUpdateTime(null);
        return department;
    }

    /**
     * 更新部门。
     *
     * @param department
     *            部门数据
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "更新部门", notes = "根据部门数据更新。")
    @PutMapping("/{id}")
    public void updateDepartment(@RequestBody @ApiParam(value = "部门数据", required = true) Department department,
        Long userId) {
        departmentService.updateDepartment(department, userId);
    }

    /**
     * 启用禁用部门。
     *
     * @param id
     *            部门主键id
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "启用禁用部门", notes = "启用禁用部门。")
    @PatchMapping("/{id}/status")
    public void enableDepartment(@PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id,
        @RequestParam @ApiParam(value = "状态(1:启用，0:禁用)", required = true) Integer status, Long userId) {
        departmentService.enableDepartment(id, status, userId);
    }

    /**
     * 删除部门。
     *
     * @param id
     *            部门主键id
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "删除部门", notes = "根据部门Id删除一个部门。")
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id,
        Long userId) {
        departmentService.deleteDepartment(id, userId);
    }

    /**
     * 同层级部门的平移。
     *
     * @param sourceId
     *            源id
     * @param targetId
     *            目标id
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "移动部门", notes = "将当前部门上移或下移。")
    @PutMapping
    public void moveDepartment(@RequestParam("from") @ApiParam(value = "源id", required = true) Long sourceId,
        @RequestParam("to") @ApiParam(value = "目标id", required = true) Long targetId, Long userId) {
        if (null == targetId || null == sourceId) {
            return;
        }
        departmentService.moveDepartment(sourceId, targetId, userId);
    }

}
