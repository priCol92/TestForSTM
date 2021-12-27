package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CharacterNotFoundException extends RuntimeException {
    public CharacterNotFoundException() {
    }

    public CharacterNotFoundException(String message) {
        super(message);
    }

    public CharacterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CharacterNotFoundException(Throwable cause) {
        super(cause);
    }

    public CharacterNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
