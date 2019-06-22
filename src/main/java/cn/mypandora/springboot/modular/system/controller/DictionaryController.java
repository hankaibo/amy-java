package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import cn.mypandora.springboot.modular.system.model.Dictionary;
import cn.mypandora.springboot.modular.system.service.DictionaryService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RestController
@RequestMapping("/api/v1/dicts")
@Api(value = "Dictionary API接口", tags = "字典管理", description = "Dictionary API接口")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @ApiOperation(value = "字典列表", notes = "查询字典列表")
    @GetMapping
    public Result<PageInfo> listAll(@RequestParam(value = "page", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        Dictionary dictionary = new Dictionary();
        dictionary.setParentId(-1L);
        return ResultGenerator.success(dictionaryService.selectDictionary(pageNum, pageSize, dictionary));
    }

    @ApiOperation(value = "字典项", notes = "查询字典某项")
    @GetMapping("/{id}")
    public Result<PageInfo> listById(@RequestParam(value = "page", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                     @PathVariable("id") Long id) {
        Dictionary dictionary = new Dictionary();
        dictionary.setParentId(id);
        return ResultGenerator.success(dictionaryService.selectDictionary(pageNum, pageSize, dictionary));
    }

    @ApiOperation(value = "新建字典")
    @ApiImplicitParam(name = "dictionary", value = "字典对象", required = true, dataType = "Dictionary")
    @PostMapping
    public Result<Dictionary> insert(@RequestBody Dictionary dictionary) {
        dictionaryService.addDictionary(dictionary);
        return ResultGenerator.success();
    }

    @ApiOperation(value = "删除字典", notes = "根据字典Id删除")
    @DeleteMapping("/{id}")
    public Result<Dictionary> remove(@PathVariable("id") Long dictId) {
        dictionaryService.deleteDictionary(dictId);
        return ResultGenerator.success();
    }

    @ApiOperation(value = "删除字典(批量)", notes = "根据字典Id批量删除")
    @DeleteMapping
    public Result<Dictionary> remove(@RequestBody Map<String, Long[]> ids) {
        dictionaryService.deleteBatchDictionary(StringUtils.join(ids.get("ids"), ","));
        return ResultGenerator.success();
    }

    @ApiOperation(value = "更新字典")
    @PutMapping
    public Result<Dictionary> update(@RequestBody Dictionary dictionary) {
        dictionaryService.updateDictionary(dictionary);
        return ResultGenerator.success();
    }

}
