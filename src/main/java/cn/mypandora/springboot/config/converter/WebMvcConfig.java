package cn.mypandora.springboot.config.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.mypandora.springboot.core.converter.String2LocalDateConverter;
import cn.mypandora.springboot.core.converter.String2LocalDateTimeConverter;
import cn.mypandora.springboot.core.converter.String2LocalTimeConverter;

/**
 * @author hankaibo
 * @date 2020/8/5
 * @see <a href="https://www.codenong.com/cs106882526/">https://www.codenong.com/cs106882526/</a>
 * @see <a href="https://www.cnblogs.com/myitnews/p/12329126.html">WebMvcConfigurationSupport问题一</a>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new String2LocalDateConverter());
        registry.addConverter(new String2LocalDateTimeConverter());
        registry.addConverter(new String2LocalTimeConverter());
    }

}