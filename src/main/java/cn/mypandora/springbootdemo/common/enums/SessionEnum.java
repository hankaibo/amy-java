package cn.mypandora.springbootdemo.common.enums;

import lombok.Getter;

@Getter
public enum SessionEnum {

    CURRENT_USER("CURRENT_USER");

    private String value;

    SessionEnum(String value) {
        this.value = value;
    }
}
