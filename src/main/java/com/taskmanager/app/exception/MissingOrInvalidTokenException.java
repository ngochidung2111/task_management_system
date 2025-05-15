package com.taskmanager.app.exception;

import org.springframework.security.core.AuthenticationException;

public class MissingOrInvalidTokenException extends AuthenticationException {
    public MissingOrInvalidTokenException(String msg) {
        super(msg);
    }
}