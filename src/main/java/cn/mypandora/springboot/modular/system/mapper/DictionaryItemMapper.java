package cn.mypandora.springboot.modular.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.DictionaryItem;

/**
 * DictionaryItemMapper
 * 
 * @author hankaibo
 */
public interface DictionaryItemMapper extends MyBaseMapper<DictionaryItem> {

    /**
     * 根据字典编码查询其下的字典项数据
     * 
     * @param dictionaryItem
     *            字典项条件
     * @param code
     *            字典编码
     * @param sort
     *            排序
     * @return 字典项列表
     */
    List<DictionaryItem> pageDictionaryItem(@Param(value = "dictionaryItem") DictionaryItem dictionaryItem,
        @Param(value = "code") String code, @Param(value = "sort") String sort);
}