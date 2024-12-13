package ru.practicum.booking.state;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.ShareItApp;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebMvcTest(controllers = BookingState.class)
@ContextConfiguration(classes = ShareItApp.class)
public class BookingStateTest {

    @ParameterizedTest
    @ValueSource(strings = {"ALL", "CURRENT", "FUTURE", "PAST", "REJECTED", "WAITING"})
    public void testFrom_ValidInput(String input) {
        Optional<BookingState> result = BookingState.from(input);
        assertTrue(result.isPresent());
        assertEquals(BookingState.valueOf(input), result.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"all", "current", "future", "past", "rejected", "waiting"})
    public void testFrom_ValidInputIgnoreCase(String input) {
        Optional<BookingState> result = BookingState.from(input);
        assertTrue(result.isPresent());
        assertEquals(BookingState.valueOf(input.toUpperCase()), result.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"INVALID", "UNKNOWN", "OTHER"})
    public void testFrom_InvalidInput(String input) {
        Optional<BookingState> result = BookingState.from(input);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFrom_NullInput() {
        Optional<BookingState> result = BookingState.from(null);
        assertTrue(result.isEmpty());
    }
}