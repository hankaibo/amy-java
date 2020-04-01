package cn.mypandora.springboot.core.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JwtToken
 * <p>
 * 自定义一种token类型，类似与UsernamePasswordToken，只是一种登录方式对应的token。 我们的登录流程如下： 1. 用户名/密码登录，返回token; 2. 使用token查询用户信息与权限。
 * 因为第二次请求时，只有一个参数：token。故对其封装的类也就只设置了一个jwt属性。 为什么不再设置一个其它属性呢？ 一是因为设置的属性还必须前端传递，不安全；二是token里面已经封装了所有的信息，没有必要。
 * 当然，如果你非要设置其它属性，可以设置一些用户不用传递的数据。比如网络ip地址啦等待。
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
     * json web token值
     */
    private String jwt;

    @Override
    public Object getPrincipal() {
        return this.jwt;
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }

}
