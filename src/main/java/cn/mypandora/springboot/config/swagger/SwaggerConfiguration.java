package cn.mypandora.springboot.config.swagger;

import cn.mypandora.springboot.core.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Parameter parameter = new ParameterBuilder().name("Authorization").description("header带上token")
                // -1是为了当一群都是默认值SWAGGER_PLUGIN_ORDER, TOKEN可以排在最前
                .modelRef(new ModelRef("string")).parameterType("header").order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER - 1).required(true).build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("系统API接口文档")
                //这里采用包含注解的方式来确定要显示的接口
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.mypandora.springboot.modular.system.controller"))
//                .paths(PathSelectors.regex("/api/.*"))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                // 全局信息
                .globalResponseMessage(RequestMethod.GET, customResponseMessage())
                .globalResponseMessage(RequestMethod.POST, customResponseMessage())
                .globalResponseMessage(RequestMethod.PUT, customResponseMessage())
                .globalResponseMessage(RequestMethod.DELETE, customResponseMessage())
                //请求,带上token
                .globalOperationParameters(Arrays.asList(parameter));
    }

    /**
     * 业务模块接口组
     *
     * @return Docket
     */
    @Bean
    public Docket docketBusiness() {
        Parameter parameter = new ParameterBuilder().name("Authorization").description("header带上token")
                // -1是为了当一群都是默认值SWAGGER_PLUGIN_ORDER, TOKEN可以排在最前
                .modelRef(new ModelRef("string")).parameterType("header").order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER - 1).required(true).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("业务API接口文档")
                //这里采用包含注解的方式来确定要显示的接口
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.mypandora.springboot.modular.zhizhu.controller"))
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, customResponseMessage())
                .globalResponseMessage(RequestMethod.POST, customResponseMessage())
                .globalResponseMessage(RequestMethod.PUT, customResponseMessage())
                .globalResponseMessage(RequestMethod.DELETE, customResponseMessage())
                //请求,带上token
                .globalOperationParameters(Arrays.asList(parameter));
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
                .version(projectProperties.getVersion())
                .title(projectProperties.getName())
                .description(projectProperties.getDescription())
                .contact(contact)
                .build();
    }

    private List<ResponseMessage> customResponseMessage() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(ResultEnum.SUCCESS.getCode()).message(ResultEnum.SUCCESS.getMessage()).build());
        responseMessageList.add(new ResponseMessageBuilder().code(ResultEnum.FAIL.getCode()).message(ResultEnum.FAIL.getMessage()).build());
        responseMessageList.add(new ResponseMessageBuilder().code(ResultEnum.UNAUTHORIZED.getCode()).message(ResultEnum.UNAUTHORIZED.getMessage()).build());
        responseMessageList.add(new ResponseMessageBuilder().code(ResultEnum.FORBIDDEN.getCode()).message(ResultEnum.FORBIDDEN.getMessage()).build());
        responseMessageList.add(new ResponseMessageBuilder().code(ResultEnum.NOT_FOUND.getCode()).message(ResultEnum.NOT_FOUND.getMessage()).build());
        responseMessageList.add(new ResponseMessageBuilder().code(ResultEnum.INTERNAL_SERVER_ERROR.getCode()).message(ResultEnum.INTERNAL_SERVER_ERROR.getMessage()).build());
        return responseMessageList;
    }
}
