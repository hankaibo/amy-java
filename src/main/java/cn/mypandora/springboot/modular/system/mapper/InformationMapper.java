package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.Information;

/**
 * InformationMapper
 *
 * @author hankaibo
 * @date 2019/10/29
 */
public interface InformationMapper extends MyBaseMapper<Information> {

    /**
     * 批量更新发布状态。
     *
     * @param idList
     *            id集合
     */
    void updateBatchPublish(long[] idList);
}
