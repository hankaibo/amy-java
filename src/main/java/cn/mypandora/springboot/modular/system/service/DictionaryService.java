package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.model.po.Dictionary;

/**
 * DictionaryService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface DictionaryService {

    /**
     * 带分页、带条件查询字典数据。
     *
     * @param pageNum
     *            当前页码
     * @param pageSize
     *            当前页数
     * @param dictionary
     *            查询条件
     * @return 字典数据
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
     * 根据主键查询字典详情。
     *
     * @param id
     *            主键id
     * @return 字典数据
     */
    Dictionary getDictionary(Long id);

    /**
     * 更新字典。
     *
     * @param dictionary
     *            修改后的字典实体
     */
    void updateDictionary(Dictionary dictionary);

    /**
     * 启用禁用字典。 1:开启; 0:禁用。
     *
     * @param id
     *            字典id
     * @param status
     *            启用(1),禁用(0)
     */
    void enableDictionary(Long id, Integer status);

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
     *            [1,2]
     */
    void deleteBatchDictionary(long[] ids);
}
