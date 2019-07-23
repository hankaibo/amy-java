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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public PageInfo<User> selectUserPage(int pageNum, int pageSize, User user) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.select(user);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Long id) {
        User user = new User();
        user.setId(id);
        userMapper.delete(user);
        userRoleMapper.deleteUserRole(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchUser(String ids) {
        userMapper.deleteByIds(ids);

        Long[] idList = Stream.of(ids.split(",")).map(s -> Long.valueOf(s)).collect(Collectors.toList()).toArray(new Long[]{});
        userRoleMapper.deleteBatchUserRole(idList);
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

        return this.userMapper.updateByPrimaryKeySelective(user) > 0;
    }

    @Override
    public List<Role> selectRoleByIdOrName(Long id, String username) {
        return userRoleMapper.selectUserRole(id, username);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean giveUserRole(Long userId, Long[] roleListId) {
        // 删除旧的角色
        userRoleMapper.deleteUserRole(userId);
        // 添加新的角色
        return userRoleMapper.giveUserRole(userId, roleListId) > 0;
    }

    /**
     * 使用BCrypt加密密码，与之相对应的 PasswordRealm.java 也要使用这个规则。
     *
     * @param user 加密的用户
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
