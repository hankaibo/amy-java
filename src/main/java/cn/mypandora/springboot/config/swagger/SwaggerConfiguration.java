package cn.mypandora.springboot.config.swagger;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * SwaggerConfiguration
 * <p>
 * 配置swagger-ui。 注：
 * <ol>
 * <li>@Configuration是指的java Config(Java 配置)，是一个Ioc容器类，相当于传统项目中见到的一个spring的xml配置文件。
 * 当Spring发现某个类使用了@Configuration标注了，就去将该类下使用@Bean注解的方法创建bean并放入到容器中。</li>
 * <li>@Conditional满足特定条件时才会创建一个Bean放入到IOC容器，@ConditionalOnXxx都是组合@Conditional元注解，
 * 使用了不同的条件Condition。@ConditionalOnProperty指定的属性是否有指定的值。</li>
 * <li>使用更简洁的Profile代替Conditional判断是否开启swagger。(生产环境下报错，bean注册失败，故去掉。)</li>
 * </ol>
 *
 * @author hankaibo
 * @date 2019/1/14
 * @see <a href="https://blog.csdn.net/baiyicanggou_wujie/article/details/87537888" />
 */
@EnableOpenApi
@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    private final SwaggerProperties swaggerProperties;

    @Autowired
    public SwaggerConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    /**
     * 系统模块接口组（排除登录接口，因为它不需要token）
     *
     * @return Docket
     */
    @Bean
    public Docket docketSystem() {
        return new Docket(DocumentationType.OAS_30).groupName("system API")
            // 是否开启swagger
            .enable(swaggerProperties.getEnable())
            // 将api的元信息设置为包含在json响应中
            .apiInfo(apiInfo())
            // 接口调试地址
            .host(swaggerProperties.getTryHost())
            // 选择哪些接口信作为swagger的doc发布
            .select()
            // 指定包
            .apis(RequestHandlerSelectors.basePackage("cn.mypandora.springboot.modular.system.controller"))
            // 指定前缀
            .paths(PathSelectors.regex("/api/.*")).build()
            // 支持的通讯协议集合
            .protocols(new LinkedHashSet<>(Arrays.asList("https", "http")))
            // 授权信息设置，必要的header token 等认证信息
            .securitySchemes(securitySchemes())
            // 授权信息全局应用
            .securityContexts(securityContexts());
    }

    /**
     * 业务模块接口组
     *
     * @return Docket
     */
    @Bean
    public Docket docketBusiness() {
        return new Docket(DocumentationType.OAS_30).groupName("business API")
            // 是否开启swagger
            .enable(swaggerProperties.getEnable())
            // 将api的元信息设置为包含在json响应中
            .apiInfo(apiInfo())
            // 接口调试地址
            .host(swaggerProperties.getTryHost())
            // 选择哪些接口信作为swagger的doc发布
            .select()
            // 指定包
            .apis(RequestHandlerSelectors.basePackage("cn.mypandora.springboot.modular.hospital.controller"))
            // 指定前缀
            .paths(PathSelectors.regex("/api/.*")).build()
            // 支持的通讯协议集合
            .protocols(new LinkedHashSet<>(Arrays.asList("https", "http")))
            // 授权信息设置，必要的header token 等认证信息
            .securitySchemes(securitySchemes())
            // 授权信息全局应用
            .securityContexts(securityContexts());
    }

    /**
     * swagger 信息
     *
     * @return swagger页面信息
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact(swaggerProperties.getAuthor().getName(), swaggerProperties.getAuthor().getUrl(),
            swaggerProperties.getAuthor().getEmail());
        return new ApiInfoBuilder().title(swaggerProperties.getApplicationName() + " API Doc")
            .version(swaggerProperties.getApplicationVersion())
            .description(swaggerProperties.getApplicationDescription()).contact(contact).build();
    }

    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("Authorization", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder().securityReferences(Collections.singletonList(
            new SecurityReference("Authorization", new AuthorizationScope[] {new AuthorizationScope("global", "")})))
            .build());
    }

}
