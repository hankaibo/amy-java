package cn.mypandora.springboot.config.exception;

/**
 * @author hankaibo
 * @date 2020/6/4
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
