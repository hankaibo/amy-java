package cn.mypandora.springboot.core.enums;

import lombok.Getter;

@Getter
public enum SessionEnum {

    /**
     * 当前用户
     */
    CURRENT_USER("CURRENT_USER");

    private String value;

    SessionEnum(String value) {
        this.value = value;
    }
}
