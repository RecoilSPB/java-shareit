package booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.dto.booking.BookingOutputDto;
import ru.practicum.model.booking.BookingStatus;
import ru.practicum.dto.item.ItemDto;
import ru.practicum.dto.user.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookingDtoTest {

    @Mock
    private ItemDto itemDto;

    @Mock
    private UserDto userDto;

    @InjectMocks
    private BookingOutputDto bookingOutputDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bookingOutputDto = BookingOutputDto.builder()
                .id(1L)
                .start(LocalDateTime.of(2023, 10, 1, 10, 0))
                .end(LocalDateTime.of(2023, 10, 2, 10, 0))
                .item(itemDto)
                .booker(userDto)
                .status(BookingStatus.APPROVED)
                .build();
    }

    @Test
    public void testBookingOutputDtoBuilder() {
        assertNotNull(bookingOutputDto);
        assertEquals(1L, bookingOutputDto.getId());
        assertEquals(LocalDateTime.of(2023, 10, 1, 10, 0), bookingOutputDto.getStart());
        assertEquals(LocalDateTime.of(2023, 10, 2, 10, 0), bookingOutputDto.getEnd());
        assertEquals(itemDto, bookingOutputDto.getItem());
        assertEquals(userDto, bookingOutputDto.getBooker());
        assertEquals(BookingStatus.APPROVED, bookingOutputDto.getStatus());
    }

    @Test
    public void testBookingOutputDtoNoArgsConstructor() {
        BookingOutputDto noArgsBookingOutputDto = new BookingOutputDto(1L, LocalDateTime.now(),
                LocalDateTime.now(), ItemDto.builder().build(), UserDto.builder().build(), BookingStatus.WAITING);
        assertNotNull(noArgsBookingOutputDto);
    }

    @Test
    public void testBookingOutputDtoAllArgsConstructor() {
        BookingOutputDto allArgsBookingOutputDto = new BookingOutputDto(
                1L,
                LocalDateTime.of(2023, 10, 1, 10, 0),
                LocalDateTime.of(2023, 10, 2, 10, 0),
                itemDto,
                userDto,
                BookingStatus.APPROVED
        );
        assertNotNull(allArgsBookingOutputDto);
        assertEquals(1L, allArgsBookingOutputDto.getId());
        assertEquals(LocalDateTime.of(2023, 10, 1, 10, 0), allArgsBookingOutputDto.getStart());
        assertEquals(LocalDateTime.of(2023, 10, 2, 10, 0), allArgsBookingOutputDto.getEnd());
        assertEquals(itemDto, allArgsBookingOutputDto.getItem());
        assertEquals(userDto, allArgsBookingOutputDto.getBooker());
        assertEquals(BookingStatus.APPROVED, allArgsBookingOutputDto.getStatus());
    }
}

