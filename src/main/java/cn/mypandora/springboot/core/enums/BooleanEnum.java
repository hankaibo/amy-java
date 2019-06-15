package cn.mypandora.springboot.core.enums;

import lombok.Getter;

@Getter
public enum BooleanEnum {
    /**
     * YES: 1
     * NO: 0
     */
    YES(1),
    NO(0);

    private int value;

    BooleanEnum(int value) {
        this.value = value;
    }
}
