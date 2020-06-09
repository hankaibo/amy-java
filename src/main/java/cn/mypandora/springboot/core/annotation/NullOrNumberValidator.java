package cn.mypandora.springboot.core.annotation;

import java.util.HashSet;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义校验规则实现类。
 * 
 * @author hankaibo
 * @date 2020/5/5
 */
public class NullOrNumberValidator implements ConstraintValidator<NullOrNumber, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        HashSet<Object> status = new HashSet<>();
        status.add(null);
        status.add(1);
        status.add(0);
        return status.contains(value);
    }
}
