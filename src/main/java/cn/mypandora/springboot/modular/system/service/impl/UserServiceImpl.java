package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.utils.PasswordUtil;
import cn.mypandora.springboot.modular.system.mapper.UserMapper;
import cn.mypandora.springboot.modular.system.mapper.UserRoleMapper;
import cn.mypandora.springboot.modular.system.model.User;
import cn.mypandora.springboot.modular.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
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

    private UserMapper userMapper;

    private UserRoleMapper userRoleMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public PageInfo<User> selectUserList(int pageNum, int pageSize, User user) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectByCondition(user);
        return new PageInfo<>(userList);
    }

    @Override
    public User selectUserByIdOrName(Long userId, String username) {
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        return this.userMapper.selectOne(user);
    }

    @Override
    public void addUser(User user) {
        Date now = new Date(System.currentTimeMillis());
        String salt = RandomStringUtils.randomAlphabetic(16);
        user.setSalt(salt);
        //
        String password = PasswordUtil.encrypt(user.getPassword(), salt);
        user.setPassword(password);
        user.setCreateTime(now);
        userMapper.insert(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteByIds(id.toString());
    }

    @Override
    public void deleteBatchUser(String ids) {
        userMapper.deleteByIds(ids);
    }

    @Override
    public void updateUser(User user) {
        Date now = new Date(System.currentTimeMillis());
        user.setModifyTime(now);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public boolean enableUser(Long id) {
        return false;
    }
}
