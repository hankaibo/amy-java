package cn.mypandora.springboot.config.swagger;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.swagger.models.parameters.Parameter;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl;

/**
 * CustomModelToSwaggerMapper 添加access配置，隐藏登录接口header中统一添加的token。
 *
 * @author hankaibo
 * @date 2019/7/5
 */
@Primary
@Component("ServiceModelToSwagger2Mapper")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomModelToSwaggerMapper extends ServiceModelToSwagger2MapperImpl {

    @Override
    protected List<Parameter> parameterListToParameterList(List<springfox.documentation.service.Parameter> list) {
        // 自定义规则，当access="hidden"，不显示
        list = list.stream().filter(p -> !"hidden".equals(p.getParamAccess())).collect(Collectors.toList());
        // list需要根据order|position排序
        list = list.stream().sorted((p1, p2) -> Integer.compare(p1.getOrder(), p2.getOrder()))
            .collect(Collectors.toList());
        return super.parameterListToParameterList(list);
    }

}
