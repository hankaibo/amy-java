package cn.mypandora.springboot.core.shiro.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdPasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken {
    /**
     * 用户Id
     */
    private String id;
    /**
     * 用户密码
     */
    private char[] password;
    /**
     * 是否记住
     */
    private boolean rememberMe;
    /**
     * Ip
     */
    private String host;


    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public boolean isRememberMe() {
        return this.rememberMe;
    }

    @Override
    public Object getPrincipal() {
        return this.id;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }
}
