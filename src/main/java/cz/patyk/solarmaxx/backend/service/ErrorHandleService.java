package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.exceptions.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ErrorHandleService<D extends Number> {
    private static final String ERROR_MESSAGE_PATTERN = "ID: {} of {}";

    public ApplicationException handleNotFoundError(D id, String message) {
        log.error(ERROR_MESSAGE_PATTERN, id, message);
        return new ApplicationException(message, HttpStatus.NOT_FOUND);
    }

    public ApplicationException handleBadRequestError(Long id, String message) {
        log.error(ERROR_MESSAGE_PATTERN, id, message);
        return new ApplicationException(message, HttpStatus.BAD_REQUEST);
    }

    public ApplicationException handleBadRequestError(String message) {
        log.error("{}", message);
        return new ApplicationException(message, HttpStatus.BAD_REQUEST);
    }
}
