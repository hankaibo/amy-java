package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.modular.system.model.Dictionary;
import cn.mypandora.springboot.modular.system.service.DictionaryService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * DictionaryController
 * <p>
 * 因使用application/json格式传输数据，故使用@RequestParam @PathVariable @RequestBody等注解收集参数。
 * 批量删除收集前台数组参数时(复杂参数)，只能使用Map或者对应的POJO接收，为了省写代码，使用Map方式了。
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Api(tags = "字典管理", description = "字典相关接口")
@RestController
@RequestMapping("/api/v1/dicts")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    /**
     * 查询分页字典数据。
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 分页数据
     */
    @ApiOperation(value = "字典列表", notes = "查询字典列表")
    @GetMapping
    public Result<PageInfo> listAll(@RequestParam(value = "pageNum", defaultValue = "1") @ApiParam(value = "页码", required = true) int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "条数", required = true) int pageSize,
                                    Dictionary dictionary) {
        return ResultGenerator.success(dictionaryService.selectDictionaryList(pageNum, pageSize, dictionary));
    }

    /**
     * 查询字典某项的详细数据。
     *
     * @param id 字典主键id
     * @return 新建结果
     */
    @ApiOperation(value = "字典详情")
    @GetMapping("/{id}")
    public Result<Dictionary> listById(@PathVariable("id") @ApiParam(value = "字典主键", required = true) Long id) {
        return ResultGenerator.success(dictionaryService.selectDictionary(id));
    }

    /**
     * 新建字典。
     *
     * @param dictionary 字典数据
     * @return 新建结果
     */
    @ApiOperation(value = "新建字典")
    @PostMapping
    public Result insert(@RequestBody @ApiParam(name = "dictionary", value = "字典数据", required = true) Dictionary dictionary) {
        dictionaryService.addDictionary(dictionary);
        return ResultGenerator.success();
    }

    /**
     * 删除字典。
     *
     * @param dictId 字典主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除字典", notes = "根据字典Id删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") @ApiParam(value = "字典主键id", required = true) Long dictId) {
        dictionaryService.deleteDictionary(dictId);
        return ResultGenerator.success();
    }

    /**
     * 批量删除字典。
     *
     * @param ids 字典id数组
     * @return 删除结果
     */
    @ApiOperation(value = "删除字典(批量)", notes = "根据字典Id批量删除")
    @DeleteMapping
    public Result remove(@RequestBody @ApiParam(value = "字典主键数组ids", required = true) Map<String, Long[]> ids) {
        dictionaryService.deleteBatchDictionary(StringUtils.join(ids.get("ids"), ","));
        return ResultGenerator.success();
    }

    /**
     * 更新字典。
     *
     * @param dictionary 字典数据
     * @return 更新结果
     */
    @ApiOperation(value = "更新字典")
    @PutMapping
    public Result update(@RequestBody @ApiParam(value = "字典数据", required = true) Dictionary dictionary) {
        dictionaryService.updateDictionary(dictionary);
        return ResultGenerator.success();
    }

}
