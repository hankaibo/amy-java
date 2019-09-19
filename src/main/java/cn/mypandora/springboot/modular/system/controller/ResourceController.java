package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.core.enums.TypeEnum;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.vo.TreeNode;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
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
@Api(tags = "资源管理")
@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {

    private final String DOWN = "DOWN";

    private ResourceService resourceService;

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
    public Result<List<TreeNode>> list(@RequestParam("type") @ApiParam(value = "资源类型(1资源，2接口)") Integer type) {
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
    @PutMapping("/{id}")
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
            resourceService.addResource(resource);
        } else {
            Resource info = resourceService.findResourceById(resource.getParentId());
            resource.setLft(info.getRgt());
            resource.setRgt(info.getRgt() + 1);
            resource.setLevel(info.getLevel() + 1);
            resourceService.addResource(resource);
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
     * 本功能只针对同层级节点的平移。
     *
     * @param id  当前资源id
     * @param map 移动步数(1：上移，-1：下移）
     * @return ok
     */
    @ApiOperation(value = "移动资源", notes = "将当前资源上移或下移。")
    @PutMapping("/{id}/location")
    public Result moveUp(@PathVariable @ApiParam(value = "资源数据", required = true) Long id,
                         @RequestBody @ApiParam(value = "上移(1)或下移(-1)", required = true) Map<String, String> map) {
        String direction = map.get("direction");
        // 获得同层级节点
        List<Resource> resourceList = resourceService.getResourceSibling(id);
        // 目标节点id
        Long targetId = getTargetId(resourceList, id, direction);
        if (null == targetId) {
            return ResultGenerator.failure("当前资源不能移动。");
        }
        resourceService.moveResource(id, targetId);
        return ResultGenerator.success();
    }

    /**
     * 获得目标节点的id。
     *
     * @param resourceList 当前资源所在的数组
     * @param id           当前资源id
     * @param direction    上移下移
     * @return 当前资源的兄弟资源id
     */
    private Long getTargetId(List<Resource> resourceList, Long id, String direction) {
        int len = resourceList.size();
        if (len == 0 || len == 1) {
            return null;
        }
        // 获得当前节点的索引
        int index = 0;
        for (int i = 0; i < resourceList.size(); i++) {
            if (resourceList.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        int targetIndex = StringUtils.equals(StringUtils.upperCase(direction), DOWN) ? index + 1 : index - 1;
        // 顶节点上移，不操作；底节点下移，不操作
        if (targetIndex < 0 || targetIndex >= len) {
            return null;
        }
        return resourceList.get(targetIndex).getId();
    }

}
