package com.security.login.exceptions;

import com.security.login.constant.Error;

import static com.security.login.constant.Error.INSUFFICIENT_PERMISSION;

public class InsufficientPermissionExcpetion extends RuntimeException{
    private final Error error;

    public InsufficientPermissionExcpetion() {
        super(INSUFFICIENT_PERMISSION.getDescription());
        this.error = INSUFFICIENT_PERMISSION;
    }
    public Error getErrorCode() {
        return error;
    }

}
