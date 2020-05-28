package cn.mypandora.springboot.modular.system.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import cn.mypandora.springboot.modular.system.model.po.UserRole;
import cn.mypandora.springboot.modular.system.service.UserService;
import tk.mybatis.mapper.entity.Example;

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
    public PageInfo<User> pageUser(int pageNum, int pageSize, User user, Long departmentId) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.pageUser(user, departmentId);

        userList.forEach(item -> {
            item.setPassword(null);
            item.setSalt(null);
        });
        return new PageInfo<>(userList);
    }

    @Override
    public User getUserByName(String username) {
        Example user = new Example(User.class);
        user.createCriteria().andEqualTo("username", username);
        User info = userMapper.selectOneByExample(user);
        if (info == null) {
            throw new EntityNotFoundException(User.class, "用户不存在。");
        }
        return info;
    }

    @Override
    public User getUserById(Long id) {
        // 查询用户
        User info = userMapper.selectByPrimaryKey(id);
        if (info == null) {
            throw new EntityNotFoundException(User.class, "用户不存在。");
        }
        // 避免密码被返回给页面
        info.setSalt(null);
        info.setPassword(null);

        // 查询用户所在部门
        DepartmentUser departmentUser = new DepartmentUser();
        departmentUser.setUserId(id);
        List<DepartmentUser> departmentUserList = departmentUserMapper.select(departmentUser);
        List<Long> departmentIdList = new ArrayList<>();
        for (DepartmentUser du : departmentUserList) {
            departmentIdList.add(du.getDepartmentId());
        }
        info.setDepartmentIdList(departmentIdList);
        return info;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(User user) {
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        passwordHelper(user);

        // 添加用户
        userMapper.insert(user);
        // 添加用户部门关联
        if (user.getDepartmentIdList().size() > 0) {
            List<DepartmentUser> departmentUserList = new ArrayList<>();
            for (Long departmentId : user.getDepartmentIdList()) {
                DepartmentUser departmentUser = new DepartmentUser();
                departmentUser.setDepartmentId(departmentId);
                departmentUser.setUserId(user.getId());
                departmentUser.setCreateTime(now);
                departmentUserList.add(departmentUser);
            }
            departmentUserMapper.insertList(departmentUserList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(User user, Long[] plusDepartmentIds, Long[] minusDepartmentIds) {
        LocalDateTime now = LocalDateTime.now();
        user.setUpdateTime(now);

        userMapper.updateByPrimaryKeySelective(user);

        // 添加用户的新部门关联
        if (plusDepartmentIds.length > 0) {
            List<DepartmentUser> departmentUserList = new ArrayList<>();
            for (Long departmentId : plusDepartmentIds) {
                DepartmentUser departmentUser = new DepartmentUser();
                departmentUser.setDepartmentId(departmentId);
                departmentUser.setUserId(user.getId());
                departmentUser.setCreateTime(now);
                departmentUserList.add(departmentUser);
            }
            departmentUserMapper.insertList(departmentUserList);
        }

        // 删除用户旧的部门关联
        if (minusDepartmentIds.length > 0) {
            Example departmentUser = new Example(DepartmentUser.class);
            departmentUser.createCriteria().andIn("departmentId", Arrays.asList(minusDepartmentIds))
                .andEqualTo("userId", user.getId());
            departmentUserMapper.deleteByExample(departmentUser);
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
        User info = userMapper.selectByPrimaryKey(id);
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
    public void deleteUser(Long id, Long departmentId) {
        // 单一用户多部门情况下，不能直接删除用户对象，而是删除与该用户相关联的部门
        // 部门不为空，则删除用户与部门关系
        DepartmentUser departmentUser = new DepartmentUser();
        departmentUser.setUserId(id);
        departmentUser.setDepartmentId(departmentId);
        departmentUserMapper.delete(departmentUser);
        // 部门为空，则删除用户及用户的所有角色
        if (departmentId == null) {
            userMapper.deleteByPrimaryKey(id);
            // 删除用户所有角色
            Example userRole = new Example(UserRole.class);
            userRole.createCriteria().andEqualTo("userId", id);
            userRoleMapper.deleteByExample(userRole);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchUser(Long[] ids, Long departmentId) {
        // 部门不为空，则删除用户与部门关系
        Example batchDepartmentUser = new Example(DepartmentUser.class);
        batchDepartmentUser.createCriteria().andIn("userId", Arrays.asList(ids)).andEqualTo("departmentId",
            departmentId);
        departmentUserMapper.deleteByExample(batchDepartmentUser);
        // 部门为空，则删除用户及用户的所有角色
        if (departmentId == null) {
            userMapper.deleteByIds(StringUtils.join(ids, ','));
            // 删除用户所有角色（批量）。
            Example batchUserRole = new Example(UserRole.class);
            batchUserRole.createCriteria().andIn("userId", Arrays.asList(ids));
            userRoleMapper.deleteByExample(batchUserRole);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void grantUserRole(Long userId, long[] plusId, long[] minusId) {
        // 删除旧角色
        if (minusId.length > 0) {
            Example userRole = new Example(UserRole.class);
            userRole.createCriteria().andIn("roleId", Arrays.asList(minusId)).andEqualTo("userId", userId);
            userRoleMapper.deleteByExample(userRole);
        }
        // 添加新的角色
        if (plusId.length > 0) {
            LocalDateTime now = LocalDateTime.now();
            List<UserRole> userRoleList = new ArrayList<>();
            for (Long roleId : plusId) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setCreateTime(now);
                userRoleList.add(userRole);
            }
            userRoleMapper.insertList(userRoleList);
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
