package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.mapper.UserMapper;
import cn.mypandora.springboot.modular.system.mapper.UserRoleMapper;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
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
        userList.forEach(item -> {
            item.setSalt(null);
            item.setPassword(null);
        });
        return new PageInfo<>(userList);
    }

    @Override
    public User selectUserByIdOrName(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return this.userMapper.selectOne(user);
    }

    @Override
    public void addUser(User user) {
        Date now = new Date(System.currentTimeMillis());
        passwordHelper(user);
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
        passwordHelper(user);
        user.setModifyTime(now);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public boolean enableUser(Long id, Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);

        int result = this.userMapper.updateByPrimaryKeySelective(user);
        return result > 0;
    }

    @Override
    public List<Role> selectRoleList(Long id, String username) {
        Role role = new Role();
        role.setName("admin");
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        return roleList;
    }

    private void passwordHelper(User user) {
        // 使用BCrypt加密密码，与之相对应的 PasswordRealm.java 也要使用这个规则。
        String salt = BCrypt.gensalt();
        user.setSalt(salt);
        String originPassword = user.getPassword();
        if (StringUtils.isNotBlank(originPassword)) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
        }
    }
}
