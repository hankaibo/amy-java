package cn.mypandora.springboot.core.enums;

import lombok.Getter;

/**
 * ResultEnum
 *
 * @author hankaibo
 * @date 2019/6/19
 */
@Getter
public enum ResultEnum {
    /**
     * SUCCESS: 200 成功
     * FAIL: 400 失败
     * UNAUTHORIZED： 401权限验证失败
     * NOT_FOUND： 404 不存在
     * SERVER_ERROR: 500 服务异常
     */
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    UNAUTHORIZED(401, "权限验证失败"),
    NOT_FOUND(404, "不存在"),
    INTERNAL_SERVER_ERROR(500, "服务异常");

    private final int code;
    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
    }

}
