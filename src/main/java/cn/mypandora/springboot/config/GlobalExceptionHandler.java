package cn.mypandora.springboot.config;

import cn.mypandora.springboot.core.exception.CustomException;
import cn.mypandora.springboot.core.exception.Error;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GlobalExceptionHandler
 *
 * @author hankaibo
 * @date 2019/9/30
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 定义要捕获的异常 可以多个 @ExceptionHandler({})
     *
     * @param request  request
     * @param ex       exception
     * @param response response
     * @return 响应结果
     */
    @ExceptionHandler(CustomException.class)
    public Error customExceptionHandler(HttpServletRequest request, final Exception ex, HttpServletResponse response) {
        CustomException exception = (CustomException) ex;
        response.setStatus(exception.getCode());
        return new Error(exception.getCode(), exception.getMessage());
    }

    /**
     * 捕获  RuntimeException 异常
     *
     * @param request  request
     * @param ex       exception
     * @param response response
     * @return 响应结果
     */
    @ExceptionHandler(RuntimeException.class)
    public Error runtimeExceptionHandler(HttpServletRequest request, final Exception ex, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        RuntimeException exception = (RuntimeException) ex;
        return new Error(400, exception.getMessage());
    }

    /**
     * 通用的接口映射异常处理方
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            return new ResponseEntity<>(new Error(status.value(), exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            return new ResponseEntity<>(new Error(status.value(), "参数转换失败"), status);
        }
        return new ResponseEntity<>(new Error(status.value(), "参数转换失败"), status);
    }
}
