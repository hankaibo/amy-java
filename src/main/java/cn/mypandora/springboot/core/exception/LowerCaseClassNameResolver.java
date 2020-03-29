package cn.mypandora.springboot.core.exception;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

/**
 * LowerCaseClassNameResolver
 *
 * @author hankaibo
 * @date 2020/3/29
 */
public class LowerCaseClassNameResolver extends TypeIdResolverBase {

    @Override
    public String idFromValue(Object o) {
        return o.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public String idFromValueAndType(Object o, Class<?> aClass) {
        return idFromValue(o);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}
