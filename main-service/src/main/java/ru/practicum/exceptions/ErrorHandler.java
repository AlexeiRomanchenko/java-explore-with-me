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
        log.info(MessageManager.requestIncorrectly);
        return new ApiError(MessageManager.badRequest, MessageManager.requestIncorrectly,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MissingServletRequestParameterException e) {
        log.info(MessageManager.requestIncorrectly);
        return new ApiError(MessageManager.badRequest, MessageManager.requestIncorrectly,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(ValidationRequestException e) {
        log.info(MessageManager.requestIncorrectly);
        return new ApiError(MessageManager.badRequest, MessageManager.requestIncorrectly,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            CategoryNotFoundException.class,
            EventNotFoundException.class,
            RequestNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleObjectNotFoundException(EntityNotFoundException e) {
        log.info(e.getMessage());
        return new ApiError(MessageManager.notFound, MessageManager.requiredNotFound,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleValidationException(ConstraintViolationException e) {
        log.info(e.getMessage());
        return new ApiError(MessageManager.conflict, MessageManager.integrityConstraint,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleValidationException(DataIntegrityViolationException e) {
        log.info(e.getMessage());
        return new ApiError(MessageManager.conflict, MessageManager.integrityConstraint,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleValidationException(DataException e) {
        log.info(e.getMessage());
        return new ApiError(MessageManager.conflict, MessageManager.integrityConstraint,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleForbiddenException(ForbiddenException e) {
        log.info(e.getMessage());
        return new ApiError(MessageManager.forbidden, MessageManager.wrongConditions,
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(Throwable e) {
        log.info(MessageManager.unexpectedError + e.getCause());
        return new ApiError(MessageManager.internalServerError, MessageManager.unexpectedError,
                e.getMessage(), LocalDateTime.now());
    }
}