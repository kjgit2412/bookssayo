package org.koreait.commons;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AlertBackException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super(bundleError.getString("BadRequest"));
    }

}
