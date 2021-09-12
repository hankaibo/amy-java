package cn.mypandora.springboot.modular.system.service;

import org.springframework.web.multipart.MultipartFile;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.modular.system.model.po.Dictionary;
import cn.mypandora.springboot.modular.system.model.vo.Token;

/**
 * DictionaryService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface DictionaryService {

    /**
     * 根据分页参数查询字典。
     *
     * @param pageNum
     *            当前页码
     * @param pageSize
     *            当前页数
     * @param dictionary
     *            字典条件
     * @return 字典列表
     */
    PageInfo<Dictionary> pageDictionary(int pageNum, int pageSize, Dictionary dictionary);

    /**
     * 添加字典。
     *
     * @param dictionary
     *            字典
     */
    void addDictionary(Dictionary dictionary);

    /**
     * 根据字典Id查询字典。
     *
     * @param id
     *            字典id
     * @return 字典信息
     */
    Dictionary getDictionaryById(Long id);

    /**
     * 更新字典。
     *
     * @param dictionary
     *            字典
     */
    void updateDictionary(Dictionary dictionary);

    /**
     * 启用禁用字典。
     *
     * @param id
     *            字典id
     * @param status
     *            状态
     */
    void enableDictionary(Long id, StatusEnum status);

    /**
     * 删除字典。
     *
     * @param id
     *            字典id
     */
    void deleteDictionary(Long id);

    /**
     * 批量删除字典。
     *
     * @param ids
     *            [1,2,3,4]
     */
    void deleteBatchDictionary(Long[] ids);

}
