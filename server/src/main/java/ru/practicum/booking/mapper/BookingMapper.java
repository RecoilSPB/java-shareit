package ru.practicum.booking.mapper;

import ru.practicum.booking.comparator.BookingComparator;
import ru.practicum.booking.dto.BookingOutputDto;
import ru.practicum.booking.dto.DateBookingDto;
import ru.practicum.booking.model.Booking;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.item.mapper.ItemMapper.toItemDto;
import static ru.practicum.user.mapper.UserMapper.toUserDto;

public class BookingMapper {
    public static BookingOutputDto toBookingDto(Booking booking) {
        return BookingOutputDto.builder()
                .id(booking.getId())
                .end(booking.getEnd())
                .start(booking.getStart())
                .booker(toUserDto(booking.getBooker()))
                .item(toItemDto(booking.getItem()))
                .status(booking.getStatus())
                .build();
    }

    public static DateBookingDto toDateBookingDto(Booking booking) {
        return DateBookingDto.builder()
                .id(booking.getId())
                .bookerId(booking.getBooker().getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    public static List<BookingOutputDto> convertBookings(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .sorted(new BookingComparator().reversed())
                .collect(Collectors.toList());
    }
}