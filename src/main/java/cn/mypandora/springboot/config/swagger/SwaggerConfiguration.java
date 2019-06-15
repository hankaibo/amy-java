package cn.mypandora.springboot.config.swagger;

import io.swagger.annotations.ApiOperation;
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
 * 配置swagger-ui
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

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) //这里采用包含注解的方式来确定要显示的接口
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .pathMapping("/");
    }

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
