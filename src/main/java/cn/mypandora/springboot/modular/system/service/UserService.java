package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.model.po.User;

/**
 * UserService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface UserService {

    /**
     * 根据分页参数查询用户。
     *
     * @param pageNum  当前页码
     * @param pageSize 当前页数
     * @param user     用户条件
     * @return 用户列表
     */
    PageInfo<User> pageUser(int pageNum, int pageSize, User user);

    /**
     * 根据用户Id或者名称查询用户。
     *
     * @param id       用户Id
     * @param username 用户名称
     * @return 用户信息
     */
    User getUserByIdOrName(Long id, String username);

    /**
     * 根据用户Id查询用户。
     *
     * @param id 用户Id
     * @return 用户信息
     */
    User getUserById(Long id);

    /**
     * 添加用户。
     *
     * @param user 用户
     */
    void addUser(User user);

    /**
     * 删除用户。
     *
     * @param id 用户id
     */
    void deleteUser(Long id);

    /**
     * 批量删除用户。
     *
     * @param ids '1,2,3,4'
     */
    void deleteBatchUser(String ids);

    /**
     * 更新用户。
     *
     * @param user 用户
     */
    void updateUser(User user);

    /**
     * 启用禁用用户。 1:开启; 0:禁用。
     *
     * @param id     用户id
     * @param status 启用(1),禁用(0)
     * @return 是否成功
     */
    boolean enableUser(Long id, Integer status);

    /**
     * 赋予用户某角色。
     *
     * @param userId     用户Id
     * @param roleListId 用色Id集合
     * @return 成功or失败
     */
    boolean grantUserRole(Long userId, Long[] roleListId);

}
