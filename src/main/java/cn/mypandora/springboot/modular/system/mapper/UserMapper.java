package cn.mypandora.springboot.modular.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.User;

/**
 * UserMapper
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface UserMapper extends MyBaseMapper<User> {

    /**
     * 根据部门id等条件查的用户。
     *
     * @param user
     *            查询条件
     * @param departmentId
     *            部门主键id
     * @return 带分页用户数据集
     */
    List<User> pageUser(@Param(value = "user") User user, @Param(value = "departmentId") Long departmentId);

}
