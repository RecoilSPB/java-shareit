package ru.practicum.shareit.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.DuplicateObjectException;
import ru.practicum.shareit.exception.NotFoundObjectException;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        assert errorMessage != null;
        Map<String, String> error = Map.of("error", errorMessage);
        log.warn("Validation error: {}", errorMessage);
        return error;
    }

    @ExceptionHandler(NotFoundObjectException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotExsistObject(final NotFoundObjectException e) {
        Map<String, String> error = Map.of("error", e.getMessage());
        log.warn("Not found error: {}", e.getMessage());
        return error;
    }

    @ExceptionHandler(DuplicateObjectException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDuplicateException(final DuplicateObjectException e) {
        Map<String, String> error = Map.of("error", e.getMessage());
        log.warn("Conflict error: {}", e.getMessage());
        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleException(final Exception e) {
        Map<String, String> error = Map.of("error", "Internal server error");
        log.error("Internal server error: {}", e.getMessage(), e);
        return error;
    }
}