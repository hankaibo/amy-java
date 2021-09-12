package cn.mypandora.springboot.modular.system.controller;

import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.BatchGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import cn.mypandora.springboot.modular.system.model.po.DictionaryItem;
import cn.mypandora.springboot.modular.system.service.DictionaryItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * DictionaryItemController
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Validated
@Api(tags = "字典项管理")
@RestController
@RequestMapping("/api/v1/dictionaryItems")
public class DictionaryItemController {

    private final DictionaryItemService dictionaryItemService;

    @Autowired
    public DictionaryItemController(DictionaryItemService dictionaryItemService) {
        this.dictionaryItemService = dictionaryItemService;
    }

    /**
     * 分页查询字典项数据。
     *
     * @param pageNum
     *            页码
     * @param pageSize
     *            每页条数
     * @param dictionaryId
     *            字典id
     * @param name
     *            字典项名称
     * @param status
     *            字典项状态
     * @param code
     *            字典编码
     * @param sort
     *            排序
     * @return 分页数据
     */
    @ApiOperation(value = "字典项列表")
    @GetMapping
    public PageInfo<DictionaryItem> pageDictionaryItem(
        @Positive @RequestParam(value = "current", defaultValue = "1") @ApiParam(value = "页码",
            required = true) int pageNum,
        @Positive @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "每页条数",
            required = true) int pageSize,
        @RequestParam(value = "dictionaryId", required = false) @ApiParam(value = "字典id") Long dictionaryId,
        @RequestParam(value = "name", required = false) @ApiParam(value = "字典项名称") String name,
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态") StatusEnum status,
        @RequestParam(value = "code", required = false) @ApiParam(value = "字典编码") String code,
        @RequestParam(value = "sort", required = false, defaultValue = "asc") @ApiParam(value = "排序") String sort) {
        DictionaryItem dictionaryItem = new DictionaryItem();
        dictionaryItem.setDictionaryId(dictionaryId);
        dictionaryItem.setName(name);
        dictionaryItem.setStatus(status);
        return dictionaryItemService.pageDictionaryItem(pageNum, pageSize, dictionaryItem, code, sort);
    }

    /**
     * 新建字典项。
     *
     * @param dictionaryItem
     *            字典项数据
     */
    @ApiOperation(value = "字典项新建")
    @PostMapping
    public void addDictionaryItem(@Validated({AddGroup.class}) @RequestBody @ApiParam(value = "字典项数据",
        required = true) DictionaryItem dictionaryItem) {
        dictionaryItemService.addDictionaryItem(dictionaryItem);
    }

    /**
     * 查询字典项详细数据。
     *
     * @param id
     *            字典项主键id
     * @return 字典项信息
     */
    @ApiOperation(value = "字典项详情")
    @GetMapping("/{id}")
    public DictionaryItem
        getDictionaryItemById(@Positive @PathVariable("id") @ApiParam(value = "字典项主键id", required = true) Long id) {
        return dictionaryItemService.getDictionaryItemById(id);
    }

    /**
     * 更新字典项。
     *
     * @param dictionaryItem
     *            字典项数据
     */
    @ApiOperation(value = "字典项更新")
    @PutMapping("/{id}")
    public void updateDictionaryItem(@Validated({UpdateGroup.class}) @RequestBody @ApiParam(value = "字典项数据",
        required = true) DictionaryItem dictionaryItem) {
        dictionaryItemService.updateDictionaryItem(dictionaryItem);
    }

    /**
     * 启用禁用字典项。
     *
     * @param id
     *            字典项主键id
     * @param status
     *            状态
     */
    @ApiOperation(value = "字典项状态启用禁用")
    @PatchMapping("/{id}/status")
    public void enableDictionaryItem(
        @Positive @PathVariable("id") @ApiParam(value = "字典项主键id", required = true) Long id,
        @RequestParam @ApiParam(value = "状态", required = true) StatusEnum status) {
        dictionaryItemService.enableDictionaryItem(id, status);
    }

    /**
     * 删除字典项。
     *
     * @param id
     *            字典项主键id
     */
    @ApiOperation(value = "字典项删除")
    @DeleteMapping("/{id}")
    public void
        deleteDictionaryItem(@Positive @PathVariable("id") @ApiParam(value = "字典项主键id", required = true) Long id) {
        dictionaryItemService.deleteDictionaryItem(id);
    }

    /**
     * 批量删除字典项。
     *
     * @param ids
     *            字典项主键数据
     */
    @ApiOperation(value = "字典项删除(批量)")
    @DeleteMapping
    public void deleteBatchDictionaryItem(
        @Validated({BatchGroup.class}) @RequestBody @ApiParam(value = "字典项id数组", required = true) Long[] ids) {
        dictionaryItemService.deleteBatchDictionaryItem(ids);
    }

}
