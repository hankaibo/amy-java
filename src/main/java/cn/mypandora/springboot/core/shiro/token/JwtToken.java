package cn.mypandora.springboot.core.shiro.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken
 * 自定义一种token类型，类似与UsernamePasswordToken，只是一种登录方式对应的token。
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 7530808133924763006L;

    /**
     * 用户标识
     */
    private String username;

    /**
     * json web token值
     */
    private String jwt;

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }
}
