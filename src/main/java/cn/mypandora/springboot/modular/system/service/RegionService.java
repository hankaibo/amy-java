package cn.mypandora.springboot.modular.system.service;

import java.util.List;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.modular.system.model.po.Region;

/**
 * RegionService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RegionService {

    /**
     * 获得区域树（一次性全部加载，适合数据量少的情况）。
     *
     * @param status
     *            状态，默认为空查询所有
     * @return 区域列表
     */
    List<Region> listRegion(StatusEnum status);

    /**
     * 获得直接子区域。
     *
     * @param id
     *            当前操作区域id
     * @param status
     *            状态
     * @return 区域列表
     */
    List<Region> listChildrenRegion(Long id, StatusEnum status);

    /**
     * 添加区域。
     *
     * @param region
     *            区域
     */
    void addRegion(Region region);

    /**
     * 根据主键查询区域详情。
     *
     * @param id
     *            主键id
     * @return 区域数据
     */
    Region getRegionById(Long id);

    /**
     * 根据code码获取对应的区域数据
     * 
     * @param codeList
     *            code码集合
     * @return 区域集合
     */
    List<Region> listRegionByCode(List<String> codeList);

    /**
     * 更新区域。
     *
     * @param region
     *            修改后的区域实体
     */
    void updateRegion(Region region);

    /**
     * 启用禁用区域。
     *
     * @param id
     *            区域id
     * @param status
     *            状态
     */
    void enableRegion(Long id, StatusEnum status);

    /**
     * 删除区域。
     *
     * @param id
     *            区域id
     */
    void deleteRegion(Long id);

    /**
     * 平移区域。
     *
     * @param sourceId
     *            源区域id
     * @param targetId
     *            目标区域id
     */
    void moveRegion(Long sourceId, Long targetId);
}
