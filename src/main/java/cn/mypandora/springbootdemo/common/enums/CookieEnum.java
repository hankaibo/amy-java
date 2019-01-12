package cn.mypandora.springbootdemo.common.enums;

import lombok.Getter;

@Getter
public enum CookieEnum {
    REMEMBER_ME("rememberMe");

    private String value;

    CookieEnum(String value) {
        this.value = value;
    }
}
