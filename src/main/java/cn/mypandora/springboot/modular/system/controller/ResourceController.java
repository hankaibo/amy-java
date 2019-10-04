package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.enums.TypeEnum;
import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.vo.TreeNode;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
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
    public List<TreeNode> listResource(@RequestParam("type") @ApiParam(value = "资源类型(1资源，2接口)") Integer type) {
        List<Resource> resourceList = resourceService.listAll(type);

        return TreeUtil.lr2Tree(resourceList);
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
        resource.setUpdateTime(null);
        return resource;
    }

    /**
     * 更新资源。
     *
     * @param resource 资源数据
     */
    @ApiOperation(value = "更新资源", notes = "根据资源数据更新资源。")
    @PutMapping("/{id}")
    public void updateResource(@RequestBody @ApiParam(value = "资源数据", required = true) Resource resource) {
        resourceService.updateResource(resource);
    }

    /**
     * 删除资源。
     *
     * @param id 资源主键id
     */
    @ApiOperation(value = "删除资源", notes = "根据资源Id删除资源。")
    @DeleteMapping("/{id}")
    public void deleteResource(@PathVariable("id") @ApiParam(value = "资源主键id", required = true) Long id) {
        resourceService.deleteResource(id);
    }

    /**
     * 添加资源
     */
    @ApiOperation(value = "新建资源", notes = "根据资源数据新建。")
    @PostMapping
    public void addResource(@RequestBody @ApiParam(value = "资源数据", required = true) Resource resource) {
        // 如果没有parentId为空，那么就创建为一个新树的根节点，parentId是空的，level是1。
        if (resource.getParentId() == null) {
            resource.setLft(1);
            resource.setRgt(2);
            resource.setLevel(1);
            resource.setType(TypeEnum.MENU.getValue());
            resourceService.addResource(resource);
        } else {
            Resource info = resourceService.getResourceById(resource.getParentId());
            resource.setLft(info.getRgt());
            resource.setRgt(info.getRgt() + 1);
            resource.setLevel(info.getLevel() + 1);
            resourceService.addResource(resource);
        }
    }

    /**
     * 查询子资源
     *
     * @param id 主键id
     * @return 某个资源的所有直接子资源
     */
    @ApiOperation(value = "查询子资源", notes = "根据主键id查询其下的所有直接子资源。")
    @GetMapping("/{id}/children")
    public List<TreeNode> listChildrenResource(@PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
                                               @RequestParam("type") @ApiParam(value = "资源类型（1菜单，2接口）") Integer type) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("type", type);
        List<Resource> resourceList = resourceService.listChildren(map);

        return TreeUtil.lr2Node(resourceList);
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
    public ResponseEntity<Void> moveUp(@PathVariable @ApiParam(value = "资源数据", required = true) Long id,
                                       @RequestBody @ApiParam(value = "上移(1)或下移(-1)", required = true) Map<String, String> map) {
        String direction = map.get("direction");
        // 获得同层级节点
        List<Resource> resourceList = resourceService.listSiblings(id);
        // 目标节点id
        Long targetId = getTargetId(resourceList, id, direction);
        if (null == targetId) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        resourceService.moveResource(id, targetId);
        return ResponseEntity.ok().build();
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
