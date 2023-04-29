package cn.mypandora.springboot.core.aspect;

import cn.mypandora.springboot.core.annotaiton.Log;
import cn.mypandora.springboot.core.enums.LogStatusEnum;
import cn.mypandora.springboot.core.util.IpUtil;
import cn.mypandora.springboot.modular.system.model.po.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * @author hankaibo
 * @date 9/30/2021
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 处理完请求后执行
     *
     * @param joinPoint     切点
     * @param controllerLog
     * @param jsonResult
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint     切点
     * @param controllerLog
     * @param e             异常信息
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            // 获取当前的用户
            Subject loginUser = SecurityUtils.getSubject();

            // 数据库日志
            OperationLog operationLog = new OperationLog();
            operationLog.setStatus(LogStatusEnum.SUCCESS);
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            String ip = IpUtil.getIpFromRequest(servletRequestAttributes.getRequest());
            operationLog.setIp(ip);
            operationLog.setUrl(servletRequestAttributes.getRequest().getRequestURI());
            String username = loginUser.getPrincipal().toString();
            if (username != null) {
                operationLog.setUsername(username);
            }
            if (e != null) {
                operationLog.setStatus(LogStatusEnum.FAIL);
                operationLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operationLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operationLog.setRequestMethod(servletRequestAttributes.getRequest().getMethod());
            // 处理设置注解上的参数

        } catch (Exception exception) {
            log.error("异常信息:{}", exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息。
     *
     * @param joinPoint
     * @param log
     * @param operationLog
     * @param jsonResult
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, OperationLog operationLog, Object jsonResult) throws Exception {
        operationLog.setBusinessType(log.businessType());
        operationLog.setTitle(log.title());
        if (log.isSaveRequestData()) {
            setRequestValue(joinPoint, operationLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param joinPoint
     * @param operationLog 操作日志
     * @throws Exception
     */
    private void setRequestValue(JoinPoint joinPoint, OperationLog operationLog) throws Exception {
        String requestMethod = operationLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operationLog.setParams(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operationLog.setParams(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 参数拼接。
     *
     * @param paramsArray
     * @return
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (StringUtils.isNotBlank((CharSequence) o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = o;
                    } catch (Exception e) {

                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象
     *
     * @param o 对象信息
     * @return 如果是需要过滤的对象，则返回 true; 否则返回 false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof BindingResult;
    }
}
