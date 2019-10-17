package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * TypeEnum
 *
 * @author hankaibo
 * @date 2019/7/18
 */
@Getter
public enum TypeEnum {

    /**
     * 1代表菜单
     * 2代表api
     */
    MENU(1),
    API(2);

    private int value;

    TypeEnum(int value) {
        this.value = value;
    }
}
