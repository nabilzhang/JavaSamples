package me.nabil.maven.plugin.restgen.exception;

/**
 * 权限系统访问失败
 *
 * @author zhangbi
 */
public class AuthorizationAccessFailedException extends RuntimeException {

    public AuthorizationAccessFailedException(String message) {
        super(message);
    }

    public AuthorizationAccessFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

