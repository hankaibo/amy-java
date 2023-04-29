package cn.mypandora.springboot.core.annotaiton;

import cn.mypandora.springboot.core.enums.BusinessTypeEnum;

import java.lang.annotation.*;

/**
 * @author hankaibo
 * @date 9/30/2021
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     *
     * @return ""
     */
    String title() default "";

    /**
     * 功能
     *
     * @return other
     */
    BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 是否保存请求的参数
     *
     * @return true
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     *
     * @return true
     */
    boolean isSaveResponseData() default true;
}
