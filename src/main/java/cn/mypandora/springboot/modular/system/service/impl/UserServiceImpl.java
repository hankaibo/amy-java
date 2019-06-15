package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.modular.system.model.UserRole;
import cn.mypandora.springboot.modular.system.mapper.UserMapper;
import cn.mypandora.springboot.modular.system.mapper.UserRoleMapper;
import cn.mypandora.springboot.modular.system.service.UserService;
import cn.mypandora.springboot.core.utils.PasswordUtil;
import cn.mypandora.springboot.core.utils.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * UserServiceImpl
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public String selectRoleByUserId(Long userId) {
        return userMapper.selectRoleByUserId(userId);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> selectUserByRoleId(Long roleId) {
        return userMapper.selectUserByRoleId(roleId);
    }

    @Override
    public boolean authorityUserRole(Long userId, Long roleId) {
        Date now = new Date(System.currentTimeMillis());
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setCreateTime(now);
        userRole.setModifyTime(now);
        return userRoleMapper.insert(userRole) == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean deleteAuthorityUserRole(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRoleMapper.delete(userRole) == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public PageInfo<User> selectByPage(int pageNum, int pageSize, User user) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectByCondition(user);
        return new PageInfo<>(userList);
    }

    @Override
    public User selectByIdOrName(Long userId, String username) {
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        return this.userMapper.selectOne(user);
    }

    @Override
    public User selectById(Long userId) {
        User user = new User();
        user.setId(userId);
        return userMapper.selectOne(user);
    }

    @Override
    public List<User> selectByIds(List<Long> userIdList) {
        return userMapper.selectByIds(userIdList.toString());
    }

    @Override
    public void addUser(User user) {
        Date now = new Date(System.currentTimeMillis());
        String salt = RandomUtil.getSalt();
        user.setSalt(salt);
        //
        String password = PasswordUtil.encrypt(user.getPassword(), salt);
        user.setPassword(password);
        user.setCreateTime(now);
        user.setModifyTime(now);
        userMapper.insert(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteByIds(userId.toString());
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKey(user);
    }
}
