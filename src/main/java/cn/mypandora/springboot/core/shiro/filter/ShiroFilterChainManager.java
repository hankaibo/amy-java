package cn.mypandora.springboot.core.shiro.filter;

import cn.mypandora.springboot.config.shiro.RestPathMatchingFilterChainResolver;
import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.core.support.SpringContextHolder;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.*;

/**
 * ShiroFilterChainManager
 * <p>
 * 自定义过滤器然后注入到ShiroFilter中。
 *
 * @author hankaibo
 * @date 2019/6/18
 * @see <a href="https://blog.csdn.net/finalcola/article/details/81197584" />
 * @see <a href="https://blog.csdn.net/m0_37962779/article/details/78605478" />
 */
@Slf4j
@Component
public class ShiroFilterChainManager {

    private final StringRedisTemplate redisTemplate;
    private final UserService userService;
    private final ResourceService resourceService;

    @Lazy
    @Autowired
    public ShiroFilterChainManager(StringRedisTemplate redisTemplate, UserService userService, ResourceService resourceService) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.resourceService = resourceService;
    }

    /**
     * 初始化过滤链
     *
     * @return 过滤器链Map对象
     */
    public Map<String, Filter> initGetFilters() {
        Map<String, Filter> filters = new LinkedHashMap<>();
        // 配置登录过滤器
        PasswordFilter passwordFilter = new PasswordFilter();
        filters.put("auth", passwordFilter);

        // 配置token过滤器
        BonJwtFilter jwtFilter = new BonJwtFilter();
        jwtFilter.setRedisTemplate(redisTemplate);
        jwtFilter.setUserService(userService);
        filters.put("jwt", jwtFilter);

        return filters;
    }

    /**
     * 初始化过滤链规则
     *
     * @return 规则Map对象
     */
    public Map<String, String> initGetFilterChainDefinitionMap() {
        Map<String, String> chains = new LinkedHashMap<>();
        // anon 默认过滤器忽略的url
        List<String> defaultAnon = Arrays.asList("/css/**","/swagger-resources/**","/swagger/**");
        defaultAnon.forEach(ignored -> chains.put(ignored, "anon"));
        // auth 默认需要认证过滤器的url 走auth-PasswordFilter
        List<String> defaultAth = Arrays.asList("/api/v1/login/**");
        defaultAth.forEach(auth -> chains.put(auth, "auth"));
        // dynamic 动态url
        if (resourceService != null) {
//            List<RolePermRule> rolePermRules = this.resourceService.loadRolePermRules();
            // ---- TODO-------------------------------------------------------------------
            List<RolePermRule> rolePermRules = new ArrayList<>();
            RolePermRule foo1 = new RolePermRule();
            foo1.setUrl("/api/v1/dicts==POST");
            foo1.setNeedRoles("admin,user");
            rolePermRules.add(foo1);
            RolePermRule foo2 = new RolePermRule();
            foo2.setUrl("/api/v1/dicts==GET");
            foo2.setNeedRoles("admin,user");
            rolePermRules.add(foo2);
            RolePermRule foo3 = new RolePermRule();
            foo3.setUrl("/api/v1/dicts==DELETE");
            foo3.setNeedRoles("admin,user");
            rolePermRules.add(foo3);
            RolePermRule foo4 = new RolePermRule();
            foo4.setUrl("/api/v1/users/info==GET");
            foo4.setNeedRoles("admin,user");
            rolePermRules.add(foo4);
            RolePermRule foo5 = new RolePermRule();
            foo4.setUrl("/api/v1/users**==GET,POST,DELETE,PUT");
            foo4.setNeedRoles("admin,user");
            rolePermRules.add(foo5);
            // ---- TODO-------------------------------------------------------------------
            if (null != rolePermRules) {
                rolePermRules.forEach(rule -> {
                    StringBuilder chain = rule.toFilterChain();
                    if (null != chain) {
                        chains.putIfAbsent(rule.getUrl(), chain.toString());
                    }
                });
            }
        }
        return chains;
    }

    /**
     * 动态重新加载过滤链规则
     */
    public void reloadFilterChain() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = SpringContextHolder.getBean(ShiroFilterFactoryBean.class);
        AbstractShiroFilter abstractShiroFilter = null;

        try {
            abstractShiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            RestPathMatchingFilterChainResolver filterChainResolver = (RestPathMatchingFilterChainResolver) abstractShiroFilter.getFilterChainResolver();
            DefaultFilterChainManager filterChainManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            filterChainManager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(this.initGetFilterChainDefinitionMap());
            shiroFilterFactoryBean.getFilterChainDefinitionMap().forEach((k, v) -> filterChainManager.createChain(k, v));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
