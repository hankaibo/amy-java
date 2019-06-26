package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.Dictionary;

import java.util.List;

/**
 * DictionaryMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface DictionaryMapper extends MyBaseMapper<Dictionary> {

    /**
     * 查询所有父级的code值
     *
     * @param dictionary 查询条件
     * @return ok
     */
    List<Dictionary> selectByCode(Dictionary dictionary);
}
