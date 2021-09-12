package cn.mypandora.springboot.modular.system.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.util.TreeUtil;
import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import cn.mypandora.springboot.modular.system.model.po.Region;
import cn.mypandora.springboot.modular.system.model.vo.RegionTree;
import cn.mypandora.springboot.modular.system.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * RegionController
 * <p>
 * 因使用application/json格式传输数据，故使用@RequestParam @PathVariable @RequestBody等注解收集参数。
 * 批量删除收集前台数组参数时(复杂参数)，只能使用Map或者对应的POJO接收，为了省写代码，使用Map方式了。
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Validated
@Api(tags = "地区管理")
@RestController
@RequestMapping("/api/v1/regions")
public class RegionController {

    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * 获取地区树。
     *
     * @param status
     *            状态
     * @return 地区树
     */
    @ApiOperation(value = "获取地区树")
    @GetMapping
    public List<RegionTree> listRegionTree(
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态") StatusEnum status) {
        List<Region> regionList = regionService.listRegion(status);
        return TreeUtil.region2Tree(regionList);
    }

    /**
     * 获取子地区列表。
     *
     * @param id
     *            主键id
     * @param status
     *            状态
     * @return 某个地区的直接子地区
     */
    @ApiOperation(value = "获取子地区列表")
    @GetMapping("/{id}/children")
    public List<Region> listRegionChildren(
        @Positive @PathVariable("id") @ApiParam(value = "主键id", required = true) Long id,
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态") StatusEnum status) {
        return regionService.listChildrenRegion(id, status);
    }

    /**
     * 新建地区。
     *
     * @param region
     *            地区数据
     */
    @ApiOperation(value = "新建地区")
    @PostMapping
    public void addRegion(
        @Validated({AddGroup.class}) @RequestBody @ApiParam(value = "地区数据", required = true) Region region) {
        regionService.addRegion(region);
    }

    /**
     * 获取地区详情。
     *
     * @param id
     *            地区主键id
     * @return 新建结果
     */
    @ApiOperation(value = "获取地区详情")
    @GetMapping("/{id}")
    public Region
        getRegionById(@Positive @PathVariable("id") @ApiParam(value = "地区主键", required = true) Long id) {
        Region region = regionService.getRegionById(id);
        region.setRgt(null);
        region.setLft(null);
        region.setLevel(null);
        return region;
    }

    /**
     * 更新地区。
     *
     * @param region
     *            地区数据
     */
    @ApiOperation(value = "更新地区")
    @PutMapping("/{id}")
    public void updateRegion(
        @Validated({UpdateGroup.class}) @RequestBody @ApiParam(value = "地区数据", required = true) Region region) {
        regionService.updateRegion(region);
    }

    /**
     * 启用|禁用地区。
     *
     * @param id
     *            地区主键id
     * @param status
     *            状态
     */
    @ApiOperation(value = "启用禁用地区")
    @PatchMapping("/{id}/status")
    public void enableRegion(@Positive @PathVariable("id") @ApiParam(value = "地区主键id", required = true) Long id,
        @RequestParam @ApiParam(value = "地区状态", required = true) StatusEnum status) {
        regionService.enableRegion(id, status);
    }

    /**
     * 删除地区。
     *
     * @param id
     *            地区主键id
     */
    @ApiOperation(value = "删除地区")
    @DeleteMapping("/{id}")
    public void deleteRegion(@Positive @PathVariable("id") @ApiParam(value = "地区主键id", required = true) Long id) {
        regionService.deleteRegion(id);
    }

    /**
     * 同层级地区的平移。
     *
     * @param sourceId
     *            源id
     * @param targetId
     *            目标id
     */
    @ApiOperation(value = "移动地区")
    @PutMapping
    public void moveRegion(@Positive @RequestParam("from") @ApiParam(value = "源id", required = true) Long sourceId,
        @Positive @RequestParam("to") @ApiParam(value = "目标id", required = true) Long targetId) {
        regionService.moveRegion(sourceId, targetId);
    }

    @ApiOperation(value = "根据code码获取相应地区数据")
    @GetMapping("/codes")
    public List<RegionTree> listRegionByCode(
        @NotNull @RequestParam("codeList") @ApiParam(value = "code码集合", required = true) String[] codeList) {
        List<Region> regionList = regionService.listRegionByCode(Arrays.asList(codeList));
        return TreeUtil.region2Tree(regionList);
    }

}
