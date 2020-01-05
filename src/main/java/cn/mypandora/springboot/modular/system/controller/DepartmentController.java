package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.model.vo.DepartmentTree;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import cn.mypandora.springboot.modular.system.service.DepartmentService;
import cn.mypandora.springboot.modular.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    private DepartmentService departmentService;
    private UserService userService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, UserService userService) {
        this.departmentService = departmentService;
        this.userService = userService;
    }

    /**
     * 获取部门树。
     *
     * @param authorization token
     * @param status        状态(启用:1，禁用:0)
     * @return 部门树
     */
    @ApiOperation(value = "部门树", notes = "根据状态获取部门树。")
    @GetMapping
    public List<DepartmentTree> listDepartmentTree(@RequestHeader(value = "Authorization") String authorization,
                                                   @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status) {
        Long userId = getUserIdFromToken(authorization);
        List<Department> departmentList = departmentService.listDepartment(status, userId);
        return TreeUtil.department2Tree(departmentList);
    }

    /**
     * 获取子部门。
     *
     * @param authorization token
     * @param id            主键id
     * @param status        状态(1:启用，0:禁用)
     * @return 某个部门的直接子部门
     */
    @ApiOperation(value = "子部门列表", notes = "根据部门id查询其下的所有直接子部门。")
    @GetMapping("/{id}/children")
    public List<Department> listDepartmentChildren(@RequestHeader(value = "Authorization") String authorization,
                                                   @PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
                                                   @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status) {
        Long userId = getUserIdFromToken(authorization);
        return departmentService.listDepartmentChildren(id, status, userId);
    }

    /**
     * 添加部门。
     *
     * @param authorization token
     * @param department    部门数据
     * @return 空或异常
     */
    @ApiOperation(value = "部门新建", notes = "根据数据新建一个部门。")
    @PostMapping
    public ResponseEntity<Void> addDepartment(@RequestHeader(value = "Authorization") String authorization,
                                              @RequestBody @ApiParam(value = "部门数据", required = true) Department department) {
        Long userId = getUserIdFromToken(authorization);
        departmentService.addDepartment(department, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询部门。
     *
     * @param authorization token
     * @param id            部门主键id
     * @return 部门对象或空
     */
    @ApiOperation(value = "部门详情", notes = "根据部门id查询部门详情。")
    @GetMapping("/{id}")
    public Department getDepartmentById(@RequestHeader(value = "Authorization") String authorization,
                                        @PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id) {
        Long userId = getUserIdFromToken(authorization);
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
     * @param authorization token
     * @param department    部门数据
     * @return 空或异常
     */
    @ApiOperation(value = "部门更新", notes = "根据部门数据更新部门。")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDepartment(@RequestHeader(value = "Authorization") String authorization,
                                                 @RequestBody @ApiParam(value = "部门数据", required = true) Department department) {
        Long userId = getUserIdFromToken(authorization);
        departmentService.updateDepartment(department, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 启用禁用部门。
     *
     * @param authorization token
     * @param id            部门主键id
     * @param map           状态(启用:1，禁用:0)
     * @return 空或异常
     */
    @ApiOperation(value = "部门启用禁用", notes = "根据部门状态启用禁用部门。")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> enableDepartment(@RequestHeader(value = "Authorization") String authorization,
                                                 @PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id,
                                                 @RequestBody @ApiParam(value = "状态(1:启用，0:禁用)", required = true) Map<String, Integer> map) {
        Long userId = getUserIdFromToken(authorization);
        int count = departmentService.countUserById(id);
        if (count > 0) {
            throw new CustomException(HttpStatus.FORBIDDEN.value(), "该部门或子部门有关联用户，不可以禁用。");
        }
        int status = map.get("status");
        departmentService.enableDepartment(id, status, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除部门。
     *
     * @param authorization token
     * @param id            部门主键id
     * @return 空或异常
     */
    @ApiOperation(value = "部门删除", notes = "根据部门Id删除一个部门。")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@RequestHeader(value = "Authorization") String authorization,
                                                 @PathVariable("id") @ApiParam(value = "部门主键id", required = true) Long id) {
        Long userId = getUserIdFromToken(authorization);
        int count = departmentService.countUserById(id);
        if (count > 0) {
            throw new CustomException(HttpStatus.FORBIDDEN.value(), "该部门或子部门有关联用户，不可以删除。");
        }
        departmentService.deleteDepartment(id, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 同层级部门的平移。
     *
     * @param authorization token
     * @param sourceId      源id
     * @param targetId      目标id
     * @return 空或异常
     */
    @ApiOperation(value = "部门移动", notes = "将当前部门上移或下移。")
    @PutMapping
    public ResponseEntity<Void> moveDepartment(@RequestHeader(value = "Authorization") String authorization,
                                               @RequestParam("from") @ApiParam(value = "源id", required = true) Long sourceId,
                                               @RequestParam("to") @ApiParam(value = "目标id", required = true) Long targetId) {
        Long userId = getUserIdFromToken(authorization);
        if (null == targetId || null == sourceId) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        departmentService.moveDepartment(sourceId, targetId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取用户主键id
     *
     * @param authorization token
     * @return 用户id
     */
    private Long getUserIdFromToken(String authorization) {
        String jwt = JsonWebTokenUtil.unBearer(authorization);
        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        return userService.getUserByIdOrName(null, jwtAccount.getAppId()).getId();
    }

}
