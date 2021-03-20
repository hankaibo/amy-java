package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * InformationEnum
 *
 * @author hankaibo
 * @date 2019/10/26
 */
@Getter
public enum MessageTypeEnum {

    /**
     * 信息类型 通知 消息 事件
     */
    NOTIFICATION("通知"), MESSAGE("消息"), EVENT("事件");

    private String name;

    MessageTypeEnum(String name) {
        this.name = name;
    }

}
