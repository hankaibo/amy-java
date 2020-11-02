package cn.mypandora.springboot.modular.system.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;

import cn.mypandora.springboot.config.exception.BusinessException;
import cn.mypandora.springboot.config.exception.EntityNotFoundException;
import cn.mypandora.springboot.config.exception.StorageException;
import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.util.FileUtil;
import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import cn.mypandora.springboot.modular.system.mapper.DepartmentUserMapper;
import cn.mypandora.springboot.modular.system.mapper.UserMapper;
import cn.mypandora.springboot.modular.system.mapper.UserRoleMapper;
import cn.mypandora.springboot.modular.system.model.po.*;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import cn.mypandora.springboot.modular.system.model.vo.Token;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
import cn.mypandora.springboot.modular.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * UserServiceImpl
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final ObjectMapper om = new ObjectMapper();

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final DepartmentUserMapper departmentUserMapper;
    private RoleService roleService;
    private ResourceService resourceService;
    private StringRedisTemplate stringRedisTemplate;

    @Value("${upload.path}")
    private String dirPath;
    @Value("${upload.remote-url}")
    private String remoteUrl;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper,
        DepartmentUserMapper departmentUserMapper, RoleService roleService, ResourceService resourceService,
        StringRedisTemplate stringRedisTemplate) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.departmentUserMapper = departmentUserMapper;
        this.roleService = roleService;
        this.resourceService = resourceService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Token login(String username) {
        User user = getUserByName(username);
        Long userId = user.getId();

        // 获取角色信息
        Map<String, String> roleMap = getRoles(username);
        String roleCodes = roleMap.get("code");
        String roleIds = roleMap.get("id");

        // 获取资源信息
        String resourceCodes = getResourceCodes(username);
        // 时间以秒计算,token有效刷新时间是token有效过期时间的2倍
        long refreshPeriodTime = 36000L;

        // 生成jwt并将签发的JWT存储到Redis： {JWT-ID-{username} , jwt}
        String jwt = JsonWebTokenUtil.createJwt(UUID.randomUUID().toString(), "token-server", username,
            refreshPeriodTime >> 1, userId, roleIds, roleCodes, resourceCodes);
        stringRedisTemplate.opsForValue().set(StringUtils.upperCase("JWT-ID-" + username), jwt, refreshPeriodTime,
            TimeUnit.SECONDS);

        // 返回给前台数据
        Token token = new Token();
        token.setToken(jwt);
        token.setRoles(roleCodes);
        token.setResources(resourceCodes);

        return token;
    }

    @Override
    public void logout(String authorization) {
        SecurityUtils.getSubject().logout();
        String jwt = JsonWebTokenUtil.unBearer(authorization);
        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        String username = jwtAccount.getAppId();
        if (StringUtils.isEmpty(username)) {
            throw new EntityNotFoundException(User.class, "Authorization错误，用户为空。");
        }
        String jwtInRedis = stringRedisTemplate.opsForValue().get(StringUtils.upperCase("JWT-ID-" + username));
        if (StringUtils.isEmpty(jwtInRedis)) {
            throw new EntityNotFoundException(User.class, "token为空。");
        }
        stringRedisTemplate.opsForValue().getOperations().delete(StringUtils.upperCase("JWT-ID-" + username));
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
    public String saveFile(MultipartFile file) {
        return FileUtil.saveFile(file, dirPath, remoteUrl);
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

        // 转换用户头像地址
        back2FrontPath(info);

        return info;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(User user) {
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        user.setLastLoginTime(null);
        passwordHelper(user);

        // 转换用户头像地址
        front2BackPath(user);

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

        // 转换用户头像地址
        front2BackPath(user);

        // 如果头像更换则删除旧头像
        User info = null;
        try {
            info = userMapper.selectByPrimaryKey(user.getId());
            if (null != info.getAvatar() && !StringUtils.isNotBlank(info.getAvatar())
                && !StringUtils.equals(info.getAvatar(), user.getAvatar())) {
                Path rootLocation = Paths.get(dirPath);
                Files.deleteIfExists(rootLocation.resolve(info.getAvatar()));
            }
        } catch (IOException e) {
            throw new StorageException("Failed to delete file " + info.getAvatar());
        }

        // 修改用户
        userMapper.updateByPrimaryKeySelective(user);

        // 添加用户的新部门关联
        if (null != plusDepartmentIds && plusDepartmentIds.length > 0) {
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
        if (null != minusDepartmentIds && minusDepartmentIds.length > 0) {
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
        // 用户多部门情况下，不能直接删除用户对象，而是删除与该用户相关联的部门
        DepartmentUser departmentUser = new DepartmentUser();
        departmentUser.setUserId(id);
        departmentUser.setDepartmentId(departmentId);
        departmentUserMapper.delete(departmentUser);

        // 如果用户没有部门时，物理删除该用户
        Integer exists = departmentUserMapper.existsUserByUserId(id);
        if (exists == null) {
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
        Set<Long> idSet = Set.of(ids);
        // 部门不为空，则删除用户与部门关系
        Example batchDepartmentUser = new Example(DepartmentUser.class);
        batchDepartmentUser.createCriteria().andIn("userId", idSet).andEqualTo("departmentId", departmentId);
        departmentUserMapper.deleteByExample(batchDepartmentUser);

        Set<Long> deleteId = new HashSet<>();
        for (Long id : idSet) {
            Integer exists = departmentUserMapper.existsUserByUserId(id);
            if (exists == null) {
                deleteId.add(id);
            }
        }

        if (deleteId.size() > 0) {
            userMapper.deleteByIds(StringUtils.join(deleteId, ','));
            // 删除用户所有角色（批量）。
            Example batchUserRole = new Example(UserRole.class);
            batchUserRole.createCriteria().andIn("userId", deleteId);
            userRoleMapper.deleteByExample(batchUserRole);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void grantUserRole(Long userId, Long[] plusRoleIds, Long[] minusRoleIds) {
        // 删除旧角色
        if (minusRoleIds != null && minusRoleIds.length > 0) {
            Example userRole = new Example(UserRole.class);
            userRole.createCriteria().andIn("roleId", Arrays.asList(minusRoleIds)).andEqualTo("userId", userId);
            userRoleMapper.deleteByExample(userRole);
        }

        // 添加新的角色
        if (plusRoleIds != null && plusRoleIds.length > 0) {
            LocalDateTime now = LocalDateTime.now();
            List<UserRole> userRoleList = new ArrayList<>();
            for (Long roleId : plusRoleIds) {
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
     * 根据用户姓名，获取其角色码和id
     * 
     * @param username
     *            用户名称
     * @return 角色信息
     */
    private Map<String, String> getRoles(String username) {
        // 获取角色信息
        List<Role> roleList = roleService.listRoleByUserIdOrName(null, username);
        List<String> roleCodeList = roleList.stream().map(Role::getCode).collect(Collectors.toList());
        List<Long> roleIdList = roleList.stream().map(Role::getId).collect(Collectors.toList());

        String roleCodes = StringUtils.join(roleCodeList, ',');
        String roleIds = StringUtils.join(roleIdList, ',');

        Map<String, String> map = new HashMap<>(2);
        map.put("code", roleCodes);
        map.put("id", roleIds);
        return map;
    }

    /**
     * 根据用户姓名，获取其资源码
     * 
     * @param username
     *            用户名称
     * @return 资源码
     */
    private String getResourceCodes(String username) {
        List<Resource> resourceList =
            resourceService.listResourceByUserIdOrName(null, username, null, StatusEnum.ENABLED.getValue());
        List<String> resourceCodeList = resourceList.stream().map(Resource::getCode).collect(Collectors.toList());
        return StringUtils.join(resourceCodeList, ',');
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

    /**
     * 将前台传递来的路径转为后台数据库路径
     *
     * @param user
     *            用户对象
     */
    private void front2BackPath(User user) {
        converterPath(user, dirPath);
    }

    /**
     * 将后台传递来的路径转为后前数据库路径
     *
     * @param user
     *            用户对象
     */
    private void back2FrontPath(User user) {
        converterPath(user, remoteUrl);
    }

    private void converterPath(User user, String prefix) {
        if (StringUtils.isNotBlank(user.getAvatar())) {
            String filename = user.getAvatar().substring(user.getAvatar().lastIndexOf('/') + 1);
            String path = prefix + filename;
            user.setAvatar(path);
        }
    }

}
