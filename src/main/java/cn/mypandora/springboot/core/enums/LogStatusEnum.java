package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * LogStatusEnum 状态枚举类，基础实体的启用禁用状态。
 *
 * @author hankaibo
 * @date 2020/1/8
 */
@Getter
public enum LogStatusEnum {

    /**
     * ENABLED DISABLED
     */
    SUCCESS("正常"), FAIL("异常");

    private final String name;

    LogStatusEnum(String name) {
        this.name = name;
    }

}
