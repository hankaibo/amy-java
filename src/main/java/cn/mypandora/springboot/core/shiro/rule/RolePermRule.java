package cn.mypandora.springboot.core.shiro.rule;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cn.mypandora.springboot.core.util.JsonWebTokenUtil;
import lombok.Data;

/**
 * RolePermRule
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Data
public class RolePermRule implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String ANON_ROLE = "role_anon";

    /**
     * 资源URL
     */
    private String url;

    /**
     * 访问资源所需要的角色列表，多个列表用逗号间隔
     */
    private String needRoles;

    /**
     * 将url needRoles转化成shiro可识别的过滤器链： url=jwt[角色1、角色2、角色3]
     *
     * @return url=jwt[角色1、角色2、角色3] || null
     */
    public StringBuilder toFilterChain() {
        if (null == this.url || this.url.isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> setRole = JsonWebTokenUtil.split(this.getNeedRoles());

        // 约定若role_anon角色拥有此uri资源的权限,则此uri资源直接访问不需要认证和权限
        if (!StringUtils.isEmpty(this.getNeedRoles()) && setRole.contains(ANON_ROLE)) {
            stringBuilder.append("anon");
        }
        // 其他自定义资源uri需通过jwt认证和角色认证
        if (!StringUtils.isEmpty(this.getNeedRoles()) && !setRole.contains(ANON_ROLE)) {
            stringBuilder.append("jwt");
            stringBuilder.append("[");
            stringBuilder.append(this.getNeedRoles());
            stringBuilder.append("]");
        }
        return stringBuilder.length() > 0 ? stringBuilder : null;
    }

    @Override
    public String toString() {
        return "RolePermRule [url=" + url + ",needRoles=" + needRoles + "]";
    }

}
