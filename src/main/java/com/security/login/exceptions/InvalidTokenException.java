package com.security.login.exceptions;

import com.security.login.constant.Error;

import static com.security.login.constant.Error.INVALID_TOKEN;

public class InvalidTokenException extends RuntimeException {
    private final Error error;

    public InvalidTokenException() {
        super(INVALID_TOKEN.getDescription());
        this.error = INVALID_TOKEN;
    }
    public Error getErrorCode() {
        return error;
    }
}
