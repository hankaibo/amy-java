package cn.mypandora.springboot.core.shiro.config;

import cn.mypandora.springboot.core.shiro.filter.ShiroFilterChainManager;
import cn.mypandora.springboot.core.shiro.filter.StatelessWebSubjectFactory;
import cn.mypandora.springboot.core.shiro.realm.AModularRealmAuthenticator;
import cn.mypandora.springboot.core.shiro.realm.RealmManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;

/**
 * ShiroConfiguration
 *
 *
 */
//@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroFilterChainManager filterChainManager) {
        RestShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
        // 使用自定义的安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 使用自定义过滤器
        shiroFilterFactoryBean.setFilters(filterChainManager.initGetFilters());
        // 使用自定义的过滤器链定义
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainManager.initGetFilterChain());
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(RealmManager realmManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义认证器
        securityManager.setAuthenticator(new AModularRealmAuthenticator());
        // 设置自定义realm
        securityManager.setRealms(realmManager.initGetRealm());

        // 无状态subjectFactory设置
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO()).getSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(Boolean.FALSE);
        StatelessWebSubjectFactory subjectFactory = new StatelessWebSubjectFactory();
        securityManager.setSubjectFactory(subjectFactory);

        // 使用自定义的安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

}
