package ru.blog.exceptions;

import org.springframework.security.core.AuthenticationException;

public class RefreshTokenException extends AuthenticationException {
    public RefreshTokenException(String msg) {
        super(msg);
    }

    public RefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
