package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.DepartmentUser;

/**
 * DepartmentUserMapper
 *
 * @author hankaibo
 * @date 2019/9/28
 */
public interface DepartmentUserMapper extends MyBaseMapper<DepartmentUser> {

    /**
     * 查询某部门的用户总数。
     *
     * @param departmentId
     *            部门Id
     * @return 用户数量
     */
    int countUserByDepartmentId(Long departmentId);

}
