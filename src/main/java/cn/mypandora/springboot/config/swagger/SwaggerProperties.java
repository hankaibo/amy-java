package cn.mypandora.springboot.config.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * SwaggerProperties
 * <p>
 * 方法有三种，如下：
 * <ul>
 * <li>1. @ConfigurationProperties + @Component 注解到bean定义类上</li>
 * <li>2. @ConfigurationProperties + @Bean注解在配置类的bean定义方法上</li>
 * <li>3. @ConfigurationProperties注解到普通类然后通过@EnableConfigurationProperties定义为bean</li>
 * </ul>
 *
 * <p>
 * 读取配置文件(application*.xml)中的属性值。
 * <p>
 * 注：
 * <ol>
 * <li>1. @Component 将该类作为Bean注入Ioc容器；</li>
 * <li>2. @ConfigurationProperties批量注入配置文件的属性。@Value只能一个个指定。</li>
 * </ol>
 * *
 *
 * @author hankaibo
 * @date 2019/6/17
 * @see <a href="https://blog.csdn.net/andy_zhang2007/article/details/78761651" />
 * @see <a href="https://www.cnblogs.com/duanxz/p/7493276.html#4261534" />
 */
@Data
@Component
@ConfigurationProperties(prefix = "springfox")
class SwaggerProperties {

    /**
     * 是否开启swagger
     */
    private Boolean enable;

    /**
     * 项目名称
     */
    private String applicationName;

    /**
     * 项目版本号
     */
    private String applicationVersion;

    /**
     * 项目描述
     */
    private String applicationDescription;

    /**
     * 接口调度地址
     */
    private String tryHost;

    /**
     * swagger contact属性
     */
    private ContactProperties author;

    /**
     * ContactProperties
     * <p>
     * swagger 作者、网址、邮箱
     *
     * @author hankaibo
     * @date 2019/6/21
     */
    @Data
    static class ContactProperties {
        private String name;
        private String url;
        private String email;
    }

}
