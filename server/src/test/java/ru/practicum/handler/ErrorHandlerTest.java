package ru.practicum.handler;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.practicum.exception.*;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorHandlerTest {

    private final ru.practicum.handler.ErrorHandler errorHandler = new ErrorHandler();

    @Test
    void testHandleNotFoundObjectException() {
        NotFoundObjectException exception = new NotFoundObjectException("Object not found");
        Map<String, String> response = errorHandler.handleNotExsistObject(exception);

        assertEquals("Object not found", response.get("error"));
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        // Создаем mock для BindingResult
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.getAllErrors())
                .thenReturn(Collections.singletonList(new ObjectError("object", "Validation error")));

        // Создаем mock для MethodArgumentNotValidException
        MethodArgumentNotValidException exception = Mockito.mock(MethodArgumentNotValidException.class);
        Mockito.when(exception.getBindingResult()).thenReturn(bindingResult);

        // Тестируем метод обработчика
        Map<String, String> response = errorHandler.handleMethodArgumentNotValidException(exception);

        // Проверяем результат
        assertEquals("Validation error", response.get("error"));
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Internal error");
        Map<String, String> response = errorHandler.handleException(exception);

        assertEquals("Internal server error", response.get("error"));
    }

    @Test
    void testHandleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User not found message");
        Map<String, String> response = errorHandler.handleUserNotFoundException(exception);

        assertEquals("User not found message", response.get("error"));
    }

    @Test
    void testHandleDuplicateObjectException() {
        DuplicateObjectException exception = new DuplicateObjectException("Duplicate object message");
        Map<String, String> response = errorHandler.handleDuplicateException(exception);

        assertEquals("Duplicate object message", response.get("error"));
    }

    @Test
    void testHandleInvalidRequestException() {
        InvalidRequestException exception = new InvalidRequestException("Invalid request message");
        Map<String, String> response = errorHandler.handle(exception);

        assertEquals("Invalid request message", response.get("error"));
    }

    @Test
    void testHandleOtherDataException() {
        OtherDataException exception = new OtherDataException("Other data message");
        Map<String, String> response = errorHandler.handle(exception);

        assertEquals("Other data message", response.get("error"));
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Illegal argument");
        Map<String, String> response = errorHandler.handle(exception);

        assertEquals("Unknown state: UNSUPPORTED_STATUS", response.get("error"));
    }

    @Test
    void testHandleItemUnavailableException() {
        ItemUnavalibleException exception = new ItemUnavalibleException("Item unavailable message");
        Map<String, String> response = errorHandler.handle(exception);

        assertEquals("Item unavailable message", response.get("error"));
    }

    @Test
    void testHandleUnknownStateException() {
        UnknownStateException exception = new UnknownStateException("Unknown state: Active");
        Map<String, String> response = errorHandler.handle(exception);

        assertEquals("Unknown state: Active", response.get("error"));
    }
}