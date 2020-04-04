package cn.mypandora.springboot.modular.system.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.mapper.InformationMapper;
import cn.mypandora.springboot.modular.system.model.po.Information;
import cn.mypandora.springboot.modular.system.service.InformationService;
import tk.mybatis.mapper.entity.Example;

/**
 * InformationServiceImpl
 *
 * @author hankaibo
 * @date 2019/10/29
 */
@Service
public class InformationServiceImpl implements InformationService {

    private InformationMapper informationMapper;

    @Autowired
    public InformationServiceImpl(InformationMapper informationMapper) {
        this.informationMapper = informationMapper;
    }

    @Override
    public PageInfo<Information> pageInformation(int pageNum, int pageSize, Information information) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Information.class);
        Example.Criteria criteria = example.createCriteria();
        final String type = "type";
        if (information.getType() != null) {
            criteria.andEqualTo(type, information.getType());
        }
        List<Information> informationList = informationMapper.selectByExample(example);
        return new PageInfo<>(informationList);
    }

    @Override
    public void addInformation(Information information) {
        LocalDateTime now = LocalDateTime.now();
        information.setCreateTime(now);

        informationMapper.insert(information);
    }

    @Override
    public Information getInformationById(Long id) {
        Information information = new Information();
        information.setId(id);
        return informationMapper.selectOne(information);
    }

    @Override
    public void updateInformation(Information information) {
        LocalDateTime now = LocalDateTime.now();
        information.setUpdateTime(now);
        informationMapper.updateByPrimaryKeySelective(information);
    }

    @Override
    public void enableInformation(Long id, Integer status) {
        LocalDateTime now = LocalDateTime.now();
        Information information = new Information();
        information.setId(id);
        information.setStatus(status);
        information.setUpdateTime(now);
        informationMapper.updateByPrimaryKeySelective(information);
    }

    @Override
    public void publishInformation(Long id) {
        Information information = new Information();
        information.setId(id);
        information.setPublish(1);
        informationMapper.updateByPrimaryKeySelective(information);
    }

    @Override
    public void publishBatchInformation(String ids) {
        long[] idList = Arrays.stream(ids.split(",")).mapToLong(Long::valueOf).toArray();
        informationMapper.updateBatchPublish(idList);
    }

    @Override
    public void deleteInformation(Long id) {
        Information information = new Information();
        information.setId(id);
        informationMapper.delete(information);
    }

    @Override
    public void deleteBatchInformation(String ids) {
        informationMapper.deleteByIds(ids);
    }
}
