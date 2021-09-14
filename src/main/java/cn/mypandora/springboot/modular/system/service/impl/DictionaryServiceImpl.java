package cn.mypandora.springboot.modular.system.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.mypandora.springboot.config.exception.EntityNotFoundException;
import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.modular.system.mapper.DictionaryMapper;
import cn.mypandora.springboot.modular.system.model.po.Dictionary;
import cn.mypandora.springboot.modular.system.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hankaibo
 * @date 9/12/2021
 */
@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryMapper dictionaryMapper;

    @Autowired
    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper) {
        this.dictionaryMapper = dictionaryMapper;
    }

    @Override
    public PageInfo<Dictionary> pageDictionary(int pageNum, int pageSize, Dictionary dictionary) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dictionary> dictionaryList = dictionaryMapper.select(dictionary);
        return new PageInfo<>(dictionaryList);
    }

    @Override
    public void addDictionary(Dictionary dictionary) {
        LocalDateTime now = LocalDateTime.now();
        dictionary.setCreateTime(now);

        dictionaryMapper.insert(dictionary);
    }

    @Override
    public Dictionary getDictionaryById(Long id) {
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
    public void enableDictionary(Long id, StatusEnum status) {
        LocalDateTime now = LocalDateTime.now();
        Dictionary dictionary = new Dictionary();
        dictionary.setId(id);
        dictionary.setStatus(status);
        dictionary.setUpdateTime(now);

        dictionaryMapper.updateByPrimaryKeySelective(dictionary);
    }

    @Override
    public void deleteDictionary(Long id) {
        dictionaryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatchDictionary(Long[] ids) {
        dictionaryMapper.deleteByIds(StringUtils.join(ids, ","));
    }
}
