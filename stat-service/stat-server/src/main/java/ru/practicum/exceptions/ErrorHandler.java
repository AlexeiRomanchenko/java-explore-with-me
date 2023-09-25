package ru.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.controller.StatController;
import ru.practicum.discriptions.MessageManager;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice(assignableTypes = {StatController.class})
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(ValidationRequestException e) {
        log.info(MessageManager.REQUEST_INCORRECTLY);
        return new ApiError(MessageManager.BAD_REQUEST, MessageManager.REQUEST_INCORRECTLY,
                e.getMessage(), LocalDateTime.now());
    }
}