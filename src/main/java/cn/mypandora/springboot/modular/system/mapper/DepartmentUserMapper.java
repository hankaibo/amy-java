package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.DepartmentUser;
import org.apache.ibatis.annotations.Param;

/**
 * DepartmentUserMapper
 *
 * @author hankaibo
 * @date 2019/9/28
 */
public interface DepartmentUserMapper extends MyBaseMapper<DepartmentUser> {

    /**
     * 根据用户id更新其所在的部门
     *
     * @param userId
     * @param departmentId
     */
    void updateByUserId(@Param("userId") Long userId, @Param("departmentId") Long departmentId);

    /**
     * 根据用户id删除其所在的部门
     *
     * @param userId
     */
    void deleteByUserId(Long userId);

    /**
     * 根据用户id批量删除其所在的部门
     *
     * @param userListId
     */
    void deleteBatchByUserIds(@Param(value = "userListId") Long[] userListId);
}
