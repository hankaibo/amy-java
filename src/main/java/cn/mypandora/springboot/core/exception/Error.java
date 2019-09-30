package cn.mypandora.springboot.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error
 *
 * @author hankaibo
 * @date 2019/9/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    /**
     * 错误状态码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;
}
