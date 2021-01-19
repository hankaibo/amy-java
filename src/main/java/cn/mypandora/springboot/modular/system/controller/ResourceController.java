package cn.mypandora.springboot.modular.system.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.mypandora.springboot.core.annotation.NullOrNumber;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.vo.ResourceTree;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * ResourceController
 *
 * @author hankaibo
 * @date 2019/7/17
 */
@Validated
@Api(tags = "资源管理")
@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 获取资源树。
     *
     * @param type
     *            资源类型(1:菜单，2:接口)
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            用户id
     * @return 资源树
     */
    @ApiOperation(value = "获取资源树")
    @GetMapping
    public List<ResourceTree> listResource(
        @Range(min = 1, max = 2) @RequestParam("type") @ApiParam(value = "资源类型(1:菜单，2:接口)") Integer type,
        @NullOrNumber @RequestParam(value = "status",
            required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status,
        @ApiIgnore Long userId) {
        List<Resource> resourceList = resourceService.listResource(type, status, userId);
        return TreeUtil.resource2Tree(resourceList);
    }

    /**
     * 查询子资源列表。
     *
     * @param id
     *            主键id
     * @param type
     *            资源类型(1:菜单，2:接口)
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            用户id
     * @return 某个资源的直接子资源
     */
    @ApiOperation(value = "获取子资源列表", notes = "根据资源id查询其下的所有直接子资源。")
    @GetMapping("/{id}/children")
    public List<Resource> listChildrenResource(
        @Positive @PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
        @Range(min = 1, max = 2) @RequestParam("type") @ApiParam(value = "资源类型（1:菜单，2:接口）") Integer type,
        @NullOrNumber @RequestParam(value = "status",
            required = false) @ApiParam(value = "状态(1:启用，0:禁用)") Integer status,
        @ApiIgnore Long userId) {
        return resourceService.listChildrenResource(id, type, status, userId);
    }

    /**
     * 新建资源。
     *
     * @param resource
     *            资源数据
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "新建资源", notes = "根据数据新建资源。")
    @PostMapping
    public void addResource(@Valid @RequestBody @ApiParam(value = "资源数据", required = true) Resource resource,
        @ApiIgnore Long userId) {
        resourceService.addResource(resource, userId);
    }

    /**
     * 获取资源详情。
     *
     * @param id
     *            资源主键id
     * @param userId
     *            用户id
     * @return 资源信息
     */
    @ApiOperation(value = "获取资源详情", notes = "根据资源id查询资源详情。")
    @GetMapping("/{id}")
    public Resource listResourceById(@Positive @PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id,
        @ApiIgnore Long userId) {
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
     * @param resource
     *            资源数据
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "更新资源", notes = "根据资源数据更新资源。")
    @PutMapping("/{id}")
    public void updateResource(@Valid @RequestBody @ApiParam(value = "资源数据", required = true) Resource resource,
        @ApiIgnore Long userId) {
        resourceService.updateResource(resource, userId);
    }

    /**
     * 启用禁用资源。
     *
     * @param id
     *            资源主键id
     * @param status
     *            状态(1:启用，0:禁用)
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "启用禁用资源", notes = "根据状态启用禁用资源。")
    @PatchMapping("/{id}/status")
    public void enableResource(@Positive @PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id,
        @Range(min = 0, max = 1) @RequestParam("status") @ApiParam(value = "状态(1:启用，0:禁用)") Integer status,
        @ApiIgnore Long userId) {
        resourceService.enableResource(id, status, userId);
    }

    /**
     * 删除资源。
     *
     * @param id
     *            资源主键id
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "删除资源", notes = "根据资源Id删除资源。")
    @DeleteMapping("/{id}")
    public void deleteResource(@Positive @PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id,
        @ApiIgnore Long userId) {
        resourceService.deleteResource(id, userId);
    }

    /**
     * 本功能只针对同层级节点的平移。
     *
     * @param sourceId
     *            源id
     * @param targetId
     *            目标id
     * @param userId
     *            用户id
     */
    @ApiOperation(value = "移动资源", notes = "将当前资源上移或下移。")
    @PutMapping
    public void move(@Positive @RequestParam("from") @ApiParam(value = "源id", required = true) Long sourceId,
        @Positive @RequestParam("to") @ApiParam(value = "目标id", required = true) Long targetId,
        @ApiIgnore Long userId) {
        resourceService.moveResource(sourceId, targetId, userId);
    }

    @ApiOperation(value = "批量导入资源", notes = "批量导入文件中资源。")
    @PostMapping("/batch")
    public void importBatch(
        @NotEmpty @RequestBody @ApiParam(value = "资源列表", required = true) List<Resource> resourceList,
        @ApiIgnore Long userId) {
        resourceService.importBatchResource(resourceList, userId);
    }

}
