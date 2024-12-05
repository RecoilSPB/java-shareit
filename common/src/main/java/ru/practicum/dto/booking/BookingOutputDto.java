package ru.practicum.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.dto.item.ItemDto;
import ru.practicum.dto.user.UserDto;
import ru.practicum.model.booking.BookingStatus;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookingOutputDto extends BaseBookingDto {

    private ItemDto item;
    private UserDto booker;
    private BookingStatus status;

    public BookingOutputDto(Long id, LocalDateTime start, LocalDateTime end, ItemDto item, UserDto booker, BookingStatus status) {
        super(id, start, end);
        this.item = item;
        this.booker = booker;
        this.status = status;
    }
}