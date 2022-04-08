package com.chalon.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author wei.peng
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "用户数量太多")
public class UserTooManyException extends RuntimeException {

    public UserTooManyException() {

    }

    public UserTooManyException(String message) {
        super(message);
    }

}
