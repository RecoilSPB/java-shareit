package booking;

import org.junit.jupiter.api.Test;
import ru.practicum.dto.booking.BookingOutputDto;
import ru.practicum.model.booking.BookingStatus;
import ru.practicum.dto.item.ItemDto;
import ru.practicum.dto.user.UserDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingOutputDtoTest {

    @Test
    void testBuilder() {
        Long id = 1L;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        ItemDto itemDto = new ItemDto();
        UserDto userDto = new UserDto();
        BookingStatus status = BookingStatus.APPROVED;

        BookingOutputDto bookingOutputDto = BookingOutputDto.builder()
                .id(id)
                .start(start)
                .end(end)
                .item(itemDto)
                .booker(userDto)
                .status(status)
                .build();

        assertThat(bookingOutputDto.getId()).isEqualTo(id);
        assertThat(bookingOutputDto.getStart()).isEqualTo(start);
        assertThat(bookingOutputDto.getEnd()).isEqualTo(end);
        assertThat(bookingOutputDto.getItem()).isEqualTo(itemDto);
        assertThat(bookingOutputDto.getBooker()).isEqualTo(userDto);
        assertThat(bookingOutputDto.getStatus()).isEqualTo(status);
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        ItemDto itemDto = new ItemDto();
        UserDto userDto = new UserDto();
        BookingStatus status = BookingStatus.APPROVED;

        BookingOutputDto bookingOutputDto = new BookingOutputDto(id, start, end, itemDto, userDto, status);

        assertThat(bookingOutputDto.getId()).isEqualTo(id);
        assertThat(bookingOutputDto.getStart()).isEqualTo(start);
        assertThat(bookingOutputDto.getEnd()).isEqualTo(end);
        assertThat(bookingOutputDto.getItem()).isEqualTo(itemDto);
        assertThat(bookingOutputDto.getBooker()).isEqualTo(userDto);
        assertThat(bookingOutputDto.getStatus()).isEqualTo(status);
    }

    @Test
    void testNoArgsConstructor() {
        BookingOutputDto bookingOutputDto = new BookingOutputDto(null, null, null, null, null, null);

        assertThat(bookingOutputDto.getId()).isNull();
        assertThat(bookingOutputDto.getStart()).isNull();
        assertThat(bookingOutputDto.getEnd()).isNull();
        assertThat(bookingOutputDto.getItem()).isNull();
        assertThat(bookingOutputDto.getBooker()).isNull();
        assertThat(bookingOutputDto.getStatus()).isNull();
    }
}