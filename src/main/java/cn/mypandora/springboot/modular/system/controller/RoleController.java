package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.core.shiro.filter.FilterChainManager;
import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import cn.mypandora.springboot.modular.system.model.vo.ResourceTree;
import cn.mypandora.springboot.modular.system.model.vo.RoleTree;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
     * 获取整棵角色树。
     *
     * @param status 状态(启用:1，禁用:0)
     * @return 角色树
     */
    @ApiOperation(value = "角色树", notes = "根据状态获取整棵角色树。")
    @GetMapping
    public List<RoleTree> listRole(@RequestHeader(value = "Authorization") String authorization,
                                   @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status) {
        // 获取当前登录者的所有角色
        String jwt = JsonWebTokenUtil.unBearer(authorization);
        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        String[] roles = StringUtils.split(jwtAccount.getRoles(), ",");
        // 获取所有后代角色
        Set<Role> roleSet = new HashSet<>();
        for (String name : roles) {
            roleSet.addAll(roleService.listDescendantRole(name));
        }
        return TreeUtil.role2Tree(new ArrayList<>(roleSet));
    }

    /**
     * 获取子角色。
     *
     * @param id     主键id
     * @param status 状态(1:启用，0:禁用)
     * @return 某个角色的分页子数据
     */
    @ApiOperation(value = "子角色列表", notes = "查询子角色列表。")
    @GetMapping("/{id}/children")
    public List<Role> listSubRole(@PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
                                  @RequestParam(value = "status", required = false) @ApiParam(value = "状态") Integer status) {
        Role role = new Role();
        role.setId(id);
        if (status != null) {
            role.setStatus(status);
        }
        return roleService.listChildrenRole(role);
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
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "角色不存在。");
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
     * 启用禁用角色。
     *
     * @param id  角色主键id
     * @param map 状态(1:启用，0:禁用)
     */
    @ApiOperation(value = "角色状态启用禁用", notes = "根据角色id启用或禁用其状态。")
    @PatchMapping("/{id}/status")
    public void enableRole(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
                           @RequestBody @ApiParam(value = "启用(1)，禁用(0)", required = true) Map<String, Integer> map) {
        int status = map.get("status");
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
     * 查询该角色所包含的资源。
     *
     * @param id 角色主键id
     * @return 角色所包含的资源
     */
    @ApiOperation(value = "查询角色的所有资源", notes = "根据角色id查询其包含的资源数据。")
    @GetMapping("/{id}/resources")
    public Map<String, List> listRoleResource(@RequestHeader(value = "Authorization") String authorization,
                                              @PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
                                              @RequestParam(value = "status", required = false) @ApiParam(value = "状态") Integer status) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("status", status);
        // 获取当前登录者的所有角色
        String jwt = JsonWebTokenUtil.unBearer(authorization);
        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        String[] roles = StringUtils.split(jwtAccount.getRoles(), ",");
        // 获取所有后代角色
        Set<Role> roleSet = new HashSet<>();
        for (String name : roles) {
            roleSet.addAll(roleService.listDescendantRole(name));
        }
        // 获取角色的资源
        Set<Resource> resourceSet=new HashSet<>();
        for(Role role: roleSet){
            resourceSet.addAll(resourceService.listResourceByRoleId(role.getId()));
        }
        List<Resource> allResourceList = new ArrayList<>(resourceSet);
        List<Resource> roleResourceList = resourceService.listResourceByRoleId(id);
        List<ResourceTree> resourceTreeList = TreeUtil.resource2Tree(allResourceList);

        Map<String, List> map = new HashMap<>(2);
        map.put("resTree", resourceTreeList);
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
