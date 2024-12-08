package ru.practicum.dto.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.dto.item.ItemDto;
import ru.practicum.dto.user.UserDto;
import ru.practicum.model.booking.BookingStatus;
import ru.practicum.validation.CreateObject;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookingOutputDto extends BaseBookingDto {

    @Valid
    @NotNull(message = "Item is not null", groups = CreateObject.class)
    private ItemDto item;
    @Valid
    @NotNull(message = "Borker is not null", groups = CreateObject.class)
    private UserDto booker;
    private BookingStatus status;

    public BookingOutputDto(Long id, LocalDateTime start, LocalDateTime end, ItemDto item, UserDto booker, BookingStatus status) {
        super(id, start, end);
        this.item = item;
        this.booker = booker;
        this.status = status;
    }
}