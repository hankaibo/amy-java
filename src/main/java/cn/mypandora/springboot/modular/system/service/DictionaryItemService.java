package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.modular.system.model.po.DictionaryItem;

/**
 * DictionaryItemService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface DictionaryItemService {

    /**
     * 根据字典的编码查询其下的字典项数据。
     * 
     * @param pageNum
     *            当前页码
     * @param pageSize
     *            当前页数
     * @param dictionaryItem
     *            字典项
     * @param code
     *            字典编码
     * @param sort
     *            排序
     * @return 字典项列表
     */
    PageInfo<DictionaryItem> pageDictionaryItem(int pageNum, int pageSize, DictionaryItem dictionaryItem, String code,
        String sort);

    /**
     * 添加字典项。
     *
     * @param dictionaryItem
     *            字典项
     */
    void addDictionaryItem(DictionaryItem dictionaryItem);

    /**
     * 根据字典项Id查询字典项。
     *
     * @param id
     *            字典项id
     * @return 字典项信息
     */
    DictionaryItem getDictionaryItemById(Long id);

    /**
     * 更新字典项。
     *
     * @param dictionaryItem
     *            字典项
     */
    void updateDictionaryItem(DictionaryItem dictionaryItem);

    /**
     * 启用禁用字典项。
     *
     * @param id
     *            字典项id
     * @param status
     *            状态
     */
    void enableDictionaryItem(Long id, StatusEnum status);

    /**
     * 删除字典项。
     *
     * @param id
     *            字典项id
     */
    void deleteDictionaryItem(Long id);

    /**
     * 批量删除字典项。
     *
     * @param ids
     *            [1,2,3,4]
     */
    void deleteBatchDictionaryItem(Long[] ids);

}
