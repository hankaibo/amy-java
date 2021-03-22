package cn.mypandora.springboot.modular.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.vo.ResourceTree;
import cn.mypandora.springboot.modular.system.model.vo.RoleGrant;
import cn.mypandora.springboot.modular.system.model.vo.RoleTree;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * RoleController
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Validated
@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;
    private final ResourceService resourceService;

    @Autowired
    public RoleController(RoleService roleService, ResourceService resourceService) {
        this.roleService = roleService;
        this.resourceService = resourceService;
    }

    /**
     * 获取角色树。
     *
     * @param status
     *            状态
     * @param userId
     *            用户id
     * @return 角色树
     */
    @ApiOperation(value = "获取角色树")
    @GetMapping
    public List<RoleTree> listRoleTree(
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态") StatusEnum status,
        @ApiIgnore Long userId) {
        List<Role> roleList = roleService.listRole(status, userId);
        return TreeUtil.role2Tree(roleList);
    }

    /**
     * 获取子角色列表。
     *
     * @param id
     *            主键id
     * @param status
     *            状态
     * @param userId
     *            用户id
     * @return 某个角色的直接子角色
     */
    @ApiOperation(value = "获取子角色列表")
    @GetMapping("/{id}/children")
    public List<Role> listRoleChildren(@Positive @PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态") StatusEnum status,
        @ApiIgnore Long userId) {
        return roleService.listChildrenRole(id, status, userId);
    }

    /**
     * 新建角色。
     *
     * @param role
     *            角色数据
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "新建角色")
    @PostMapping
    public void addRole(@Validated({AddGroup.class}) @RequestBody @ApiParam(value = "角色数据", required = true) Role role,
        @ApiIgnore Long userId) {
        roleService.addRole(role, userId);
    }

    /**
     * 获取角色详情。
     *
     * @param id
     *            角色主键id
     * @param userId
     *            用户id
     * @return 角色信息
     */
    @ApiOperation(value = "获取角色详情")
    @GetMapping("/{id}")
    public Role getRole(@Positive @PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
        @ApiIgnore Long userId) {
        Role role = roleService.getRoleByIdOrName(id, null, userId);
        role.setRgt(null);
        role.setLft(null);
        role.setLevel(null);
        return role;
    }

    /**
     * 更新角色。
     *
     * @param role
     *            角色数据
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "更新角色")
    @PutMapping("/{id}")
    public void updateRole(
        @Validated({UpdateGroup.class}) @RequestBody @ApiParam(value = "角色数据", required = true) Role role,
        @ApiIgnore Long userId) {
        roleService.updateRole(role, userId);
    }

    /**
     * 启用禁用角色。
     *
     * @param id
     *            角色主键id
     * @param status
     *            状态
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "启用禁用角色")
    @PatchMapping("/{id}/status")
    public void enableRole(@Positive @PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
        @RequestParam @ApiParam(value = "状态", required = true) StatusEnum status, @ApiIgnore Long userId) {
        roleService.enableRole(id, status, userId);
    }

    /**
     * 删除角色。
     *
     * @param id
     *            角色主键id
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public void deleteRole(@Positive @PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
        @ApiIgnore Long userId) {
        roleService.deleteRole(id, userId);
    }

    /**
     * 同层级角色的平移。
     *
     * @param sourceId
     *            源id
     * @param targetId
     *            目标id
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "移动角色")
    @PutMapping
    public void moveRole(@Positive @RequestParam("from") @ApiParam(value = "源id", required = true) Long sourceId,
        @Positive @RequestParam("to") @ApiParam(value = "目标id", required = true) Long targetId,
        @ApiIgnore Long userId) {
        roleService.moveRole(sourceId, targetId, userId);
    }

    /**
     * 查询该角色所包含的资源。
     *
     * @param id
     *            角色主键id
     * @return 角色所包含的资源
     */
    @ApiOperation(value = "获取角色资源")
    @GetMapping("/{id}/resources")
    public Map<String, List> listRoleResource(
        @Positive @PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态") StatusEnum status,
        @ApiIgnore Long userId) {
        Role role = roleService.getRoleByIdOrName(id, null, userId);

        // 获取当前角色可分配的所有资源，即父角色资源
        Long[] parentRoleId = new Long[] {role.getParentId()};
        List<Resource> parentResourceList = resourceService.listResourceByRoleIds(parentRoleId, null, status, userId);
        List<ResourceTree> resourceTrees = TreeUtil.resource2Tree(parentResourceList);

        // 获取当前角色已分配资源
        Long[] roleId = new Long[] {id};
        List<Resource> resourceList = resourceService.listResourceByRoleIds(roleId, null, status, userId);

        Map<String, List> map = new HashMap<>(2);
        map.put("resTree", resourceTrees);
        map.put("resSelected", resourceList);

        return map;
    }

    /**
     * 赋予某角色某资源。
     *
     * @param id
     *            角色id
     * @param roleGrant
     *            增加和删除的角色对象
     */
    @ApiOperation(value = "赋予角色资源。")
    @PostMapping("/{id}/resources")
    public void grantRoleResource(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
        @Validated @RequestBody @ApiParam(value = "增加资源与删除资源对象", required = true) RoleGrant roleGrant,
        @ApiIgnore Long userId) {
        Long[] plusResourceIds = roleGrant.getPlusResourceIds();
        Long[] minusResourceIds = roleGrant.getMinusResourceIds();
        roleService.grantRoleResource(id, plusResourceIds, minusResourceIds, userId);
    }

}
