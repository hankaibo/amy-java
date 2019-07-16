package cn.mypandora.springboot.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ProjectProperties
 * <p>
 * 读取配置文件(application*.xml)中的属性值。
 * 注：
 * 1. @Component 将该类作为Bean注入Ioc容器；
 * 2. @ConfigurationProperties批量注入配置文件的属性。@Value只能一个个指定。
 *
 * @author hankaibo
 * @date 2019/6/17
 * @see <a href="../druid/DataSourceProperties.java" />
 */
@Data
@Component
@ConfigurationProperties(prefix = "project")
class ProjectProperties {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目版本号
     */
    private String version;

    /**
     * 项目描述
     */
    private String description;

    /**
     * swagger contact属性
     */
    private ContactProperties author;

    /**
     * ProjectProperties
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
