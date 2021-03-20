package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * ResourceTypeEnum 资源类型枚举类，基础实体资源对象的类型。
 *
 * @author hankaibo
 * @date 2019/7/18
 */
@Getter
public enum ResourceTypeEnum {

    /**
     * 菜单 接口
     */
    MENU("菜单"), API("接口");

    private String name;

    ResourceTypeEnum(String name) {
        this.name = name;
    }

}
