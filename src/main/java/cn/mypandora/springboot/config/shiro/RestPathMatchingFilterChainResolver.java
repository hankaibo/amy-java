package cn.mypandora.springboot.config.shiro;

import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * RestPathMatchingFilterChainResolver
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Slf4j
public class RestPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {

    private static final String DEFAULT_PATH_SEPARATOR = "/";
    private static final int NUM_2 = 2;

    public RestPathMatchingFilterChainResolver() {
        super();
    }

    /**
     * 重写filterChain匹配
     *
     * @param request
     *            请求
     * @param response
     *            响应
     * @param originalChain
     *            原生过滤器链
     * @return 新过滤器链
     */
    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = this.getFilterChainManager();
        // 没有配置shiro过滤器则直接进入servlet。
        if (!filterChainManager.hasChains()) {
            return null;
        }

        String requestURI = getPathWithinApplication(request);

        requestURI = safeFormatUrl(requestURI);

        for (String pathPattern : filterChainManager.getChainNames()) {
            String[] pathPatternArray = pathPattern.split("==");
            // 不是/api/v1/xxx===GET形式的请求，则按原来shiro的匹配模式执行
            if (pathPatternArray.length == 1) {
                pathPattern = safeFormatUrl(pathPattern);
                if (pathMatches(pathPattern, requestURI)) {
                    if (log.isTraceEnabled()) {
                        log.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "]. "
                            + "Utilizing corresponding filter chain...");
                    }
                    // 只是路径一致
                    return filterChainManager.proxy(originalChain, pathPattern);
                }
            } else {
                // 自定义的请求接口形式，则除了匹配路径外还要匹配请求方法
                int len = pathPatternArray.length;
                String[] pathPatternUrlArray = Arrays.copyOf(pathPatternArray, len - 1);
                String pathPatternUrl = StringUtils.join(pathPatternUrlArray, "==");
                pathPatternUrl = safeFormatUrl(pathPatternUrl);
                String pathPatternMethod = pathPatternArray[len - 1];
                if (WebUtils.toHttp(request).getMethod().toUpperCase().equals(pathPatternMethod.toUpperCase())
                    && pathMatches(pathPatternUrl, requestURI)) {
                    if (log.isTraceEnabled()) {
                        log.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "]. "
                            + "Utilizing corresponding filter chain...");
                    }
                    return filterChainManager.proxy(originalChain, pathPattern);
                }
            }
        }
        // 匹配不到进入servlet.
        return null;
    }

    private String safeFormatUrl(String requestURI) {
        if (requestURI != null && !DEFAULT_PATH_SEPARATOR.equals(requestURI)
            && requestURI.endsWith(DEFAULT_PATH_SEPARATOR)) {
            requestURI = requestURI.substring(0, requestURI.length() - 1);
        }
        return requestURI;
    }

}
