package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.mapper.DepartmentUserMapper;
import cn.mypandora.springboot.modular.system.mapper.UserMapper;
import cn.mypandora.springboot.modular.system.mapper.UserRoleMapper;
import cn.mypandora.springboot.modular.system.model.po.DepartmentUser;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.service.UserService;
import com.github.pagehelper.PageHelper;
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
    private DepartmentUserMapper departmentUserMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper, DepartmentUserMapper departmentUserMapper) {
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
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return this.userMapper.selectOne(user);
    }

    @Override
    public User getUserById(Long id) {
        return this.userMapper.getUser(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(User user) {
        Date now = new Date(System.currentTimeMillis());
        user.setCreateTime(now);
        passwordHelper(user);

        DepartmentUser departmentUser = new DepartmentUser();
        departmentUser.setDepartmentId(user.getDepartmentId());
        departmentUser.setUserId(user.getId());
        departmentUser.setCreateTime(now);

        userMapper.insert(user);
        departmentUserMapper.insert(departmentUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Long id) {
        User user = new User();
        user.setId(id);

        userMapper.delete(user);
        userRoleMapper.deleteUserRole(id);
        departmentUserMapper.deleteByUserId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchUser(String ids) {
        userMapper.deleteByIds(ids);

        Long[] idList = Stream.of(ids.split(",")).map(s -> Long.valueOf(s)).collect(Collectors.toList()).toArray(new Long[]{});
        userRoleMapper.deleteBatchUserRole(idList);
        departmentUserMapper.deleteBatchByUserIds(idList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(User user) {
        Date now = new Date(System.currentTimeMillis());
        user.setUpdateTime(now);

        userMapper.updateByPrimaryKeySelective(user);
        departmentUserMapper.updateByUserId(user.getId(), user.getDepartmentId());
    }

    @Override
    public boolean enableUser(Long id, Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);

        return this.userMapper.updateByPrimaryKeySelective(user) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean grantUserRole(Long userId, Long[] roleListId) {
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
