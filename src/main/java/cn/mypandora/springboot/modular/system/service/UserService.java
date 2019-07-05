package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.modular.system.model.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

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
    PageInfo<User> selectUserList(int pageNum, int pageSize, User user);

    /**
     * 根据用户Id或者名称查询用户。
     *
     * @param id       用户Id
     * @param username 用户名称
     * @return 用户信息
     */
    User selectUserByIdOrName(Long id, String username);

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
     * @param id 用户id
     * @return 是否成功
     */
    boolean enableUser(Long id);
}
