package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.core.shiro.filter.FilterChainManager;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.vo.TreeNode;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RoleController
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private RoleService roleService;
    private ResourceService resourceService;
    private FilterChainManager filterChainManager;

    @Autowired
    public RoleController(RoleService roleService, ResourceService resourceService, FilterChainManager filterChainManager) {
        this.roleService = roleService;
        this.resourceService = resourceService;
        this.filterChainManager = filterChainManager;
    }

    /**
     * 分页查询角色数据。
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @param status   状态(1:启用，0:禁用)
     * @return 分页数据
     */
    @ApiOperation(value = "角色列表", notes = "查询角色列表。")
    @GetMapping
    public PageInfo<Role> pageRole(@RequestParam(value = "current", defaultValue = "1") @ApiParam(value = "页码", required = true) int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "每页条数", required = true) int pageSize,
                                   @RequestParam(value = "status", required = false) @ApiParam(value = "状态") Integer status) {
        Role role = new Role();
        if (status != null) {
            role.setStatus(status);
        }
        return roleService.pageRole(pageNum, pageSize, role);
    }

    /**
     * 新建角色。
     *
     * @param role 角色数据
     */
    @ApiOperation(value = "角色新建", notes = "根据数据新建一个角色。")
    @PostMapping
    public void addRole(@RequestBody @ApiParam(value = "角色数据", required = true) Role role) {
        roleService.addRole(role);
    }

    /**
     * 查询角色详细数据。
     *
     * @param id 角色主键id
     * @return 角色信息
     */
    @ApiOperation(value = "角色详情", notes = "根据角色id查询角色详情。")
    @GetMapping("/{id}")
    public Role getRole(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id) {
        Role role = roleService.getRoleByIdOrName(id, null);
        if (role == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
        }
        role.setCreateTime(null);
        role.setUpdateTime(null);
        return role;
    }

    /**
     * 更新角色。
     *
     * @param role 角色数据
     */
    @ApiOperation(value = "角色更新", notes = "根据角色数据更新角色。")
    @PutMapping("/{id}")
    public void updateRole(@RequestBody @ApiParam(value = "角色数据", required = true) Role role) {
        roleService.updateRole(role);
    }

    /**
     * 启用|禁用角色。
     *
     * @param id  角色主键id
     * @param map 状态(1:启用，0:禁用)
     */
    @ApiOperation(value = "角色状态启用禁用", notes = "根据角色id启用或禁用其状态。")
    @PatchMapping("/{id}")
    public void enableRole(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
                           @RequestBody @ApiParam(value = "启用(1)，禁用(0)", required = true) Map<String, Integer> map) {
        Integer status = map.get("status");
        roleService.enableRole(id, status);
    }

    /**
     * 删除角色。
     *
     * @param id 角色主键id
     */
    @ApiOperation(value = "角色删除", notes = "根据角色Id删除角色。")
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id) {
        roleService.deleteRole(id);
    }

    /**
     * 批量删除角色。
     *
     * @param ids 角色id数组
     */
    @ApiOperation(value = "角色删除(批量)", notes = "根据角色Id批量删除角色。")
    @DeleteMapping
    public void deleteBatchRole(@RequestBody @ApiParam(value = "角色主键数组ids", required = true) Map<String, Long[]> ids) {
        roleService.deleteBatchRole(StringUtils.join(ids.get("ids"), ','));
    }

    /**
     * 查询该角色所包含的资源。
     *
     * @param id 角色主键id
     * @return 角色所包含的资源
     */
    @ApiOperation(value = "查询角色的所有资源", notes = "根据角色id查询其包含的资源数据。")
    @GetMapping("/{id}/resources")
    public Map<String, List> listRoleResource(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("status", 1);
        List<Resource> allResourceList = resourceService.listAll(params);
        List<Resource> roleResourceList = resourceService.listResourceByRoleId(id);
        List<TreeNode> treeNodeList = TreeUtil.lr2Tree(allResourceList);

        Map<String, List> map = new HashMap<>(2);
        map.put("resTree", treeNodeList);
        map.put("resSelected", roleResourceList);
        return map;
    }

    /**
     * 赋予某角色某资源。
     *
     * @param id  角色id
     * @param map 增加和删除的角色对象
     */
    @ApiOperation(value = "赋予角色一些资源。", notes = "根据角色id赋予其一些资源。")
    @PostMapping("/{id}/resources")
    public void grantRoleResource(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
                                  @RequestBody @ApiParam(value = "增加资源与删除资源对象", required = true) Map<String, List<Long>> map) {
        List<Long> plusResource = map.get("plusResource");
        List<Long> minusResource = map.get("minusResource");
        long[] plusId = plusResource.stream().distinct().mapToLong(it -> it).toArray();
        long[] minusId = minusResource.stream().distinct().mapToLong(it -> it).toArray();
        roleService.grantRoleResource(id, plusId, minusId);
        filterChainManager.reloadFilterChain();
    }

}
