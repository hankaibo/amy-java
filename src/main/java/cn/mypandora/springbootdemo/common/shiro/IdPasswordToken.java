package cn.mypandora.springbootdemo.common.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import java.util.Arrays;

/**
 * IdPasswordToken
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdPasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken {
    private String id;
    private char[] password;
    private boolean rememberMe;
    private String host;

    public IdPasswordToken(String id, String password, boolean rememberMe, String host) {
        this.id = id;
        this.password = password == null ? null : password.toCharArray();
        this.rememberMe = rememberMe;
        this.host = host;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public void clear() {
        this.id = null;
        this.host = null;
        this.rememberMe = false;
        if (this.password != null) {
            for (int i = 0; i < this.password.length; ++i) {
                this.password[i] = '\0';
            }
            this.password = null;
        }
    }

    @Override
    public String toString() {
        return "IdPasswordToken{" +
                "id='" + id + '\'' +
                ", password=" + Arrays.toString(password) +
                ", rememberMe=" + rememberMe +
                ", host='" + host + '\'' +
                '}';
    }
}
