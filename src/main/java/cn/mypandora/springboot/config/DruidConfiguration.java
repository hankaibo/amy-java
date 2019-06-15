package cn.mypandora.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * DruidConfiguration
 * 配置druid数据源、配置druid servlet、配置druid filter
 *
 * @author hankaibo
 * @date 2019/1/14
 */
@Slf4j
@Data
@Configuration
public class DruidConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.druid.initial-size}")
    private int initialSize;
    @Value("${spring.datasource.druid.min-idle}")
    private int minIdle;
    @Value("${spring.datasource.druid.max-active}")
    private int maxActive;
    @Value("${spring.datasource.druid.max-wait}")
    private int maxWait;
    @Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.druid.min-evictable-idle-time-millis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.druid.validation-query}")
    private String validationQuery;
    @Value("${spring.datasource.druid.test-while-idle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.druid.test-on-borrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.druid.test-on-return}")
    private boolean testOnReturn;
    @Value("${spring.datasource.druid.pool-prepared-statements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.druid.connection-properties}")
    private String connectionProperties;
    @Value("${spring.datasource.druid.use-global-data-source-stat}")
    private boolean useGlobalDataSourceStat;
    @Value("${spring.datasource.druid.filters}")
    private String filters;

    /**
     * 注入 DruidDataSource
     *
     * @return dataSource
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        // 数据源主要配置
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        // 数据源补充配置
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        dataSource.setConnectionProperties(connectionProperties);
        dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            log.error("druid configuration initialization filter: " + e);
        }

        return dataSource;
    }

    /**
     * 注入Druid的StatViewServlet。
     * 注解注入时，必须使用传统的<artifactId>druid-</artifactId>引入，
     * 否则，springboot的<artifactId>druid-spring-boot-starter</artifactId>引入时，
     * 它读取的是配置文件参数，导致直接写在注解中的参数失败。
     * <p>
     *
     * @return servlet
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", getUsername());
        servletRegistrationBean.addInitParameter("loginPassword", getPassword());
        // 是否能够重置数据，禁用html页面上的"reset all'功能
        servletRegistrationBean.addInitParameter("resetEnable", "true");
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
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterFilterRegistrationBean;
    }

}
