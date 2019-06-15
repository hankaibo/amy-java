package cn.mypandora.springboot.core.shiro.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * PasswordToken
 *
 * @author hankaibo
 * @date 2019/1/15
 */
@Data
@AllArgsConstructor
public class PasswordToken implements AuthenticationToken {

    private String appId;
    private String password;
    private String timestamp;
    private String host;
    private String tokenKey;

    @Override
    public Object getPrincipal() {
        return this.appId;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }
}
