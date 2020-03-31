package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * StatusEnum
 *
 * @author hankaibo
 * @date 2020/1/8
 */
@Getter
public enum StatusEnum {

    /**
     * ENABLED : 1 DISABLED: 0
     */
    ENABLED(1), DISABLED(0);

    private int value;

    StatusEnum(int value) {
        this.value = value;
    }

}
