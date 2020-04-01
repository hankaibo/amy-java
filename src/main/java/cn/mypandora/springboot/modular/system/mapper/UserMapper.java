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
     * @return 带分页用户数据集
     */
    List<User> pageUser(User user);

    /**
     * 根据用户id查询用户信息，带部门id。
     *
     * @param id
     *            用户Id
     * @param username
     *            用户姓名
     * @return 带部门的用户信息
     */
    User getUser(@Param("id") Long id, @Param("username") String username);

}
