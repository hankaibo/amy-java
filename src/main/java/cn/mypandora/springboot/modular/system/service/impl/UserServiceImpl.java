package cn.mypandora.springboot.modular.system.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.mypandora.springboot.config.exception.BusinessException;
import cn.mypandora.springboot.config.exception.EntityNotFoundException;
import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.mapper.DepartmentUserMapper;
import cn.mypandora.springboot.modular.system.mapper.UserMapper;
import cn.mypandora.springboot.modular.system.mapper.UserRoleMapper;
import cn.mypandora.springboot.modular.system.model.po.DepartmentUser;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.service.UserService;

/**
 * UserServiceImpl
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private UserRoleMapper userRoleMapper;
    private DepartmentUserMapper departmentUserMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper,
        DepartmentUserMapper departmentUserMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.departmentUserMapper = departmentUserMapper;
    }

    @Override
    public PageInfo<User> pageUser(int pageNum, int pageSize, User user) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.pageUser(user);
        return new PageInfo<>(userList);
    }

    @Override
    public User getUserByIdOrName(Long id, String username) {
        User info = userMapper.getUser(id, username);
        if (info == null) {
            throw new EntityNotFoundException(User.class, "用户不存在。");
        }
        return info;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(User user) {
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        passwordHelper(user);

        userMapper.insert(user);

        if (null != user.getDepartmentId()) {
            DepartmentUser departmentUser = new DepartmentUser();
            departmentUser.setDepartmentId(user.getDepartmentId());
            departmentUser.setUserId(user.getId());
            departmentUser.setCreateTime(now);
            departmentUserMapper.insert(departmentUser);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(User user) {
        LocalDateTime now = LocalDateTime.now();
        user.setUpdateTime(now);

        userMapper.updateByPrimaryKeySelective(user);

        User info = userMapper.getUser(user.getId(), null);
        if (!info.getDepartmentId().equals(user.getDepartmentId())) {
            departmentUserMapper.updateByUserId(user.getId(), user.getDepartmentId());
        }
    }

    @Override
    public void enableUser(Long id, Integer status) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        user.setUpdateTime(now);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void resetPassword(Long id, String password) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setUpdateTime(now);
        passwordHelper(user);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User info = userMapper.getUser(id, null);
        if (!info.getPassword().equals(BCrypt.hashpw(oldPassword, info.getSalt()))) {
            throw new BusinessException(User.class, "密码错误。");
        }

        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setId(id);
        user.setPassword(newPassword);
        user.setUpdateTime(now);
        passwordHelper(user);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Long id) {
        User user = new User();
        user.setId(id);

        userMapper.delete(user);
        userRoleMapper.deleteUserAllRole(id);
        departmentUserMapper.deleteByUserId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchUser(Long[] ids) {
        userMapper.deleteByIds(StringUtils.join(ids, ','));
        userRoleMapper.deleteBatchUserAllRole(ids);
        departmentUserMapper.deleteBatchByUserIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void grantUserRole(Long userId, long[] plusId, long[] minusId) {
        // 删除角色
        if (minusId.length > 0) {
            userRoleMapper.deleteUserSomeRole(userId, minusId);
        }
        // 添加新的角色
        if (plusId.length > 0) {
            userRoleMapper.grantUserRole(userId, plusId);
        }
    }

    /**
     * 使用BCrypt加密密码，与之相对应的 PasswordRealm.java 也要使用这个规则。
     *
     * @param user
     *            加密的用户
     */
    private void passwordHelper(User user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            return;
        }
        String salt = BCrypt.gensalt();
        user.setSalt(salt);
        String originPassword = user.getPassword();
        if (StringUtils.isNotBlank(originPassword)) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
        }
    }

}
