package cn.mypandora.springboot.core.support;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/* *
 * @Author tomsun28
 * @Description request请求安全过滤包装类
 * @Date 20:41 2018/4/15
 */
public class XssSqlHttpServletRequestWrapper extends HttpServletRequestWrapper {


    public XssSqlHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /* *
     * @Description 重写  数组参数过滤
     * @Param [parameter]
     * @Return java.lang.String[]
     */
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = filterParamString(values[i]);
        }
        return encodedValues;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> primary = super.getParameterMap();
        Map<String, String[]> result = new HashMap<>();
        for (Map.Entry<String, String[]> entry : primary.entrySet()) {
            result.put(entry.getKey(), filterEntryString(entry.getValue()));
        }
        return result;
    }

    @Override
    public String getParameter(String parameter) {
        return filterParamString(super.getParameter(parameter));
    }

    @Override
    public String getHeader(String name) {
        return filterParamString(super.getHeader(name));
    }

    @Override
    public Cookie[] getCookies() {
        Cookie[] cookies = super.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                cookie.setValue(filterParamString(cookie.getValue()));
            }
        }
        return cookies;
    }

    /* *
     * @Description  过滤字符串数组不安全内容
     * @Param [value]
     * @Return java.lang.String[]
     */
    private String[] filterEntryString(String[] value) {
        for (int i = 0; i < value.length; i++) {
            value[i] = filterParamString(value[i]);
        }
        return value;
    }

    /* *
     * @Description 过滤字符串不安全内容
     * @Param [value]
     * @Return java.lang.String
     */
    private String filterParamString(String value) {
        if (null == value) {
            return null;
        }
        // 过滤XSS 和 SQL 注入
//        return XssUtil.stripSqlXss(value);
        return "";
    }


}
