package com.czertainly.provider.entity.keystore;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.LocationException;
import com.czertainly.api.exception.NotDeletableException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.ErrorMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlingAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlingAdvice.class);

    /**
     * Handler for {@link NotFoundException}.
     *
     * @param ex Caught {@link NotFoundException}.
     * @return Error message in the payload
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDto handleNotFoundException(NotFoundException ex) {
        LOG.warn("HTTP 404: {}", ex.getMessage());
        return ErrorMessageDto.getInstance(ex.getMessage());
    }

    /**
     * Handler for {@link AlreadyExistException}.
     *
     * @return Error message in the payload
     */
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleAlreadyExistException(AlreadyExistException ex) {
        LOG.info("HTTP 400: {}", ex.getMessage());
        return ErrorMessageDto.getInstance(ex.getMessage());
    }

    /**
     * Handler for {@link NotDeletableException}.
     *
     * @return Error message in the payload
     */
    @ExceptionHandler(NotDeletableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleNotDeletableException(NotDeletableException ex) {
        LOG.info("HTTP 400: {}", ex.getMessage());
        return ErrorMessageDto.getInstance(ex.getMessage());
    }

    /**
     * Handler for {@link ValidationException}.
     *
     * @return Error message in the payload
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<String> handleValidationException(ValidationException ex) {
        LOG.info("HTTP 422: {}", ex.getMessage());

        return ex.getErrors().stream()
                .map(ValidationError::getErrorDescription)
                .collect(Collectors.toList());
    }

    /**
     * Handler for {@link HttpMessageNotReadableException}.
     *
     * @return Error message in the payload
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleMessageNotReadable(HttpMessageNotReadableException ex) {
        LOG.info("HTTP 400: {}", ex.getMessage());
        return ErrorMessageDto.getInstance(ex.getMessage());
    }

    /**
     * Handler for {@link IllegalStateException}.
     *
     * @return Error message in the payload
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleIllegalState(IllegalStateException ex) {
        LOG.info("HTTP 400: {}", ex.getMessage());
        return ErrorMessageDto.getInstance(ex.getMessage());
    }

    /**
     * Handler for {@link LocationException}.
     *
     * @return Error message in the payload
     */
    @ExceptionHandler(LocationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleIllegalState(LocationException ex) {
        LOG.info("HTTP 400: {}", ex.getMessage());
        return ErrorMessageDto.getInstance(ex.getMessage());
    }

//    /**
//     * Handler for {@link AccessDeniedException}.
//     */
//    @ExceptionHandler(AccessDeniedException.class)
//    public void handleAccessDeniedException(AccessDeniedException ex) {
//        LOG.warn("Access denied: {}", ex.getMessage());
//        // re-throw to let the Spring Security handle it
//        throw ex;
//    }

    /**
     * Handler for {@link Exception}.
     *
     * @return Error message in the payload
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDto handleException(Exception ex) {
        LOG.error("General error occurred: {}", ex.getMessage(), ex);
        return ErrorMessageDto.getInstance("Internal server error.");
    }
}
