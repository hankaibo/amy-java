package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * StatusEnum 状态枚举类，基础实体的启用禁用状态。
 *
 * @author hankaibo
 * @date 2020/1/8
 */
@Getter
public enum StatusEnum {

    /**
     * ENABLED DISABLED
     */
    ENABLED("启用"), DISABLED("禁用");

    private String name;

    StatusEnum(String name) {
        this.name = name;
    }

}
