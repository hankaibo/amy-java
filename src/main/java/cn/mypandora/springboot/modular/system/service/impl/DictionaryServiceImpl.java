package cn.mypandora.springboot.modular.system.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.mypandora.springboot.config.exception.EntityNotFoundException;
import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.mapper.DictionaryMapper;
import cn.mypandora.springboot.modular.system.model.po.Dictionary;
import cn.mypandora.springboot.modular.system.service.DictionaryService;
import tk.mybatis.mapper.entity.Example;

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
    public PageInfo<Dictionary> pageDictionary(int pageNum, int pageSize, Dictionary dictionary) {
        PageHelper.startPage(pageNum, pageSize);
        // 使用通用 Mapper Example 用法，亦可用传统的 xml 配置文件。
        Example example = new Example(Dictionary.class);
        Example.Criteria criteria = example.createCriteria();
        if (dictionary.getParentId() == null) {
            criteria.andIsNull("parentId");
        } else {
            criteria.andEqualTo("parentId", dictionary.getParentId());
        }
        if (dictionary.getStatus() != null) {
            criteria.andEqualTo("status", dictionary.getStatus());
        }
        List<Dictionary> dictionaryList = dictionaryMapper.selectByExample(example);
        return new PageInfo<>(dictionaryList);
    }

    @Override
    public void addDictionary(Dictionary dictionary) {
        LocalDateTime now = LocalDateTime.now();
        dictionary.setCreateTime(now);
        dictionaryMapper.insert(dictionary);
    }

    @Override
    public Dictionary getDictionary(Long id) {
        Dictionary info = dictionaryMapper.selectByPrimaryKey(id);
        if (info == null) {
            throw new EntityNotFoundException(Dictionary.class, "字典不存在。");
        }
        return info;
    }

    @Override
    public void updateDictionary(Dictionary dictionary) {
        LocalDateTime now = LocalDateTime.now();
        dictionary.setUpdateTime(now);
        dictionaryMapper.updateByPrimaryKeySelective(dictionary);
    }

    @Override
    public void enableDictionary(Long id, Integer status) {
        LocalDateTime now = LocalDateTime.now();
        Dictionary dictionary = new Dictionary();
        dictionary.setId(id);
        dictionary.setStatus(status);
        dictionary.setUpdateTime(now);
        dictionaryMapper.updateByPrimaryKeySelective(dictionary);
    }

    @Override
    public void deleteDictionary(Long id) {
        dictionaryMapper.deleteDictionaryById(id);
    }

    @Override
    public void deleteBatchDictionary(long[] ids) {
        dictionaryMapper.deleteDictionaryByListId(ids);
    }
}
