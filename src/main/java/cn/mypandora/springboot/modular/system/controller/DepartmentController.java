package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.model.vo.TreeNode;
import cn.mypandora.springboot.modular.system.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * 获取整棵部门树。
     *
     * @return 获取整个部门树
     */
    @ApiOperation(value = "部门列表", notes = "获取整棵部门树。")
    @GetMapping
    public List<TreeNode> listDepartment() {
        List<Department> departmentList = departmentService.listAll();

        return TreeUtil.department2Tree(departmentList);
    }

    /**
     * 查询子部门。
     *
     * @param id 主键id
     * @return 某个部门的所有直接子部门
     */
    @ApiOperation(value = "子部门列表", notes = "根据部门id查询其下的所有直接子部门。")
    @GetMapping("/{id}/children")
    public List<TreeNode> listSubDepartment(@PathVariable("id") @ApiParam(value = "主键id", required = true) Long id) {
        List<Department> departmentList = departmentService.listChildren(id);

        return TreeUtil.department2Node(departmentList);
    }

    /**
     * 查询部门。
     *
     * @param id 部门主键id
     * @return 数据
     */
    @ApiOperation(value = "部门详情", notes = "根据部门id查询部门详情。")
    @GetMapping("/{id}")
    public Department listDepartmentById(@PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id) {
        Department department = departmentService.getDepartmentById(id);
        if (department == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
        }
        department.setRgt(null);
        department.setLft(null);
        department.setLevel(null);
        department.setCreateTime(null);
        department.setUpdateTime(null);
        return department;
    }

    /**
     * 添加部门
     *
     * @return 添加部门
     */
    @ApiOperation(value = "部门新建", notes = "根据数据新建一个部门。")
    @PostMapping
    public ResponseEntity<Void> addDepartment(@RequestBody @ApiParam(value = "部门数据", required = true) Department department) {
        // 如果没有parentId为空，那么就创建为一个新树的根节点，parentId是null，level是1。
        if (department.getParentId() == null || department.getParentId() < 1) {
            department.setLft(1);
            department.setRgt(2);
            department.setLevel(1);
            department.setParentId(null);
            departmentService.addDepartment(department);
        } else {
            Department info = departmentService.getDepartmentById(department.getParentId());
            department.setLft(info.getRgt());
            department.setRgt(info.getRgt() + 1);
            department.setLevel(info.getLevel() + 1);
            departmentService.addDepartment(department);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 更新部门。
     *
     * @param department 部门数据
     * @return 更新结果
     */
    @ApiOperation(value = "部门更新", notes = "根据部门数据更新部门。")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDepartment(@RequestBody @ApiParam(value = "部门数据", required = true) Department department) {
        departmentService.updateDepartment(department);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除部门。
     *
     * @param id 部门主键id
     * @return 删除结果
     */
    @ApiOperation(value = "部门删除", notes = "根据部门Id删除一个部门。")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id) {
        int count = departmentService.countUserById(id);
        if (count > 0) {
            throw new CustomException(HttpStatus.FORBIDDEN.value(), "该部门或子部门有关联用户，不可以删除。");
        }
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 同层级部门的平移。
     *
     * @param id  当前部门id
     * @param map 移动步数(1：上移，-1：下移）
     * @return ok
     */
    @ApiOperation(value = "部门移动", notes = "将当前部门上移或下移。")
    @PutMapping("/{id}/location")
    public ResponseEntity<Void> move(@PathVariable @ApiParam(value = "部门数据", required = true) Long id,
                                     @RequestBody @ApiParam(value = "上移(1)或下移(-1)", required = true) Map<String, String> map) {
        String direction = map.get("direction");
        // 获得同层级节点
        List<Department> departmentList = departmentService.listSiblings(id);
        // 目标节点id
        Long targetId = getTargetId(departmentList, id, direction);
        if (null == targetId) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        departmentService.moveDepartment(id, targetId);
        return ResponseEntity.ok().build();
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
