package cn.mypandora.springboot.core.shiro.rule;

import cn.mypandora.springboot.core.utils.JsonWebTokenUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Set;

/**
 * RolePermRule
 *
 * @author hankaibo
 * @date 2019/1/15
 */
public class RolePermRule implements Serializable {
    @Getter
    private String url;
    @Getter
    private String needReles;

    public StringBuilder toFilterChain() {
        if (null == this.url || this.url.isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> setRole = JsonWebTokenUtil.split(this.getNeedReles());

        if (!StringUtils.isEmpty(this.getNeedReles()) && setRole.contains("role_anon")) {
            stringBuilder.append("anon");
        }

        if (!StringUtils.isEmpty(this.getNeedReles()) && !setRole.contains("role_anon")) {
            stringBuilder.append("jwt" + "[" + this.getNeedReles() + "]");
        }

        return stringBuilder.length() > 0 ? stringBuilder : null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
