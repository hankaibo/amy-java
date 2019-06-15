package cn.mypandora.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CorsConfiguration
 * 配置跨域支持
 *
 * @author hankaibo
 * @date  2019/1/14
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    /**
     * 开启CORS，实现跨域支持。
     *
     * @param registry  CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
