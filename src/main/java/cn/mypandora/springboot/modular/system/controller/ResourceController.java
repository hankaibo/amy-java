package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
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

import java.util.List;

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

    @Autowired
    public ResourceController(ResourceService resourceService, RoleService roleService, UserService userService) {
        this.resourceService = resourceService;
    }

    /**
     * 获取资源树。
     *
     * @param type   资源类型(1:菜单，2:接口)
     * @param status 状态(1:启用，0:禁用)
     * @param userId 用户id
     * @return 资源树
     */
    @ApiOperation(value = "资源树", notes = "获取资源树。")
    @GetMapping
    public List<ResourceTree> listResource(@RequestParam("type") @ApiParam(value = "资源类型(1:菜单，2:接口)") Integer type,
                                           @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status,
                                           Long userId) {
        List<Resource> resourceList = resourceService.listResource(type, status, userId);
        return TreeUtil.resource2Tree(resourceList);
    }

    /**
     * 查询子资源。
     *
     * @param id     主键id
     * @param type   资源类型(1:菜单，2:接口)
     * @param status 状态(1:启用，0:禁用)
     * @param userId 用户id
     * @return 某个资源的直接子资源
     */
    @ApiOperation(value = "子资源列表", notes = "根据资源id查询其下的所有直接子资源。")
    @GetMapping("/{id}/children")
    public List<Resource> listChildrenResource(@PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
                                               @RequestParam("type") @ApiParam(value = "资源类型（1:菜单，2:接口）") Integer type,
                                               @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status,
                                               Long userId) {
        return resourceService.listChildrenResource(id, type, status, userId);
    }

    /**
     * 添加资源。
     *
     * @param resource 资源数据
     * @param userId   用户id
     */
    @ApiOperation(value = "资源新建", notes = "根据数据新建资源。")
    @PostMapping
    public void addResource(@RequestBody @ApiParam(value = "资源数据", required = true) Resource resource,
                            Long userId) {
        resourceService.addResource(resource, userId);
    }

    /**
     * 查询资源。
     *
     * @param id     资源主键id
     * @param userId 用户id
     * @return 资源信息
     */
    @ApiOperation(value = "资源详情", notes = "根据资源id查询资源详情。")
    @GetMapping("/{id}")
    public Resource listResourceById(@PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id,
                                     Long userId) {
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
     * @param userId   用户id
     */
    @ApiOperation(value = "资源更新", notes = "根据资源数据更新资源。")
    @PutMapping("/{id}")
    public void updateResource(@RequestBody @ApiParam(value = "资源数据", required = true) Resource resource,
                               Long userId) {
        resourceService.updateResource(resource, userId);
    }

    /**
     * 启用禁用资源。
     *
     * @param id     资源主键id
     * @param type   资源类型(1:菜单，2:接口)
     * @param status 状态(1:启用，0:禁用)
     * @param userId 用户id
     */
    @ApiOperation(value = "资源状态启用禁用", notes = "根据状态启用禁用资源。")
    @PatchMapping("/{id}")
    public void enableResource(@PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id,
                               @RequestParam("type") @ApiParam(value = "资源类型（1:菜单，2:接口）") Integer type,
                               @RequestParam("status") @ApiParam(value = "状态(1:启用，0:禁用)") Integer status,
                               Long userId) {
        resourceService.enableResource(id, type, status, userId);
    }

    /**
     * 删除资源。
     *
     * @param id     资源主键id
     * @param userId 用户id
     */
    @ApiOperation(value = "资源删除", notes = "根据资源Id删除资源。")
    @DeleteMapping("/{id}")
    public void deleteResource(@PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id,
                               Long userId) {
        resourceService.deleteResource(id, userId);
    }

    /**
     * 本功能只针对同层级节点的平移。
     *
     * @param sourceId 源id
     * @param targetId 目标id
     * @param userId   用户id
     * @return ok
     */
    @ApiOperation(value = "资源移动", notes = "将当前资源上移或下移。")
    @PutMapping
    public ResponseEntity<Void> move(@RequestParam("from") @ApiParam(value = "源id", required = true) Long sourceId,
                                     @RequestParam("to") @ApiParam(value = "目标id", required = true) Long targetId,
                                     Long userId) {
        if (null == targetId || null == sourceId) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        resourceService.moveResource(sourceId, targetId, userId);
        return ResponseEntity.ok().build();
    }

}
