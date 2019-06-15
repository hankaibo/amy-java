package cn.mypandora.springboot.core.exception;

import cn.mypandora.springboot.core.enums.ResultEnum;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {
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
