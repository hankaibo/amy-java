package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * InformationEnum
 *
 * @author hankaibo
 * @date 2019/10/26
 */
@Getter
public enum InformationTypeEnum {

    /**
     * 信息类型 1, 通知 2, 消息 3, 事件
     */
    NOTIFICATION(1), MESSAGE(2), EVENT(3);

    private int value;

    InformationTypeEnum(int value) {
        this.value = value;
    }

}
