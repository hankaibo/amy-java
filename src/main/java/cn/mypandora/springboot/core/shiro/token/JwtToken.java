package cn.mypandora.springboot.core.shiro.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken
 *
 * @author hankaibo
 * @date 2019/1/15
 */
@Data
@AllArgsConstructor
public class JwtToken implements AuthenticationToken {
    private String appId;         //用户的标识
    private String ipHost;        //用户的IP
    private String deviceInfo;    //设备信息
    private String jwt;           //json web token值

    @Override
    public Object getPrincipal() {
        return this.appId;
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }
}
