package cn.mypandora.springboot.core.enums;

import lombok.Getter;

@Getter
public enum EnvironmentEnum {
    /**
     * 开发环境
     */
    DEV("dev"),
    /**
     * 生产环境
     */
    PROD("prod");

    private String name;

    EnvironmentEnum(String name) {
        this.name = name;
    }
}
