package me.nabil.maven.plugin.restgen.exception;

/**
 * httpmethod为空的exception
 *
 * @author zhangbi
 */
public class HttpMethodEmptyException extends RuntimeException {

    public HttpMethodEmptyException(String message) {
        super(message);
    }

    public HttpMethodEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
