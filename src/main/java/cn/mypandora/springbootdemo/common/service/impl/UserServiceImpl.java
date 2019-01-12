package cn.mypandora.springbootdemo.common.service.impl;

import cn.mypandora.springbootdemo.common.entity.User;
import cn.mypandora.springbootdemo.common.service.UserService;
import cn.mypandora.springbootdemo.common.mapper.UserMapper;
import cn.mypandora.springbootdemo.common.utils.PasswordUtil;
import cn.mypandora.springbootdemo.common.utils.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public PageInfo<User> listAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectAll();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public User queryByIdOrName(Long userId, String username) {
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        return this.userMapper.selectOne(user);
    }

    @Override
    public User queryUserById(Long userId) {
        User user = new User();
        user.setId(userId);
        return userMapper.selectOne(user);
    }

    @Override
    public List<User> queryUserByIds(List<Long> userIdList) {
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
