package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.vo.ResourceTree;
import cn.mypandora.springboot.modular.system.service.ResourceService;
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

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
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
    public List<ResourceTree> listResource(@RequestParam("type") @ApiParam(value = "资源类型(1菜单，2接口)") Integer type,
                                           @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("type", type);
        map.put("status", status);
        List<Resource> resourceList = resourceService.listAll(map);
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
    public List<Resource> listChildrenResource(@PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
                                                   @RequestParam("type") @ApiParam(value = "资源类型（1菜单，2接口）") Integer type,
                                                   @RequestParam(value = "status", required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("type", type);
        map.put("status", status);
        return resourceService.listChildren(id, map);
    }

    /**
     * 添加资源
     */
    @ApiOperation(value = "资源新建", notes = "根据数据新建。")
    @PostMapping
    public void addResource(@RequestBody @ApiParam(value = "资源数据", required = true) Resource resource) {
        resourceService.addResource(resource);
    }

    /**
     * 查询资源详细数据。
     *
     * @param id 资源主键id
     * @return 数据
     */
    @ApiOperation(value = "资源详情", notes = "根据资源id查询资源详情。")
    @GetMapping("/{id}")
    public Resource listResourceById(@PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id) {
        Resource resource = resourceService.getResourceById(id);
        if (resource == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
        }
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
    public void updateResource(@RequestBody @ApiParam(value = "资源数据", required = true) Resource resource) {
        resourceService.updateResource(resource);
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
    public ResponseEntity<Void> enableResource(@PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id,
                                               @RequestBody @ApiParam(value = "资源状态", required = true) Map<String, Object> map) {
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
    public void deleteResource(@PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id) {
        resourceService.deleteResource(id);
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
    public ResponseEntity<Void> move(@RequestParam("from") @ApiParam(value = "源id", required = true) Long sourceId,
                                     @RequestParam("to") @ApiParam(value = "目标id", required = true) Long targetId) {
        if (null == targetId || null == sourceId) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        resourceService.moveResource(sourceId, targetId);
        return ResponseEntity.ok().build();
    }

}
