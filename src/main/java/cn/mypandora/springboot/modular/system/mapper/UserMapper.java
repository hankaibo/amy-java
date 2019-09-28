package cn.mypandora.springboot.modular.system.mapper;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.User;

import java.util.List;

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
     * @return
     */
    List<User> pageUser(User user);

    /**
     * 根据用户id查询用户信息，带部门id。
     *
     * @param id
     * @return
     */
    User getUser(Long id);
}
