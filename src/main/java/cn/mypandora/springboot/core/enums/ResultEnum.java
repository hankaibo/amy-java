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
     * FAIL: 400 错误的请求
     * UNAUTHORIZED： 401 权限验证失败
     * FORBIDDEN: 403 访问被禁止
     * NOT_FOUND： 404 不存在
     * SERVER_ERROR: 500 服务异常
     */
    SUCCESS(200, "成功"),
    FAIL(400, "错误的请求"),
    UNAUTHORIZED(401, "权限验证失败"),
    FORBIDDEN(403, "访问被禁止"),
    NOT_FOUND(404, "不存在"),
    INTERNAL_SERVER_ERROR(500, "服务异常");

    private final int code;
    private final String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
