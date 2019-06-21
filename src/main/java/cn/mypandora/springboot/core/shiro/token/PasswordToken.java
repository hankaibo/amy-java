package cn.mypandora.springboot.core.shiro.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * PasswordToken
 * 自定义passwordToken。
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordToken implements AuthenticationToken {

    private static final long serialVersionUID = -7574188115661748152L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

}
