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
import cn.mypandora.springboot.modular.system.mapper.DictionaryItemMapper;
import cn.mypandora.springboot.modular.system.model.po.DictionaryItem;
import cn.mypandora.springboot.modular.system.service.DictionaryItemService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hankaibo
 * @date 9/12/2021
 */
@Slf4j
@Service
public class DictionaryItemServiceImpl implements DictionaryItemService {

    private final DictionaryItemMapper dictionaryItemMapper;

    @Autowired
    public DictionaryItemServiceImpl(DictionaryItemMapper dictionaryItemMapper) {
        this.dictionaryItemMapper = dictionaryItemMapper;
    }

    @Override
    public PageInfo<DictionaryItem> pageDictionaryItem(int pageNum, int pageSize, DictionaryItem dictionaryItem,
        String code, String sort) {
        PageHelper.startPage(pageNum, pageSize);
        List<DictionaryItem> dictionaryItemList = dictionaryItemMapper.pageDictionaryItem(dictionaryItem, code, sort);
        return new PageInfo<>(dictionaryItemList);
    }

    @Override
    public void addDictionaryItem(DictionaryItem dictionaryItem) {
        LocalDateTime now = LocalDateTime.now();
        dictionaryItem.setCreateTime(now);

        dictionaryItemMapper.insert(dictionaryItem);
    }

    @Override
    public DictionaryItem getDictionaryItemById(Long id) {
        DictionaryItem info = dictionaryItemMapper.selectByPrimaryKey(id);
        if (info == null) {
            throw new EntityNotFoundException(DictionaryItem.class, "字典项不存在。");
        }

        return info;
    }

    @Override
    public void updateDictionaryItem(DictionaryItem dictionaryItem) {
        LocalDateTime now = LocalDateTime.now();
        dictionaryItem.setUpdateTime(now);
        dictionaryItemMapper.updateByPrimaryKeySelective(dictionaryItem);
    }

    @Override
    public void enableDictionaryItem(Long id, StatusEnum status) {
        LocalDateTime now = LocalDateTime.now();
        DictionaryItem dictionaryItem = new DictionaryItem();
        dictionaryItem.setId(id);
        dictionaryItem.setStatus(status);
        dictionaryItem.setUpdateTime(now);

        dictionaryItemMapper.updateByPrimaryKeySelective(dictionaryItem);
    }

    @Override
    public void deleteDictionaryItem(Long id) {
        dictionaryItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatchDictionaryItem(Long[] ids) {
        dictionaryItemMapper.deleteByIds(StringUtils.join(ids, ","));
    }
}
