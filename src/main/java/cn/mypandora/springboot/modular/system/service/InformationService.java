package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.model.po.Information;

/**
 * InformationService
 *
 * @author hankaibo
 * @date 2019/10/29
 */
public interface InformationService {

    /**
     * 根据分页参数查询信息。
     *
     * @param pageNum
     *            当前页码
     * @param pageSize
     *            当前页数
     * @param information
     *            信息
     * @return 信息列表
     */
    PageInfo<Information> pageInformation(int pageNum, int pageSize, Information information);

    /**
     * 新建信息。
     *
     * @param information
     *            信息
     */
    void addInformation(Information information);

    /**
     * 根据信息 id 查询。
     *
     * @param id
     *            信息id
     * @return 信息
     */
    Information getInformationById(Long id);

    /**
     * 更新信息。
     *
     * @param information
     *            信息
     */
    void updateInformation(Information information);

    /**
     * 启用禁用信息。 1:开启; 0:禁用
     *
     * @param id
     *            信息id
     * @param status
     *            启用(1),禁用(0)
     */
    void enableInformation(Long id, Integer status);

    /**
     * 发布信息。
     *
     * @param id
     *            信息id
     */
    void publishInformation(Long id);

    /**
     * 批量发布信息。
     *
     * @param ids
     *            '1,2,3,4'
     */
    void publishBatchInformation(String ids);

    /**
     * 删除信息。
     *
     * @param id
     *            信息id
     */
    void deleteInformation(Long id);

    /**
     * 批量删除信息。
     *
     * @param ids
     *            '1,2,3,4'
     */
    void deleteBatchInformation(String ids);
}
