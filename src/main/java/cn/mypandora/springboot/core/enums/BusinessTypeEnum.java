package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * 业务操作类型
 *
 * @author hankaibo
 * @date 9/30/2021
 */
@Getter
public enum BusinessTypeEnum {

    /**
     * 业务类型
     */
    OTHER("其它"), INSERT("新增"), UPDATE("修改"), DELETE("删除"), GRANT("授权"), EXPORT("导出"), IMPORT("导入");

    private final String name;

    BusinessTypeEnum(String name) {
        this.name = name;
    }
}
