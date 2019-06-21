package cn.mypandora.springboot.config.druid;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DruidProperties
 * <p>
 * 方法有三种，如下：
 * 1. @ConfigurationProperties + @Component 注解到bean定义类上
 * 2. @ConfigurationProperties + @Bean注解在配置类的bean定义方法上
 * 3. @ConfigurationProperties注解到普通类然后通过@EnableConfigurationProperties定义为bean
 *
 * @author hankaibo
 * @date 2019/6/17
 * @see <a href="https://blog.csdn.net/andy_zhang2007/article/details/78761651" />
 * @see <a href="https://www.cnblogs.com/duanxz/p/7493276.html#4261534" />
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
class DataSourceProperties {

    /**
     * 数据库URL
     */
    private String url;

    /**
     * 数据库登录名
     */
    private String username;

    /**
     * 数据库登录密码
     */
    private String password;

    /**
     * 数据库驱动类
     */
    private String driverClassName;

    /**
     * 数据库连接池Druid
     * 在这里通过内嵌静态类引入Druid的相关配置数据。
     */
    private DruidProperties druid;

    /**
     * DataSourceProperties
     * <p>
     * 数据库连接池Druid的一些配置属性，数据通过配置文件注入。
     *
     * @author hankaibo
     * @date 2019/6/21
     */
    @Data
    static class DruidProperties {
        private int initialSize;
        private int minIdle;
        private int maxActive;
        private int maxWait;
        private int timeBetweenEvictionRunsMillis;
        private int minEvictableIdleTimeMillis;
        private String validationQuery;
        private boolean testWhileIdle;
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private boolean poolPreparedStatements;
        private int maxPoolPreparedStatementPerConnectionSize;
        private String connectionProperties;
        private boolean useGlobalDataSourceStat;
        private String filters;
    }

}
