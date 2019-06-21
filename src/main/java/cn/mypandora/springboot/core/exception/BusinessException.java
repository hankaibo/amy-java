package cn.mypandora.springboot.core.exception;

import cn.mypandora.springboot.core.enums.ResultEnum;
import lombok.*;

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
    private int errorCode = ResultEnum.INTERNAL_SERVER_ERROR.getCode();

    /**
     * 异常信息
     */
    private String errorMessage;

    public BusinessException(String errorMessage) {
        this.errorCode = ResultEnum.INTERNAL_SERVER_ERROR.getCode();
        this.errorMessage = errorMessage;
    }

    public BusinessException(String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.errorCode = ResultEnum.INTERNAL_SERVER_ERROR.getCode();
    }

    public BusinessException(int errorCode, String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.errorCode = errorCode;
    }

}
