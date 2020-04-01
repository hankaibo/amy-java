package cn.mypandora.springboot.core.util;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

/**
 * IpUtil
 *
 * @author hankaibo
 * @date 2019/7/19
 * @see <a href="https://stackoverflow.com/questions/16558869/getting-ip-address-of-client">more</a>
 */
public class IpUtil {

    public static String getIpFromRequest(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
        }
    }

}
