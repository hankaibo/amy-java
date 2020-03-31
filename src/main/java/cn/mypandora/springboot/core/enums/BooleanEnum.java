package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * BooleanEnum
 *
 * @author hankaibo
 * @date 2019/6/19
 */
@Getter
public enum BooleanEnum {

    /**
     * YES: 1 NO: 0
     */
    YES(1), NO(0);

    private int value;

    BooleanEnum(int value) {
        this.value = value;
    }

}
