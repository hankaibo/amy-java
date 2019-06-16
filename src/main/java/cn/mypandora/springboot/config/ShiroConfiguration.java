package cn.mypandora.springboot.config;

import cn.mypandora.springboot.core.enums.CookieEnum;
import cn.mypandora.springboot.core.shiro.realm.BaseRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ShiroConfiguration
 * 作用等同于shiro的xml配置文件，将以前的xml配置文件用java类实现而已。
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Configuration
public class ShiroConfiguration {
    @Bean
    public BaseRealm baseRealm() {
        return new BaseRealm();
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(7200000);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        return defaultWebSessionManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie(CookieEnum.REMEMBER_ME.getValue());
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(baseRealm());
        defaultWebSecurityManager.setSessionManager(sessionManager());
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // 设置登录路径
        shiroFilterFactoryBean.setLoginUrl("/api/v1/login");
        // 设置登录成功后跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 设置访问没有权限跳转到的界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/login");
        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
        // 设置过滤器链
        shiroFilterFactoryBean.setFilters(filtersMap);

        // 过滤器链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/test/checkAuthc", "authc");
        filterChainDefinitionMap.put("/test/**", "anon");
        // druid
        filterChainDefinitionMap.put("/druid", "anon");
        // swagger
        filterChainDefinitionMap.put("/swagger", "anon");
        filterChainDefinitionMap.put("/swagger/api/docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        // other
        filterChainDefinitionMap.put("/*", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }
}
