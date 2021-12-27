package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
    }

    public PageNotFoundException(String message) {
        super(message);
    }

    public PageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageNotFoundException(Throwable cause) {
        super(cause);
    }

    public PageNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
