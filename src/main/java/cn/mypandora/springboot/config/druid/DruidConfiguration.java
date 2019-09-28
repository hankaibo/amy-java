package cn.mypandora.springboot.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * DruidConfiguration
 * <p>
 * 配置druid数据源、配置druid servlet、配置druid filter。
 * 注：
 * 1.@Configuration就是指的Java Config(Java 配置)，是一个Ioc容器类，相当于传统项目中见到的一个spring的xml配置文件。
 * 当Spring发现某个类使用了@Configuration标注了，就去将该类下使用@Bean注解的方法创建bean并放入到容器中。
 *
 * @author hankaibo
 * @date 2019/1/14
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DruidConfiguration {

    /**
     * 注入Druid配置，yml中的spring.datasource
     */
    private final DataSourceProperties properties;

    /**
     * @param properties spring注入的对象
     *                   使用推荐的构造函数注入，而不是属性和setter方法注入。
     */
    @Autowired
    public DruidConfiguration(DataSourceProperties properties) {
        this.properties = properties;
    }

    /**
     * 注入 DruidDataSource
     *
     * @return dataSource
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        // 数据源主要配置
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setDriverClassName(properties.getDriverClassName());

        // 数据源补充配置
        dataSource.setInitialSize(properties.getDruid().getInitialSize());
        dataSource.setMinIdle(properties.getDruid().getMinIdle());
        dataSource.setMaxActive(properties.getDruid().getMaxActive());
        dataSource.setMaxWait(properties.getDruid().getMaxWait());

        dataSource.setValidationQuery(properties.getDruid().getValidationQuery());
        dataSource.setTestOnBorrow(properties.getDruid().isTestOnBorrow());
        dataSource.setTestOnReturn(properties.getDruid().isTestOnReturn());
        dataSource.setTestWhileIdle(properties.getDruid().isTestWhileIdle());

        dataSource.setTimeBetweenEvictionRunsMillis(properties.getDruid().getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(properties.getDruid().getMinEvictableIdleTimeMillis());

        dataSource.setPoolPreparedStatements(properties.getDruid().isPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getDruid().getMaxPoolPreparedStatementPerConnectionSize());

        dataSource.setUseGlobalDataSourceStat(properties.getDruid().isUseGlobalDataSourceStat());
        dataSource.setConnectionProperties(properties.getDruid().getConnectionProperties());
        try {
            dataSource.setFilters(properties.getDruid().getFilters());
        } catch (SQLException e) {
            log.error("druid configuration initialization filter: " + e);
        }

        return dataSource;
    }

    /**
     * 注入Druid的StatViewServlet。
     * druid监控视图配置。
     *
     * @return servlet
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), properties.getDruid().getStatViewServlet().getUrlPattern());
        // 控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", properties.getDruid().getStatViewServlet().getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", properties.getDruid().getStatViewServlet().getLoginPassword());
        // 是否能够重置数据，禁用html页面上的"reset all'功能
        servletRegistrationBean.addInitParameter("resetEnable", properties.getDruid().getStatViewServlet().getResetEnable());
        return servletRegistrationBean;
    }

    /**
     * 注入Druid的WebStatFilter。
     *
     * @return filter
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean<WebStatFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        filterFilterRegistrationBean.addUrlPatterns(properties.getDruid().getWebStatFilter().getUrlPattern());
        filterFilterRegistrationBean.addInitParameter("exclusions", properties.getDruid().getWebStatFilter().getExclusions());
        return filterFilterRegistrationBean;
    }

}
