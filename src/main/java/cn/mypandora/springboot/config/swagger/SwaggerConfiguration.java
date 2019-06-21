package cn.mypandora.springboot.config.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfiguration
 * <p>
 * 配置swagger-ui。
 * 注：
 * 1. @Configuration是指的java Config(Java 配置)，是一个Ioc容器类，相当于传统项目中见到的一个spring的xml配置文件。
 * 当Spring发现某个类使用了@Configuration标注了，就去将该类下使用@Bean注解的方法创建bean并放入到容器中。
 * 2. @Conditional满足特定条件时才会创建一个Bean放入到IOC容器，@ConditionalOnXxx都是组合@Conditional元注解，
 * 使用了不同的条件Condition。@ConditionalOnProperty指定的属性是否有指定的值。
 *
 * @author hankaibo
 * @date 2019/1/14
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger", name = "enable", havingValue = "true")
public class SwaggerConfiguration {

    private final ProjectProperties projectProperties;

    @Autowired
    public SwaggerConfiguration(ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }

    /**
     * 系统模块接口组
     *
     * @return Docket
     */
    @Bean
    public Docket docketSystem() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统API接口文档")
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                //这里采用包含注解的方式来确定要显示的接口
                .apis(RequestHandlerSelectors.basePackage("cn.mypandora.springboot.modular.system.controller"))
                .paths(PathSelectors.regex("/api/.*"))
                .build();
    }

    /**
     * 业务模块接口组
     *
     * @return Docket
     */
    @Bean
    public Docket docketBusiness() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("业务API接口文档")
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                //这里采用包含注解的方式来确定要显示的接口
                .apis(RequestHandlerSelectors.basePackage("cn.mypandora.springboot.modular.zhizhu.controller"))
                .paths(PathSelectors.regex("/api/.*"))
                .build();
    }

    /**
     * swagger 信息
     *
     * @return swagger页面信息
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                projectProperties.getAuthor().getName(),
                projectProperties.getAuthor().getUrl(),
                projectProperties.getAuthor().getEmail()
        );
        return new ApiInfoBuilder()
                .title(projectProperties.getName())
                .description(projectProperties.getDescription())
                .version(projectProperties.getVersion())
                .contact(contact)
                .build();
    }
}
