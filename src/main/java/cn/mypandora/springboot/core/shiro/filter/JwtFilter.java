package cn.mypandora.springboot.core.shiro.filter;

import cn.mypandora.springboot.core.shiro.token.JwtToken;
import cn.mypandora.springboot.core.utils.IpUtil;
import cn.mypandora.springboot.core.utils.JsonWebTokenUtil;
import cn.mypandora.springboot.core.utils.RequestResponseUtil;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.vo.Message;
import cn.mypandora.springboot.modular.system.service.UserService;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * JwtFilter
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Slf4j
public class JwtFilter extends AbstractPathMatchingFilter {
    private static final String STR_EXPIRED = "expiredJwt";

    private StringRedisTemplate redisTemplate;
    private UserService userService;

    @Autowired
    public JwtFilter(StringRedisTemplate redisTemplate, UserService userService) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
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
                    // TODO
                    String username = null;
                    String jwt = WebUtils.toHttp(servletRequest).getHeader("authorization");
                    String refreshJwt = redisTemplate.opsForValue().get("JWT-SESSION-" + username);
                    if (null != refreshJwt && refreshJwt.equals(jwt)) {
                        // 重新申请新的JWT
                        // 根据 username 获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
                        List<Role> roleList = userService.selectRoleList(null, username);
                        StringBuffer stringBuffer = new StringBuffer();
                        for (Role role : roleList) {
                            stringBuffer.append(role.getName());
                        }
                        String roles = stringBuffer.toString();
                        //seconds为单位,10 hours
                        long refreshPeriodTime = 36000L;
                        String newJwt = JsonWebTokenUtil.createJwt(UUID.randomUUID().toString(),
                                username,
                                "token-server",
                                refreshPeriodTime >> 1,
                                roles,
                                null,
                                SignatureAlgorithm.HS512
                        );
                        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
                        redisTemplate.opsForValue().set("JWT-SESSION-" + username, newJwt, refreshPeriodTime, TimeUnit.SECONDS);
                        Message message = new Message().ok(1005, "new jwt").addData("jwt", newJwt);
                        RequestResponseUtil.responseWrite(JSON.toJSONString(message), servletResponse);
                        return false;
                    } else {
                        // jwt时间失效过期,jwt refresh time失效 返回jwt过期客户端重新登录
                        Message message = new Message().error(1006, "expired jwt");
                        RequestResponseUtil.responseWrite(JSON.toJSONString(message), servletResponse);
                        return false;
                    }

                }
                // 其他的判断为JWT错误无效
                Message message = new Message().error(1007, "error Jwt");
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), servletResponse);
                return false;

            } catch (Exception e) {
                // 其他错误
                log.error(IpUtil.getIpFromRequest(WebUtils.toHttp(servletRequest)) + "--JWT认证失败" + e.getMessage(), e);
                // 告知客户端JWT错误1005,需重新登录申请jwt
                Message message = new Message().error(1007, "error jwt");
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), servletResponse);
                return false;
            }
        } else {
            // 请求未携带jwt 判断为无效请求
            Message message = new Message().error(1111, "error request");
            RequestResponseUtil.responseWrite(JSON.toJSONString(message), servletResponse);
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);

        // 未认证的情况上面已经处理  这里处理未授权
        if (subject != null && subject.isAuthenticated()) {
            //  已经认证但未授权的情况
            // 告知客户端JWT没有权限访问此资源
            Message message = new Message().error(1008, "no permission");
            RequestResponseUtil.responseWrite(JSON.toJSONString(message), servletResponse);
        }
        // 过滤链终止
        return false;
    }

    private boolean isJwtSubmission(ServletRequest request) {
        String jwt = RequestResponseUtil.getHeader(request, "authorization");
        return (request instanceof HttpServletRequest) && StringUtils.isNotEmpty(jwt);
    }

    private AuthenticationToken createJwtToken(ServletRequest request) {
        Map<String, String> maps = RequestResponseUtil.getRequestHeaders(request);
        String jwt = maps.get("authorization");

        return new JwtToken(jwt);
    }

    /**
     * description 验证当前用户是否属于mappedValue任意一个角色
     *
     * @param subject     1
     * @param mappedValue 2
     * @return boolean
     */
    private boolean checkRoles(Subject subject, Object mappedValue) {
        String[] rolesArray = (String[]) mappedValue;
        return rolesArray == null
                || rolesArray.length == 0
                || Stream.of(rolesArray).anyMatch(role -> subject.hasRole(role.trim()));
    }
}
