package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * ResourceTypeEnum
 *
 * @author hankaibo
 * @date 2019/7/18
 */
@Getter
public enum ResourceTypeEnum {

    /**
     * 资源类型 1, 菜单 2, 接口
     */
    MENU(1), API(2);

    private int value;

    ResourceTypeEnum(int value) {
        this.value = value;
    }

}
