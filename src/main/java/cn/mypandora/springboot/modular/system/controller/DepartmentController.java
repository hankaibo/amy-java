package cn.mypandora.springboot.modular.system.controller;

import java.util.List;

import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.model.vo.DepartmentTree;
import cn.mypandora.springboot.modular.system.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * DepartmentController
 *
 * @author hankaibo
 * @date 2019/9/25
 */
@Validated
@Api(tags = "部门管理")
@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * 获取部门树。
     *
     * @param status
     *            状态
     * @param userId
     *            当前登录用户id
     * @return 部门树
     */
    @ApiOperation(value = "获取部门树")
    @GetMapping
    public List<DepartmentTree> listDepartmentTree(
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态") StatusEnum status,
        @ApiIgnore Long userId) {
        List<Department> departmentList = departmentService.listDepartment(status, userId);
        return TreeUtil.department2Tree(departmentList);
    }

    /**
     * 获取子部门列表。
     *
     * @param id
     *            主键id
     * @param status
     *            状态
     * @param userId
     *            当前登录用户id
     * @return 某个部门的直接子部门
     */
    @ApiOperation(value = "获取子部门列表")
    @GetMapping("/{id}/children")
    public List<Department> listDepartmentChildren(
        @Positive @PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态") StatusEnum status,
        @ApiIgnore Long userId) {
        return departmentService.listChildrenDepartment(id, status, userId);
    }

    /**
     * 新建部门。
     *
     * @param department
     *            部门数据
     * @param userId
     *            当前登录用户id
     */
    @ApiOperation(value = "新建部门")
    @PostMapping
    public void addDepartment(
        @Validated({AddGroup.class}) @RequestBody @ApiParam(value = "部门数据", required = true) Department department,
        @ApiIgnore Long userId) {
        departmentService.addDepartment(department, userId);
    }

    /**
     * 获取部门详情。
     *
     * @param id
     *            部门主键id
     * @param userId
     *            当前登录用户id
     * @return 部门信息
     */
    @ApiOperation(value = "获取部门详情")
    @GetMapping("/{id}")
    public Department getDepartmentById(
        @Positive @PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id, @ApiIgnore Long userId) {
        Department department = departmentService.getDepartmentById(id, userId);
        department.setRgt(null);
        department.setLft(null);
        department.setLevel(null);
        return department;
    }

    /**
     * 更新部门。
     *
     * @param department
     *            部门数据
     * @param userId
     *            当前登录用户id
     */
    @ApiOperation(value = "更新部门")
    @PutMapping("/{id}")
    public void updateDepartment(
        @Validated({UpdateGroup.class}) @RequestBody @ApiParam(value = "部门数据", required = true) Department department,
        @ApiIgnore Long userId) {
        departmentService.updateDepartment(department, userId);
    }

    /**
     * 启用禁用部门。
     *
     * @param id
     *            部门主键id
     * @param status
     *            状态
     * @param userId
     *            当前登录用户id
     */
    @ApiOperation(value = "启用禁用部门")
    @PatchMapping("/{id}/status")
    public void enableDepartment(@Positive @PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id,
        @RequestParam @ApiParam(value = "状态", required = true) StatusEnum status, @ApiIgnore Long userId) {
        departmentService.enableDepartment(id, status, userId);
    }

    /**
     * 删除部门。
     *
     * @param id
     *            部门主键id
     * @param userId
     *            当前登录用户id
     */
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{id}")
    public void deleteDepartment(@Positive @PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id,
        @ApiIgnore Long userId) {
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
     *            当前登录用户id
     */
    @ApiOperation(value = "移动部门")
    @PutMapping
    public void moveDepartment(@Positive @RequestParam("from") @ApiParam(value = "源id", required = true) Long sourceId,
        @Positive @RequestParam("to") @ApiParam(value = "目标id", required = true) Long targetId,
        @ApiIgnore Long userId) {
        departmentService.moveDepartment(sourceId, targetId, userId);
    }

}
