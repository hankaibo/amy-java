package cn.mypandora.springboot.config.shiro;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

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

    public RestPathMatchingFilterChainResolver() {
        super();
    }

    public RestPathMatchingFilterChainResolver(FilterConfig filterConfig) {
        super(filterConfig);
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
        if (!filterChainManager.hasChains()) {
            return null;
        }

        String requestURI = getPathWithinApplication(request);
        requestURI = safeFormatUrl(requestURI);

        for (String pathPattern : filterChainManager.getChainNames()) {
            String[] pathPatternArray = pathPattern.split("==");
            String pathPatternUrl = pathPatternArray[0];
            pathPatternUrl = safeFormatUrl(pathPatternUrl);
            if (pathMatches(pathPatternUrl, requestURI)) {
                if (log.isTraceEnabled()) {
                    log.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "].  "
                        + "Utilizing corresponding filter chain...");
                }
                return filterChainManager.proxy(originalChain, pathPattern);
            }
        }
        return null;
    }

    private String safeFormatUrl(String originUrl) {
        if (originUrl != null && originUrl.endsWith(DEFAULT_PATH_SEPARATOR)) {
            originUrl = originUrl.substring(0, originUrl.length() - 1);
        }
        return originUrl;
    }

}
