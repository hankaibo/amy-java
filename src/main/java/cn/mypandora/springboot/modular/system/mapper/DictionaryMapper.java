package cn.mypandora.springboot.modular.system.mapper;

import org.apache.ibatis.annotations.Param;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.Dictionary;

/**
 * DictionaryMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface DictionaryMapper extends MyBaseMapper<Dictionary> {

    /**
     * 根据主键id删除字典，包括删除其下的所有子项。
     *
     * @param id
     *            主键id
     */
    void deleteDictionaryById(Long id);

    /**
     * 根据主键id数组删除字典，包括删除其下的所有子项。
     *
     * @param ids
     *            id数组
     */
    void deleteDictionaryByListId(@Param("ids") long[] ids);
}
