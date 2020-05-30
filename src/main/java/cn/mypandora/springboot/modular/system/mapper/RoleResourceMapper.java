package cn.mypandora.springboot.modular.system.mapper;

import org.apache.ibatis.annotations.Param;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.RoleResource;

/**
 * RoleResourceMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RoleResourceMapper extends MyBaseMapper<RoleResource> {

    /**
     * 删除资源所有角色（批量）。
     *
     * @param resourceIdList
     *            资源Id集合
     */
    void deleteBatchResourceAllRole(@Param("resourceIdList") long[] resourceIdList);
}
