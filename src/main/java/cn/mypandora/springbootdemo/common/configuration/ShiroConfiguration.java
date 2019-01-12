package cn.mypandora.springbootdemo.common.configuration;

import cn.mypandora.springbootdemo.common.enums.CookieEnum;
import cn.mypandora.springbootdemo.common.shiro.BaseRealm;
import org.apache.shiro.mgt.SecurityManager;
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
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(baseRealm());
        defaultWebSecurityManager.setSessionManager(sessionManager());
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager());

        shiroFilterFactoryBean.setLoginUrl("/login");

        shiroFilterFactoryBean.setSuccessUrl("/index");

        shiroFilterFactoryBean.setUnauthorizedUrl("/login");

        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
        //
        shiroFilterFactoryBean.setFilters(filtersMap);

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
