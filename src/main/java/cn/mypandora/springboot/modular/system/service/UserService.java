package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.modular.system.model.po.User;
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
     * 查询某个用户的所有角色权限。
     *
     * @param userId 用户Id
     * @return 用户拥有的所有角色
     */
    String selectRoleByUserId(Long userId);

    /**
     * 查询所有的用户。
     *
     * @return 所有的用户
     */
    List<User> selectAll();

    /**
     * 查询某个角色对应的所有用户。
     *
     * @param roleId 角色Id
     * @return 拥有该角色的所有用户
     */
    List<User> selectUserByRoleId(Long roleId);

    /**
     * 授权某用户某个角色权限。
     *
     * @param userId 用户Id
     * @param roleId 角色Id
     * @return 成功与否
     */
    boolean authorityUserRole(Long userId, Long roleId);

    /**
     * 删除某用户的某个角色权限。
     *
     * @param userId 用户Id
     * @param roleId 角色Id
     * @return 成功与否
     */
    boolean deleteAuthorityUserRole(Long userId, Long roleId);

    /**
     * 根据分页参数查询用户。
     *
     * @param pageNum  当前页码
     * @param pageSize 当前页数
     * @param user     用户条件
     * @return 用户列表
     */
    PageInfo<User> selectByPage(int pageNum, int pageSize, User user);

    /**
     * 根据用户Id或者名称查询用户。
     *
     * @param userId   用户Id
     * @param username 用户名称
     * @return 用户信息
     */
    User selectByIdOrName(Long userId, String username);

    /**
     * 根据用户ID查询用户信息。
     *
     * @param userId 用户Id
     * @return 用户信息
     */
    User selectById(Long userId);

    /**
     * 根据用户Id集合查询用户信息。
     *
     * @param userIdList 用户Id集合
     * @return 用户信息
     */
    List<User> selectByIds(List<Long> userIdList);

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
