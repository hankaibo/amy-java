package cn.mypandora.springboot.core.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 自定义校验规则。
 * 
 * @author hankaibo
 * @date 2020/5/5
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NullOrNumberValidator.class)
@Documented
public @interface NullOrNumber {

    String message() default "空或者数字";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
