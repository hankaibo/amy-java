package cn.mypandora.springboot.config.shiro;

import cn.mypandora.springboot.core.shiro.filter.ShiroFilterChainManager;
import cn.mypandora.springboot.core.shiro.filter.StatelessWebSubjectFactory;
import cn.mypandora.springboot.core.shiro.realm.AonModularRealmAuthenticator;
import cn.mypandora.springboot.core.shiro.realm.RealmManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ShiroConfiguration
 * <p>
 * 配置shiro，一些具体修改在 cn.mypandora.springboot.core.shiro目录。
 * 注：@Configuration就是指的Java Config(Java 配置)，是一个Ioc容器类，相当于传统项目中见到的一个spring的xml配置文件。
 * 当Spring发现某个类使用了@Configuration标注了，就去将该类下使用@Bean注解的方法创建bean并放入到容器中。
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Configuration
public class ShiroConfiguration {

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroFilterChainManager filterChainManager) {
        RestShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilters(filterChainManager.initGetFilters());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainManager.initGetFilterChainDefinitionMap());
        return shiroFilterFactoryBean;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager(RealmManager realmManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(new AonModularRealmAuthenticator());
        securityManager.setRealms(realmManager.initGetRealm());

        // 无状态subjectFactory设置
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO()).getSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(Boolean.FALSE);
        StatelessWebSubjectFactory subjectFactory = new StatelessWebSubjectFactory();
        securityManager.setSubjectFactory(subjectFactory);

        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

}
