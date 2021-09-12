package cn.mypandora.springboot.modular.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.modular.system.model.po.Region;

/**
 * RegionMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RegionMapper extends MyBaseMapper<Region> {

    /**
     * 获取整棵树（一次性全部加载，适合数据量少的情况）。
     *
     * @param status
     *            状态
     * @return 整棵树
     */
    List<Region> listAll(StatusEnum status);

    /**
     * 获得本地区的所有祖先地区。
     *
     * @param id
     *            当前操作地区id
     * @param status
     *            状态
     * @return 本地区的所有祖先地区
     */
    List<Region> listAncestries(@Param("id") Long id, @Param("status") StatusEnum status);

    /**
     * 获得本地区的所有后代地区。
     *
     * @param id
     *            当前操作地区id
     * @param status
     *            状态
     * @return 本地区的所有后代地区
     */
    List<Region> listDescendants(@Param("id") Long id, @Param("status") StatusEnum status);

    /**
     * 获得本地区的孩子地区。
     *
     * @param id
     *            当前操作地区id
     * @param status
     *            状态
     * @return 本地区的孩子地区
     */
    List<Region> listChildren(@Param("id") Long id, @Param("status") StatusEnum status);

    /**
     * 将树形结构中所有大于当前地区右值的左地区值+N
     * <p>
     * 大于当前地区左值，方便插入到父地区的头；大于当前地区右值，方便插入到父地区末尾
     * </p>
     *
     * @param id
     *            当前地区id
     * @param amount
     *            要加的数值
     * @param range
     *            被修改范围的最大左值
     */
    void lftAdd(@Param("id") Long id, @Param("amount") Integer amount, @Param("range") Integer range);

    /**
     * 将树形结构中所有大于当前地区右值的右地区值+N *
     * <p>
     * 大于当前地区左值，方便插入到父地区的头；大于当前地区右值，方便插入到父地区末尾 *
     * </p>
     *
     * @param id
     *            当前地区id
     * @param amount
     *            要加的数值
     * @param range
     *            被修改范围的最大右值
     */
    void rgtAdd(@Param("id") Long id, @Param("amount") Integer amount, @Param("range") Integer range);

    /**
     * 当前地区集合都加上N。
     *
     * @param idList
     *            地区id集合
     * @param amount
     *            地区及子孙都要加上的数值
     * @param level
     *            原层级加N
     */
    void selfAndDescendant(@Param("idList") List<Long> idList, @Param("amount") Integer amount,
        @Param("level") Integer level);

    /**
     * 启用禁用地区状态。
     *
     * @param idList
     *            地区id集合
     * @param status
     *            状态
     */
    void enableDescendants(@Param("idList") List<Long> idList, @Param("status") StatusEnum status);

    /**
     * 锁定数据，防止被修改左右值。
     *
     * @param idList
     *            地区id集合
     * @param isUpdate
     *            是否可更新状态(1:可更新，0:不可更新)
     */
    void locking(@Param("idList") List<Long> idList, @Param("isUpdate") Integer isUpdate);

}
