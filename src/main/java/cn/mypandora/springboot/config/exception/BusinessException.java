package cn.mypandora.springboot.config.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * BusinessException
 *
 * @author hankaibo
 * @date 2019/6/19
 */
public class BusinessException extends RuntimeException {

    public BusinessException(Class clazz, String message) {
        super(StringUtils.capitalize(clazz.getSimpleName()) + " was error. " + message);
    }

}
