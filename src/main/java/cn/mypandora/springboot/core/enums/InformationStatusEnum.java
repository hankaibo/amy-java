package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * InformationStatusEnum
 *
 * @author hankaibo
 * @date 2019/10/26
 */
@Getter
public enum InformationStatusEnum {

    /**
     * 信息类型中，事件信息的状态。
     */
    TODO(1, "未开始"),
    PROCESSING(2, "进行中"),
    URGENT(3, "马上到期"),
    DOING(4, "已耗时");

    private int value;
    private String name;

    InformationStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
