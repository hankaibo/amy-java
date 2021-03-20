package cn.mypandora.springboot.core.shiro.filter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.shiro.token.JwtToken;
import cn.mypandora.springboot.core.util.IpUtil;
import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import cn.mypandora.springboot.core.util.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.po.User;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
import cn.mypandora.springboot.modular.system.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * JwtFilter
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Slf4j
public class JwtFilter extends AbstractPathMatchingFilter {
    private static final String STR_EXPIRED = "expiredJwt";

    private UserService userService;
    private RoleService roleService;
    private ResourceService resourceService;
    private StringRedisTemplate redisTemplate;

    @Autowired
    public JwtFilter(UserService userService, RoleService roleService, ResourceService resourceService,
        StringRedisTemplate redisTemplate) {
        this.userService = userService;
        this.roleService = roleService;
        this.resourceService = resourceService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
        Object mappedValue) {
        Subject subject = getSubject(servletRequest, servletResponse);

        boolean isJwtPost = (null != subject && !subject.isAuthenticated()) && isJwtSubmission(servletRequest);
        // 判断是否为JWT认证请求
        if (isJwtPost) {
            AuthenticationToken token = createJwtToken(servletRequest);
            try {
                subject.login(token);
                return this.checkRoles(subject, mappedValue);
            } catch (AuthenticationException e) {
                // 如果是JWT过期
                if (STR_EXPIRED.equals(e.getMessage())) {
                    // 这里初始方案先抛出令牌过期，之后设计为在Redis中查询当前appId对应令牌，其设置的过期时间是JWT的两倍，此作为JWT的refresh时间
                    // 当JWT的有效时间过期后，查询其refresh时间，refresh时间有效即重新派发新的JWT给客户端，
                    // refresh也过期则告知客户端JWT时间过期重新认证

                    // 当存储在redis的JWT没有过期，即refresh time 没有过期
                    // 获取用户信息
                    String username = SecurityUtils.getSubject().getPrincipal().toString();
                    User user = userService.getUserByName(username);
                    Long userId = user.getId();

                    String jwt = JsonWebTokenUtil.unBearer(WebUtils.toHttp(servletRequest).getHeader("authorization"));
                    String refreshJwt = redisTemplate.opsForValue().get(StringUtils.upperCase("JWT-ID-" + username));

                    if (null != refreshJwt && refreshJwt.equals(jwt)) {
                        // 重新申请新的JWT
                        // 根据 username 获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
                        // 获取角色信息
                        List<Role> roleList = roleService.listRoleByUserIdOrName(null, username);
                        List<String> roleCodeList = roleList.stream().map(Role::getCode).collect(Collectors.toList());
                        List<Long> roleIdList = roleList.stream().map(Role::getId).collect(Collectors.toList());

                        String roleCodes = String.join(",", roleCodeList);
                        String roleIds = StringUtils.join(roleIdList, ',');

                        // 获取资源信息
                        List<Resource> resourceList =
                            resourceService.listResourceByUserIdOrName(null, username, null, StatusEnum.ENABLED);
                        List<String> resourceCodeList =
                            resourceList.stream().map(Resource::getCode).collect(Collectors.toList());

                        String resourceCodes = String.join(",", resourceCodeList);

                        // seconds为单位,10 hours
                        long refreshPeriodTime = 36000L;

                        String newJwt = JsonWebTokenUtil.createJwt(UUID.randomUUID().toString(), "token-server",
                            username, refreshPeriodTime >> 1, userId, roleIds, roleCodes, resourceCodes);
                        redisTemplate.opsForValue().set(StringUtils.upperCase("JWT-ID-" + username), newJwt,
                            refreshPeriodTime, TimeUnit.SECONDS);
                        RequestResponseUtil.responseWrite(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
                            servletResponse);
                        return false;
                    } else {
                        // jwt时间失效过期,jwt refresh time失效 返回jwt过期客户端重新登录
                        RequestResponseUtil.responseWrite(HttpStatus.UNAUTHORIZED.value(),
                            HttpStatus.UNAUTHORIZED.getReasonPhrase(), servletResponse);
                        return false;
                    }

                }
                // 其他的判断为JWT错误无效
                RequestResponseUtil.responseWrite(HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(), servletResponse);
                return false;
            } catch (Exception e) {
                // 其他错误
                log.error(IpUtil.getIpFromRequest(WebUtils.toHttp(servletRequest)) + "--JWT认证失败" + e.getMessage(), e);
                // 告知客户端JWT错误1005,需重新登录申请jwt
                RequestResponseUtil.responseWrite(HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(), servletResponse);
                return false;
            }
        } else {
            // 请求未携带jwt 判断为无效请求
            RequestResponseUtil.responseWrite(HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(), servletResponse);
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);

        // 未认证的情况上面已经处理 这里处理未授权
        if (subject != null && subject.isAuthenticated()) {
            // 已经认证但未授权的情况
            // 告知客户端JWT没有权限访问此资源
            RequestResponseUtil.responseWrite(HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(), servletResponse);
        }
        // 过滤链终止
        return false;
    }

    private boolean isJwtSubmission(ServletRequest request) {
        String jwt = JsonWebTokenUtil.unBearer(RequestResponseUtil.getHeader(request, "authorization"));
        return (request instanceof HttpServletRequest) && StringUtils.isNotEmpty(jwt);
    }

    private AuthenticationToken createJwtToken(ServletRequest request) {
        Map<String, String> maps = RequestResponseUtil.getRequestHeaders(request);
        String jwt = JsonWebTokenUtil.unBearer(maps.get("authorization"));

        return new JwtToken(jwt);
    }

    /**
     * description 验证当前用户是否属于mappedValue任意一个角色
     *
     * @param subject
     *            1
     * @param mappedValue
     *            2
     * @return boolean
     */
    private boolean checkRoles(Subject subject, Object mappedValue) {
        String[] rolesArray = (String[])mappedValue;
        return rolesArray == null || rolesArray.length == 0
            || Stream.of(rolesArray).anyMatch(role -> subject.hasRole(role.trim()));
    }
}
