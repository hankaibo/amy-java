package cn.mypandora.springboot.core.shiro.filter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import cn.mypandora.springboot.config.filter.CustomFilter;
import cn.mypandora.springboot.config.shiro.RestPathMatchingFilterChainResolver;
import cn.mypandora.springboot.core.shiro.rule.RolePermRule;
import cn.mypandora.springboot.core.support.SpringContextHolder;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import cn.mypandora.springboot.modular.system.service.RoleService;
import cn.mypandora.springboot.modular.system.service.UserService;
import lombok.extern.slf4j.Slf4j;

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
public class FilterChainManager {

    private final UserService userService;
    private final RoleService roleService;
    private final ResourceService resourceService;
    private final StringRedisTemplate redisTemplate;

    @Lazy
    @Autowired
    public FilterChainManager(UserService userService, RoleService roleService, ResourceService resourceService,
        StringRedisTemplate redisTemplate) {
        this.userService = userService;
        this.roleService = roleService;
        this.resourceService = resourceService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 初始化过滤链
     *
     * @return 过滤器链Map对象
     */
    public Map<String, Filter> initFilters() {
        Map<String, Filter> filters = new LinkedHashMap<>();
        // 配置登录过滤器
        PasswordFilter passwordFilter = new PasswordFilter();
        filters.put("auth", passwordFilter);

        // 配置token过滤器
        JwtFilter jwtFilter = new JwtFilter(userService, roleService, resourceService, redisTemplate);
        filters.put("jwt", jwtFilter);

        // 配置自定义过滤器，注入userId参数
        CustomFilter customFilter = new CustomFilter();
        filters.put("userIdFilter", customFilter);

        return filters;
    }

    /**
     * 初始化过滤链规则
     *
     * @return 规则Map对象
     */
    public Map<String, String> initFilterChainDefinitionMap() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // anon 默认过滤器忽略的url
        List<String> defaultAnon = Arrays.asList("/css/**", "/js/**");
        defaultAnon.forEach(ignored -> filterChainDefinitionMap.put(ignored, "anon"));
        // auth 默认需要认证过滤器的URL 走auth--PasswordFilter
        List<String> defaultAuth = Arrays.asList("/api/v1/login", "/api/v1/register");
        defaultAuth.forEach(auth -> filterChainDefinitionMap.put(auth, "auth"));
        // dynamic 动态url
        if (resourceService != null) {
            List<RolePermRule> rolePermRules = this.resourceService.listRolePermRules();
            if (null != rolePermRules) {
                rolePermRules.forEach(rule -> {
                    StringBuilder chain = rule.toFilterChain();
                    if (null != chain) {
                        filterChainDefinitionMap.putIfAbsent(rule.getUrl(), chain.toString() + ",userIdFilter");
                    }
                });
            }
        }
        return filterChainDefinitionMap;
    }

    /**
     * 动态重新加载过滤链规则
     */
    public void reloadFilterChain() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = SpringContextHolder.getBean(ShiroFilterFactoryBean.class);
        AbstractShiroFilter abstractShiroFilter = null;

        try {
            abstractShiroFilter = (AbstractShiroFilter)shiroFilterFactoryBean.getObject();
            RestPathMatchingFilterChainResolver filterChainResolver =
                (RestPathMatchingFilterChainResolver)abstractShiroFilter.getFilterChainResolver();
            DefaultFilterChainManager filterChainManager =
                (DefaultFilterChainManager)filterChainResolver.getFilterChainManager();
            filterChainManager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(this.initFilterChainDefinitionMap());
            shiroFilterFactoryBean.getFilterChainDefinitionMap().forEach(filterChainManager::createChain);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
