package cn.mypandora.springboot.config.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CustomException
 *
 * @author hankaibo
 * @date 2019/9/30
 */
@Data
@NoArgsConstructor
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1677885548540168691L;

    /**
     * 错误状态码
     */
    private int code;

    public CustomException(int code, String message) {
        super(message);
        this.setCode(code);
    }

}
