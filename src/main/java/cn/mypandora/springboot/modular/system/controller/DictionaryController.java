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
import cn.mypandora.springboot.modular.system.model.po.Dictionary;
import cn.mypandora.springboot.modular.system.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * DictionaryController
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Validated
@Api(tags = "字典管理")
@RestController
@RequestMapping("/api/v1/dictionaries")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    /**
     * 分页查询字典数据。
     *
     * @param pageNum
     *            页码
     * @param pageSize
     *            每页条数
     * @param name
     *            字典名称
     * @param code
     *            字典编码
     * @param status
     *            字典状态
     * @return 分页数据
     */
    @ApiOperation(value = "字典列表")
    @GetMapping
    public PageInfo<Dictionary> pageDictionary(
        @Positive @RequestParam(value = "current", defaultValue = "1") @ApiParam(value = "页码",
            required = true) int pageNum,
        @Positive @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "每页条数",
            required = true) int pageSize,
        @RequestParam(value = "name", required = false) @ApiParam(value = "字典名称") String name,
        @RequestParam(value = "code", required = false) @ApiParam(value = "字典编码") String code,
        @RequestParam(value = "status", required = false) @ApiParam(value = "状态") StatusEnum status) {
        Dictionary dictionary = new Dictionary();
        dictionary.setName(name);
        dictionary.setCode(code);
        dictionary.setStatus(status);
        return dictionaryService.pageDictionary(pageNum, pageSize, dictionary);
    }

    /**
     * 新建字典。
     *
     * @param dictionary
     *            字典数据
     */
    @ApiOperation(value = "字典新建")
    @PostMapping
    public void addDictionary(
        @Validated({AddGroup.class}) @RequestBody @ApiParam(value = "字典数据", required = true) Dictionary dictionary) {
        dictionaryService.addDictionary(dictionary);
    }

    /**
     * 查询字典详细数据。
     *
     * @param id
     *            字典主键id
     * @return 字典信息
     */
    @ApiOperation(value = "字典详情")
    @GetMapping("/{id}")
    public Dictionary
        getDictionaryById(@Positive @PathVariable("id") @ApiParam(value = "字典主键id", required = true) Long id) {
        return dictionaryService.getDictionaryById(id);
    }

    /**
     * 更新字典。
     *
     * @param dictionary
     *            字典数据
     */
    @ApiOperation(value = "字典更新")
    @PutMapping("/{id}")
    public void updateDictionary(
        @Validated({UpdateGroup.class}) @RequestBody @ApiParam(value = "字典数据", required = true) Dictionary dictionary) {
        dictionaryService.updateDictionary(dictionary);
    }

    /**
     * 启用禁用字典。
     *
     * @param id
     *            字典主键id
     * @param status
     *            状态
     */
    @ApiOperation(value = "字典状态启用禁用")
    @PatchMapping("/{id}/status")
    public void enableDictionary(@Positive @PathVariable("id") @ApiParam(value = "字典主键id", required = true) Long id,
        @RequestParam @ApiParam(value = "状态", required = true) StatusEnum status) {
        dictionaryService.enableDictionary(id, status);
    }

    /**
     * 删除字典。
     *
     * @param id
     *            字典主键id
     */
    @ApiOperation(value = "字典删除")
    @DeleteMapping("/{id}")
    public void deleteDictionary(@Positive @PathVariable("id") @ApiParam(value = "字典主键id", required = true) Long id) {
        dictionaryService.deleteDictionary(id);
    }

    /**
     * 批量删除字典。
     *
     * @param ids
     *            字典主键数据
     */
    @ApiOperation(value = "字典删除(批量)")
    @DeleteMapping
    public void deleteBatchDictionary(
        @Validated({BatchGroup.class}) @RequestBody @ApiParam(value = "字典id数组", required = true) Long[] ids) {
        dictionaryService.deleteBatchDictionary(ids);
    }

}
