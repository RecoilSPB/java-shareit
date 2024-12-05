package ru.practicum.dto.booking;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.model.booking.BookingStatus;
import ru.practicum.model.item.Item;
import ru.practicum.model.user.User;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class BookingOutputDto extends BaseBookingDto {
    private Item item;
    private User booker;
    private BookingStatus status;
}