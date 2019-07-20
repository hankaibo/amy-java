package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.core.enums.TypeEnum;
import cn.mypandora.springboot.core.utils.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.vo.TreeNode;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "资源管理", description = "资源相关接口")
@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 获取整个资源树。
     *
     * @return 获取整个资源树
     */
    @ApiOperation(value = "查询资源", notes = "获取整棵资源树。")
    @GetMapping
    public Result<List> list(@RequestParam("type") @ApiParam(value = "资源类型(1资源，2接口)") Integer type) {
        List<Resource> resourceList = resourceService.loadFullResource(type);

        List<TreeNode> treeNodeList = TreeUtil.lr2Tree(resourceList);
        return ResultGenerator.success(treeNodeList);
    }

    /**
     * 查询资源详细数据。
     *
     * @param id 资源主键id
     * @return 数据
     */
    @ApiOperation(value = "资源详情", notes = "根据资源id查询资源详情。")
    @GetMapping("/{id}")
    public Result<Resource> listById(@PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id) {
        Resource resource = resourceService.findResourceById(id);
        resource.setRgt(null);
        resource.setLft(null);
        resource.setLevel(null);
        resource.setModifyTime(null);
        return ResultGenerator.success(resource);
    }

    /**
     * 更新资源。
     *
     * @param resource 资源数据
     * @return 更新结果
     */
    @ApiOperation(value = "更新资源", notes = "根据资源数据更新资源。")
    @PutMapping
    public Result update(@RequestBody @ApiParam(value = "资源数据", required = true) Resource resource) {
        resourceService.updateResource(resource);
        return ResultGenerator.success();
    }

    /**
     * 删除资源。
     *
     * @param id 资源主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除资源", notes = "根据资源Id删除资源。")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id) {
        resourceService.delResource(id);
        return ResultGenerator.success();
    }

    /**
     * 添加资源
     *
     * @return 添加资源
     */
    @ApiOperation(value = "新建资源", notes = "根据资源数据新建。")
    @PostMapping
    public Result insert(@RequestBody @ApiParam(value = "资源数据", required = true) Resource resource) {
        // 如果没有parentId为空，那么就创建为一个新树的根节点，parentId是空的，level是1。
        if (resource.getParentId() == null) {
            resource.setLft(1L);
            resource.setRgt(2L);
            resource.setLevel(1L);
            resource.setType(TypeEnum.MENU.getValue());
            resourceService.addResource(0L, resource);
        } else {
            Resource info = resourceService.findResourceById(resource.getParentId());
            resource.setLft(info.getRgt());
            resource.setRgt(info.getRgt() + 1);
            resource.setLevel(info.getLevel() + 1);
            resourceService.addResource(resource.getParentId(), resource);
        }
        return ResultGenerator.success();
    }

    /**
     * 查询子资源
     *
     * @param id 主键id
     * @return 某个资源的所有直接子资源
     */
    @ApiOperation(value = "查询子资源", notes = "根据主键id查询其下的所有直接子资源。")
    @GetMapping("/{id}/children")
    public Result<List> selectChildren(@PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
                                       @RequestParam("type") @ApiParam(value = "资源类型（1菜单，2接口）") Integer type) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("type", type);
        List<Resource> resourceList = resourceService.getResourceChild(map);

        List<TreeNode> treeNodeList = TreeUtil.lr2Node(resourceList);
        return ResultGenerator.success(treeNodeList);
    }

    /**
     * @param id  当前资源id
     * @param map 移动步数(1：上移，-1：下移）
     * @return ok
     */
    @ApiOperation(value = "移动资源", notes = "将当前资源上移或下移。")
    @PutMapping("/{id}")
    public Result moveUp(@PathVariable @ApiParam(value = "资源数据", required = true) Long id,
                         @RequestBody @ApiParam(value = "上移(1)或下移(-1)", required = true) Map<String, Long> map) {
        Long step = map.get("step");
        // 左资源升序，保证有序
        List<Resource> resourceList = resourceService.getResourceSibling(id);
        // 上移与下移资源id
        Long upId;
        Long downId;
        Map<String, Long> siblingId = getSiblingId(resourceList, id);
        if (siblingId.isEmpty()) {
            return ResultGenerator.failure("当前资源没有兄弟资源，不能移动。");
        } else {
            upId = siblingId.get("upId");
            downId = siblingId.get("downId");
        }

        if (step == 1L) {
            resourceService.moveUpResource(id, upId);
        }
        if (step == -1L) {
            resourceService.moveDownResource(id, downId);
        }
        return ResultGenerator.success();
    }

    /**
     * 获得当前资源的兄弟资源的id。
     *
     * @param resourceList 当前资源所在的数组
     * @param id           当前资源
     * @return 当前资源的兄弟资源id
     */
    private Map<String, Long> getSiblingId(List<Resource> resourceList, Long id) {
        Map<String, Long> result = new HashMap<>(2);
        Long upId = null;
        Long downId = null;

        if (resourceList.size() == 0 || resourceList.size() == 1) {
            return result;
        }
        for (int i = 0; i < resourceList.size(); i++) {
            if (resourceList.get(i).getId().equals(id)) {
                if (i == 0) {
                    upId = -1L;
                    downId = resourceList.get(i + 1).getId();
                    break;
                }
                if (i == resourceList.size() - 1) {
                    downId = -1L;
                    upId = resourceList.get(i - 1).getId();
                    break;
                }
                upId = resourceList.get(i - 1).getId();
                downId = resourceList.get(i + 1).getId();
            }
        }
        result.put("upId", upId);
        result.put("downId", downId);
        return result;
    }

}
