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
     *            部门id
     * @return 用户数量
     */
    int countUserByDepartmentId(Long departmentId);

    /**
     * 
     * 判断用户是否存在部门用户关系表中
     * 
     * @param userId
     *            用户id
     * @return 存在返回1，不存在返回0或者null
     */
    Integer existsUserByUserId(Long userId);

}
