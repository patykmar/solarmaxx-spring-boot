package cz.patyk.solarmaxx.backend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {
    @Getter
    private final HttpStatus httpStatus;

    public ApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
