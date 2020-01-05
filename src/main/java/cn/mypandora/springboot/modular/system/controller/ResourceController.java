package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import cn.mypandora.springboot.modular.system.model.vo.ResourceTree;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
import cn.mypandora.springboot.modular.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ResourceController
 *
 * @author hankaibo
 * @date 2019/7/17
 */
@Api(tags = "资源管理")
@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {

    private ResourceService resourceService;
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public ResourceController(ResourceService resourceService, RoleService roleService, UserService userService) {
        this.resourceService = resourceService;
        this.roleService = roleService;
        this.userService = userService;
    }

    /**
     * 获取整棵资源树。
     *
     * @param type   资源类型(1菜单，2接口)
     * @param status 状态(1:启用，0:禁用)
     * @return 资源树
     */
    @ApiOperation(value = "资源列表", notes = "获取整棵资源树。")
    @GetMapping
    public List<ResourceTree> listResource(@RequestHeader(value = "Authorization") String authorization,
                                           @RequestParam("type") @ApiParam(value = "资源类型(1菜单，2接口)") Integer type,
                                           @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status) {
        Long userid = getUserIdFromToken(authorization);
        Map<String, Object> map = new HashMap<>(1);
        map.put("userId", userid);
        map.put("status", status);
        List<Resource> resourceList = resourceService.listResource(map);
        return TreeUtil.resource2Tree(resourceList);
    }

    /**
     * 查询子资源
     *
     * @param id     主键id
     * @param type   资源类型(1菜单，2接口)
     * @param status 状态(启用:1，禁用:0)
     * @return 某个资源的所有直接子资源
     */
    @ApiOperation(value = "子资源列表", notes = "根据资源id查询其下的所有直接子资源。")
    @GetMapping("/{id}/children")
    public List<Resource> listChildrenResource(@RequestHeader(value = "Authorization") String authorization,
                                               @PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
                                               @RequestParam("type") @ApiParam(value = "资源类型（1菜单，2接口）") Integer type,
                                               @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status) {
        Long userId = getUserIdFromToken(authorization);

        Map<String, Object> map = new HashMap<>(3);
        map.put("userId", userId);
        map.put("type", type);
        map.put("status", status);
        return resourceService.listChildren(id, map);
    }

    /**
     * 添加资源
     */
    @ApiOperation(value = "资源新建", notes = "根据数据新建。")
    @PostMapping
    public void addResource(@RequestHeader(value = "Authorization") String authorization,
                            @RequestBody @ApiParam(value = "资源数据", required = true) Resource resource) {
        Long userId = getUserIdFromToken(authorization);
        resourceService.addResource(resource, userId);
    }

    /**
     * 查询资源详细数据。
     *
     * @param id 资源主键id
     * @return 数据
     */
    @ApiOperation(value = "资源详情", notes = "根据资源id查询资源详情。")
    @GetMapping("/{id}")
    public Resource listResourceById(@RequestHeader(value = "Authorization") String authorization,
                                     @PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id) {
        Long userId = getUserIdFromToken(authorization);
        Resource resource = resourceService.getResourceById(id, userId);
        resource.setRgt(null);
        resource.setLft(null);
        resource.setLevel(null);
        resource.setCreateTime(null);
        resource.setUpdateTime(null);
        return resource;
    }

    /**
     * 更新资源。
     *
     * @param resource 资源数据
     */
    @ApiOperation(value = "资源更新", notes = "根据资源数据更新资源。")
    @PutMapping("/{id}")
    public void updateResource(@RequestHeader(value = "Authorization") String authorization,
                               @RequestBody @ApiParam(value = "资源数据", required = true) Resource resource) {
        Long userId = getUserIdFromToken(authorization);
        resourceService.updateResource(resource, userId);
    }

    /**
     * 启用禁用资源。
     *
     * @param id  资源主键id
     * @param map {type:资源类型(1菜单，2接口), status:状态(1:启用，0:禁用)}
     * @return 更新结果
     */
    @ApiOperation(value = "资源状态启用禁用", notes = "根据状态启用禁用资源。")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> enableResource(@RequestHeader(value = "Authorization") String authorization,
                                               @PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id,
                                               @RequestBody @ApiParam(value = "资源状态", required = true) Map<String, Object> map) {
        Long userId = getUserIdFromToken(authorization);
        map.put("userId", userId);
        resourceService.enableResource(id, map);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除资源。
     *
     * @param id 资源主键id
     */
    @ApiOperation(value = "资源删除", notes = "根据资源Id删除资源。")
    @DeleteMapping("/{id}")
    public void deleteResource(@RequestHeader(value = "Authorization") String authorization,
                               @PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id) {
        Long userId = getUserIdFromToken(authorization);
        resourceService.deleteResource(id, userId);
    }

    /**
     * 本功能只针对同层级节点的平移。
     *
     * @param sourceId 源id
     * @param targetId 目标id
     * @return ok
     */
    @ApiOperation(value = "资源移动", notes = "将当前资源上移或下移。")
    @PutMapping
    public ResponseEntity<Void> move(@RequestHeader(value = "Authorization") String authorization,
                                     @RequestParam("from") @ApiParam(value = "源id", required = true) Long sourceId,
                                     @RequestParam("to") @ApiParam(value = "目标id", required = true) Long targetId) {
        Long userId = getUserIdFromToken(authorization);
        if (null == targetId || null == sourceId) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        resourceService.moveResource(sourceId, targetId, userId);
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
