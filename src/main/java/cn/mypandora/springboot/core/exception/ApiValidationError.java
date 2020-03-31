package cn.mypandora.springboot.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ApiValidationError
 *
 * @author hankaibo
 * @date 2020/3/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationError extends AbstractApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
