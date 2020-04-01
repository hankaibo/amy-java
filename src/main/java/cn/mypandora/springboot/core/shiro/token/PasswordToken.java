package cn.mypandora.springboot.core.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PasswordToken 自定义passwordToken。 如果不喜欢用户/密码的认证方式，可以自定义其它的。比如手机号/随机短信；身份证号/验证码等等。
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
