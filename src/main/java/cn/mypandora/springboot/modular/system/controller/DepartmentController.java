package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.model.vo.TreeNode;
import cn.mypandora.springboot.modular.system.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    private final String DOWN = "DOWN";

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * 获取整个部门树。
     *
     * @return 获取整个部门树
     */
    @ApiOperation(value = "查询部门", notes = "获取整棵部门树。")
    @GetMapping
    public Result<List<TreeNode>> listDepartment() {
        List<Department> departmentList = departmentService.loadFullDepartment();

        List<TreeNode> treeNodeList = TreeUtil.lr2Tree(departmentList);
        return ResultGenerator.success(treeNodeList);
    }

    /**
     * 查询部门详细数据。
     *
     * @param id 部门主键id
     * @return 数据
     */
    @ApiOperation(value = "部门详情", notes = "根据部门id查询部门详情。")
    @GetMapping("/{id}")
    public Result<Department> listDepartmentById(@PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id) {
        Department department = departmentService.findDepartmentById(id);
        department.setRgt(null);
        department.setLft(null);
        department.setLevel(null);
        department.setUpdateTime(null);
        return ResultGenerator.success(department);
    }

    /**
     * 更新部门。
     *
     * @param department 部门数据
     * @return 更新结果
     */
    @ApiOperation(value = "更新部门", notes = "根据部门数据更新部门。")
    @PutMapping("/{id}")
    public Result updateDepartment(@RequestBody @ApiParam(value = "部门数据", required = true) Department department) {
        departmentService.updateDepartment(department);
        return ResultGenerator.success();
    }

    /**
     * 删除部门。
     *
     * @param id 部门主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除部门", notes = "根据部门Id删除部门。")
    @DeleteMapping("/{id}")
    public Result deleteDepartment(@PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id) {
        departmentService.delDepartment(id);
        return ResultGenerator.success();
    }

    /**
     * 添加部门
     *
     * @return 添加部门
     */
    @ApiOperation(value = "新建部门", notes = "根据部门数据新建。")
    @PostMapping
    public Result addDepartment(@RequestBody @ApiParam(value = "部门数据", required = true) Department department) {
        // 如果没有parentId为空，那么就创建为一个新树的根节点，parentId是0，level是1。
        if (department.getParentId() == 0) {
            department.setLft(1);
            department.setRgt(2);
            department.setLevel(1);
            departmentService.addDepartment(department);
        } else {
            Department info = departmentService.findDepartmentById(department.getParentId());
            department.setLft(info.getRgt());
            department.setRgt(info.getRgt() + 1);
            department.setLevel(info.getLevel() + 1);
            departmentService.addDepartment(department);
        }
        return ResultGenerator.success();
    }

    /**
     * 查询子部门
     *
     * @param id 主键id
     * @return 某个部门的所有直接子部门
     */
    @ApiOperation(value = "查询子部门", notes = "根据主键id查询其下的所有直接子部门。")
    @GetMapping("/{id}/children")
    public Result<List> listChildrenDepartment(@PathVariable("id") @ApiParam(value = "主键id", required = true) Long id) {
        List<Department> departmentList = departmentService.getDepartmentChild(id);

        List<TreeNode> treeNodeList = TreeUtil.lr2Node(departmentList);
        return ResultGenerator.success(treeNodeList);
    }

    /**
     * 本功能只针对同层级节点的平移。
     *
     * @param id  当前部门id
     * @param map 移动步数(1：上移，-1：下移）
     * @return ok
     */
    @ApiOperation(value = "移动部门", notes = "将当前部门上移或下移。")
    @PutMapping("/{id}/location")
    public Result moveUp(@PathVariable @ApiParam(value = "部门数据", required = true) Long id,
                         @RequestBody @ApiParam(value = "上移(1)或下移(-1)", required = true) Map<String, String> map) {
        String direction = map.get("direction");
        // 获得同层级节点
        List<Department> departmentList = departmentService.getDepartmentSibling(id);
        // 目标节点id
        Long targetId = getTargetId(departmentList, id, direction);
        if (null == targetId) {
            return ResultGenerator.failure("当前部门不能移动。");
        }
        departmentService.moveDepartment(id, targetId);
        return ResultGenerator.success();
    }

    /**
     * 获得目标节点的id。
     *
     * @param departmentList 当前部门所在的数组
     * @param id             当前部门id
     * @param direction      上移下移
     * @return 当前部门的兄弟部门id
     */
    private Long getTargetId(List<Department> departmentList, Long id, String direction) {
        int len = departmentList.size();
        if (len == 0 || len == 1) {
            return null;
        }
        // 获得当前节点的索引
        int index = 0;
        for (int i = 0; i < departmentList.size(); i++) {
            if (departmentList.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        int targetIndex = StringUtils.equals(StringUtils.upperCase(direction), DOWN) ? index + 1 : index - 1;
        // 顶节点上移，不操作；底节点下移，不操作
        if (targetIndex < 0 || targetIndex >= len) {
            return null;
        }
        return departmentList.get(targetIndex).getId();
    }

}
