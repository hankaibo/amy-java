package cn.mypandora.springboot.config.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;

/**
 * CustomFilter
 * <p>
 * 自定义过滤器，将header中authorization参数分解出的用户id直接放到参数中，方便后续Controller获取。
 *
 * @author hankaibo
 * @date 2020/1/8
 * @see <a href="https://juejin.im/post/5ce0dfb45188250c942f6c1d#heading-3">优雅获取token中参数</a>
 */
public class CustomFilter implements Filter {

    private static final String USER_ID = "userId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;

        String authorization = ((HttpServletRequest)request).getHeader("authorization");
        String jwt = JsonWebTokenUtil.unBearer(authorization);
        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        Long userId = jwtAccount.getUserId();

        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public String[] getParameterValues(String name) {
                if (USER_ID.equals(name)) {
                    return new String[] {userId.toString()};
                }
                return super.getParameterValues(name);
            }

            @Override
            public Enumeration<String> getParameterNames() {
                Set<String> paramNames = new LinkedHashSet<>();
                paramNames.add(USER_ID);
                Enumeration<String> names = super.getParameterNames();
                while (names.hasMoreElements()) {
                    paramNames.add(names.nextElement());
                }
                return Collections.enumeration(paramNames);
            }
        };
        chain.doFilter(requestWrapper, httpResponse);
    }

}
