package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.mapper.DictionaryMapper;
import cn.mypandora.springboot.modular.system.model.po.Dictionary;
import cn.mypandora.springboot.modular.system.service.DictionaryService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * DictionaryServiceImpl
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private DictionaryMapper dictionaryMapper;

    @Autowired
    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper) {
        this.dictionaryMapper = dictionaryMapper;
    }

    @Override
    public PageInfo<Dictionary> selectDictionaryList(int pageNum, int pageSize, Dictionary dictionary) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dictionary> dictionaryList = dictionaryMapper.select(dictionary);
        return new PageInfo<>(dictionaryList);
    }

    @Override
    public Dictionary selectDictionary(Long id) {
        Dictionary dictionary = new Dictionary();
        dictionary.setId(id);
        return dictionaryMapper.selectByPrimaryKey(dictionary);
    }

    @Override
    public void addDictionary(Dictionary dictionary) {
        Date now = new Date(System.currentTimeMillis());
        dictionary.setCreateTime(now);
        dictionaryMapper.insert(dictionary);
    }

    @Override
    public void deleteDictionary(Long id) {
        Dictionary dictionary = new Dictionary();
        dictionary.setId(id);
        dictionaryMapper.delete(dictionary);
    }

    @Override
    public void deleteBatchDictionary(String ids) {
        dictionaryMapper.deleteByIds(ids);
    }

    @Override
    public void updateDictionary(Dictionary dictionary) {
        Date now = new Date(System.currentTimeMillis());
        dictionary.setUpdateTime(now);
        dictionaryMapper.updateByPrimaryKeySelective(dictionary);
    }

    @Override
    public boolean enableDictionary(Long id, Integer status) {
        Dictionary dictionary = new Dictionary();
        dictionary.setId(id);
        dictionary.setStatus(status);
        return dictionaryMapper.updateByPrimaryKeySelective(dictionary) > 0;
    }
}
