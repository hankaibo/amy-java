package cn.mypandora.springboot.core.enums;

import lombok.Getter;

@Getter
public enum CookieEnum {
    /**
     * 记住我
     */
    REMEMBER_ME("rememberMe");

    private String value;

    CookieEnum(String value) {
        this.value = value;
    }
}
