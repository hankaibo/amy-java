package cn.mypandora.springbootdemo.common.configuration;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private final ProjectProperties projectProperties;

    @Value(value = "${swagger.enable}")
    private boolean swaggerEnable;

    @Autowired
    public SwaggerConfiguration(ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .enable(swaggerEnable)
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
