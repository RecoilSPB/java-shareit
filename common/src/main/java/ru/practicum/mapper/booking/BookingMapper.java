package ru.practicum.mapper.booking;

import ru.practicum.comparator.booking.BookingComparator;
import ru.practicum.dto.booking.BookingOutputDto;
import ru.practicum.dto.booking.DateBookingDto;
import ru.practicum.model.booking.Booking;

import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {
    public static BookingOutputDto toBookingDto(Booking booking) {
        return BookingOutputDto.builder()
                .id(booking.getId())
                .end(booking.getEnd())
                .start(booking.getStart())
                .booker(booking.getBooker())
                .item(booking.getItem())
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