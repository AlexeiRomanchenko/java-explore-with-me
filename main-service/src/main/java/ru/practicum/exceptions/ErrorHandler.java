package ru.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.discriptions.MessageManager;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException e) {
        log.info(MessageManager.REQUEST_INCORRECTLY);
        return new ApiError(MessageManager.BAD_REQUEST, MessageManager.REQUEST_INCORRECTLY,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MissingServletRequestParameterException e) {
        log.info(MessageManager.REQUEST_INCORRECTLY);
        return new ApiError(MessageManager.BAD_REQUEST, MessageManager.REQUEST_INCORRECTLY,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(ValidationRequestException e) {
        log.info(MessageManager.REQUEST_INCORRECTLY);
        return new ApiError(MessageManager.BAD_REQUEST, MessageManager.REQUEST_INCORRECTLY,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            CategoryNotFoundException.class,
            EventNotFoundException.class,
            RequestNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleObjectNotFoundException(EntityNotFoundException e) {
        return new ApiError(MessageManager.NOT_FOUND, MessageManager.REQUIRED_NOT_FOUND,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleValidationException(ConstraintViolationException e) {
        return new ApiError(MessageManager.CONFLICT, MessageManager.INTEGRITY_CONSTRAINT,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleValidationException(DataIntegrityViolationException e) {
        return new ApiError(MessageManager.CONFLICT, MessageManager.INTEGRITY_CONSTRAINT,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleValidationException(DataException e) {
        return new ApiError(MessageManager.CONFLICT, MessageManager.INTEGRITY_CONSTRAINT,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleForbiddenException(ForbiddenException e) {
        return new ApiError(MessageManager.FORBIDDEN, MessageManager.WRONG_CONDITIONS,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(Throwable e) {
        log.info(MessageManager.UNEXPECTED_ERROR + e.getCause());
        return new ApiError(MessageManager.INTERNAL_SERVER_ERROR, MessageManager.UNEXPECTED_ERROR,
                e.getMessage(), LocalDateTime.now());
    }
}