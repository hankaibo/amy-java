package cn.mypandora.springboot.modular.system.service;

import org.springframework.web.multipart.MultipartFile;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.model.vo.Token;

/**
 * UserService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface UserService {
    /**
     * 通过用户名称获取token
     *
     * @param username
     *            用户名称
     * @return token
     */
    Token login(String username);

    /**
     * 登出
     *
     * @param authorization
     *            用户authorization
     */
    void logout(String authorization);

    /**
     * 根据分页参数查询用户。
     *
     * @param pageNum
     *            当前页码
     * @param pageSize
     *            当前页数
     * @param user
     *            用户条件
     * @param departmentId
     *            部门fid
     * @return 用户列表
     */
    PageInfo<User> pageUser(int pageNum, int pageSize, User user, Long departmentId);

    /**
     * 保存上传的头像图片。
     *
     * @param file
     *            头像图片
     * @return 新文件名称
     */
    String saveFile(MultipartFile file);

    /**
     * 添加用户。
     *
     * @param user
     *            用户
     */
    void addUser(User user);

    /**
     * 根据名称查询用户。(主要用于登录查询)
     *
     * @param username
     *            用户名称
     * @return 用户信息
     */
    User getUserByName(String username);

    /**
     * 根据用户Id查询用户。
     *
     * @param id
     *            用户Id
     * @return 用户信息
     */
    User getUserById(Long id);

    /**
     * 更新用户。
     *
     * @param user
     *            用户
     * @param plusDepartmentIds
     *            新添加部门id数组
     * @param minusDepartmentIds
     *            删除旧部门id数组
     */
    void updateUser(User user, Long[] plusDepartmentIds, Long[] minusDepartmentIds);

    /**
     * 启用禁用用户。 (所有部门)
     *
     * @param id
     *            用户id
     * @param status
     *            状态
     */
    void enableUser(Long id, StatusEnum status);

    /**
     * 重置用户密码。
     *
     * @param id
     *            用户id
     * @param password
     *            新密码
     */
    void resetPassword(Long id, String password);

    /**
     * 更新用户密码。
     *
     * @param id
     *            用户id
     * @param oldPassword
     *            旧密码
     * @param newPassword
     *            新密码
     */
    void updatePassword(Long id, String oldPassword, String newPassword);

    /**
     * 删除用户。(多部门情况下)
     *
     * @param id
     *            用户id
     * @param departmentId
     *            部门id
     */
    void deleteUser(Long id, Long departmentId);

    /**
     * 批量删除用户。(多部门情况下)
     *
     * @param ids
     *            [1,2,3,4]
     * @param departmentId
     *            部门id
     */
    void deleteBatchUser(Long[] ids, Long departmentId);

    /**
     * 赋予用户某角色。
     *
     * @param userId
     *            用户Id
     * @param plusRoleIds
     *            增加用色Id数组
     * @param minusRoleIds
     *            删除用色Id数组
     */
    void grantUserRole(Long userId, Long[] plusRoleIds, Long[] minusRoleIds);

}
