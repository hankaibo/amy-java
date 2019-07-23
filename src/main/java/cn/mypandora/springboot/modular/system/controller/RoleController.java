package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.core.utils.TreeUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.vo.TreeNode;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
import com.github.pagehelper.PageInfo;
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
 * RoleController
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Api(tags = "角色管理", description = "角色相关接口")
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
     * 分页查询角色数据。
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 分页数据
     */
    @ApiOperation(value = "角色列表", notes = "查询角色列表")
    @GetMapping
    public Result<PageInfo<Role>> listAll(@RequestParam(value = "pageNum", defaultValue = "1") @ApiParam(value = "页码", required = true) int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "每页条数", required = true) int pageSize) {
        return ResultGenerator.success(roleService.selectRolePage(pageNum, pageSize, null));
    }

    /**
     * 查询角色详细数据。
     *
     * @param id 角色主键id
     * @return 数据
     */
    @ApiOperation(value = "角色详情", notes = "根据角色id查询角色详情。")
    @GetMapping("/{id}")
    public Result<Role> selectOneById(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id) {
        Role role = roleService.selectRoleByIdOrName(id, null);
        return ResultGenerator.success(role);
    }

    /**
     * 新建角色。
     *
     * @param role 角色数据
     * @return 新建结果
     */
    @ApiOperation(value = "新建角色", notes = "根据角色数据新建角色。")
    @PostMapping
    public Result insert(@RequestBody @ApiParam(value = "角色数据", required = true) Role role) {
        roleService.addRole(role);
        return ResultGenerator.success();
    }

    /**
     * 删除角色。
     *
     * @param id 角色主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除角色", notes = "根据角色Id删除角色。")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id) {
        roleService.deleteRole(id);
        return ResultGenerator.success();
    }

    /**
     * 批量删除角色。
     *
     * @param ids 角色id数组
     * @return 删除结果
     */
    @ApiOperation(value = "删除角色(批量)", notes = "根据角色Id批量删除角色。")
    @DeleteMapping
    public Result remove(@RequestBody @ApiParam(value = "角色主键数组ids", required = true) Map<String, Long[]> ids) {
        roleService.deleteBatchRole(StringUtils.join(ids.get("ids"), ","));
        return ResultGenerator.success();
    }


    /**
     * 更新角色。
     *
     * @param role 角色数据
     * @return 更新结果
     */
    @ApiOperation(value = "更新角色", notes = "根据角色数据更新角色。")
    @PutMapping("/{id}")
    public Result update(@RequestBody @ApiParam(value = "角色数据", required = true) Role role) {
        roleService.updateRole(role);
        return ResultGenerator.success();
    }

    /**
     * 启用|禁用角色。
     *
     * @param id     角色主键id
     * @param status 启用:1，禁用:0
     * @return 启用成功与否。
     */
    @ApiOperation(value = "启用|禁用角色", notes = "根据角色id启用或禁用角色。")
    @PutMapping("/{id}/status")
    public Result enable(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
                         @RequestBody @ApiParam(value = "启用(1)，禁用(0)", required = true) Map<String, Integer> status) {
        Integer s = status.get("status");
        boolean result = roleService.enableRole(id, s);
        return result ? ResultGenerator.success() : ResultGenerator.failure();
    }

    /**
     * 查询该角色所包含的资源。
     *
     * @param id 角色主键id
     * @return 角色所包含的资源
     */
    @ApiOperation(value = "查询角色的所有资源")
    @GetMapping("/{id}/resources")
    public Result<Map> list(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id) {
        List<Resource> allResourceList = resourceService.loadFullResource(3);
        List<Resource> roleResourceList = roleService.selectResourceById(id);
        List<TreeNode> treeNodeList = TreeUtil.lr2Tree(allResourceList);

        Map<String, List> map = new HashMap<>(2);
        map.put("resTree", treeNodeList);
        map.put("resSelected", roleResourceList);
        return ResultGenerator.success(map);
    }

    /**
     * 赋予某角色某资源。
     *
     * @param id  角色id
     * @param ids 资源id数组
     * @return 成功与否
     */
    @ApiOperation(value = "赋予角色一些资源。")
    @PostMapping("/{id}/resources")
    public Result giveRoleResource(@PathVariable("id") @ApiParam(value = "角色主键id", required = true) Long id,
                                   @RequestBody @ApiParam(value = "资源主键数组ids", required = true) Map<String, Long[]> ids) {
        Long[] idList = ids.get("ids");
        boolean result = roleService.giveRoleResource(id, idList);

        return result ? ResultGenerator.success() : ResultGenerator.failure();
    }

}
