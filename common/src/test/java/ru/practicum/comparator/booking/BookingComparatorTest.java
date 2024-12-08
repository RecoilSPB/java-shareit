package ru.practicum.comparator.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.dto.booking.BookingOutputDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BookingComparatorTest {

    private BookingComparator comparator;

    @BeforeEach
    void setUp() {
        comparator = new BookingComparator();
    }

    @Test
    void compare_WhenStartDatesAreEqual_ShouldReturnZero() {
        BookingOutputDto booking1 = createBookingWithStart(LocalDateTime.of(2024, 12, 5, 10, 0));
        BookingOutputDto booking2 = createBookingWithStart(LocalDateTime.of(2024, 12, 5, 10, 0));

        int result = comparator.compare(booking1, booking2);

        assertThat(result).isZero();
    }

    @Test
    void compare_WhenFirstStartDateIsBeforeSecond_ShouldReturnNegative() {
        BookingOutputDto booking1 = createBookingWithStart(LocalDateTime.of(2024, 12, 5, 9, 0));
        BookingOutputDto booking2 = createBookingWithStart(LocalDateTime.of(2024, 12, 5, 10, 0));

        int result = comparator.compare(booking1, booking2);

        assertThat(result).isNegative();
    }

    @Test
    void compare_WhenFirstStartDateIsAfterSecond_ShouldReturnPositive() {
        BookingOutputDto booking1 = createBookingWithStart(LocalDateTime.of(2024, 12, 5, 11, 0));
        BookingOutputDto booking2 = createBookingWithStart(LocalDateTime.of(2024, 12, 5, 10, 0));

        int result = comparator.compare(booking1, booking2);

        assertThat(result).isPositive();
    }

    private BookingOutputDto createBookingWithStart(LocalDateTime start) {
        BookingOutputDto booking = new BookingOutputDto();
        booking.setStart(start);
        return booking;
    }
}
