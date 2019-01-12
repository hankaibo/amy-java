package cn.mypandora.springbootdemo.common.service;

import cn.mypandora.springbootdemo.common.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {

    /**
     * 根据分页参数查询用户。
     *
     * @param pageNum  当前页码
     * @param pageSize 当前页数
     * @return 用户列表
     */
    PageInfo<User> listAll(int pageNum, int pageSize);

    /**
     * 根据用户Id或者名称查询用户。
     *
     * @param userId    用户Id
     * @param username  用户名称
     * @return  用户信息
     */
    User queryByIdOrName(Long userId,String username);

    /**
     * 根据用户ID查询用户信息。
     *
     * @param userId 用户Id
     * @return 用户信息
     */
    User queryUserById(Long userId);

    /**
     * 根据用户Id集合查询用户信息。
     *
     * @param userIdList 用户Id集合
     * @return 用户信息
     */
    List<User> queryUserByIds(List<Long> userIdList);

    /**
     * 添加用户。
     *
     * @param user 用户
     */
    void addUser(User user);

    /**
     * 删除用户。
     *
     * @param userId 用户id
     */
    void deleteUser(Long userId);

    /**
     * 更新用户。
     *
     * @param user 用户
     */
    void updateUser(User user);
}
