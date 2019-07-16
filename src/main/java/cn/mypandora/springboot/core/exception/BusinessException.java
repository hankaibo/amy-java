package cn.mypandora.springboot.core.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * BusinessException
 *
 * @author hankaibo
 * @date 2019/6/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -1842090721936158195L;
    /**
     * 异常代码
     */
    @Builder.Default
    private int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * 异常信息
     */
    private String errorMessage;

    public BusinessException(String errorMessage) {
        this.errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.errorMessage = errorMessage;
    }

    public BusinessException(String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public BusinessException(int errorCode, String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.errorCode = errorCode;
    }

}
