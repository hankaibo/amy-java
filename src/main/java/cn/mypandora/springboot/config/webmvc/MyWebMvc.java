package cn.mypandora.springboot.config.webmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.mypandora.springboot.core.converter.String2LocalDateConverter;
import cn.mypandora.springboot.core.converter.String2LocalDateTimeConverter;
import cn.mypandora.springboot.core.converter.String2LocalTimeConverter;

/**
 * @author hankaibo
 * @date 2/21/2021
 */
@Configuration
public class MyWebMvc implements WebMvcConfigurer {

    // @Bean
    // public MessageSource messageSource() {
    // ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    // messageSource.setBasename("classpath:i18n/dict");
    // messageSource.setDefaultEncoding("UTF-8");
    // return messageSource;
    // }
    @Autowired
    private MessageSource messageSource;

    /**
     * @param registry
     *            注册
     * @see <a href="https://www.codenong.com/cs106882526/">https://www.codenong.com/cs106882526/</a>
     * @see <a href="https://www.cnblogs.com/myitnews/p/12329126.html">WebMvcConfigurationSupport问题一</a>
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new String2LocalDateConverter());
        registry.addConverter(new String2LocalDateTimeConverter());
        registry.addConverter(new String2LocalTimeConverter());
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }
}
